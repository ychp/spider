package com.ychp.spider.web.component.spider;

import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ychp.common.exception.ResponseException;
import com.ychp.spider.enums.DataType;
import com.ychp.spider.enums.RuleStatus;
import com.ychp.spider.model.ParserNode;
import com.ychp.spider.model.Rule;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.service.*;
import com.ychp.spider.dto.SpiderWebData;
import com.ychp.spider.enums.ScanType;
import com.ychp.spider.parser.Parser;
import com.ychp.spider.parser.core.ParserRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Component
public class Spider {

    @Autowired
    private ParserRegistry parserRegistry;

    @Autowired
    private ParserNodeReadService parserNodeReadService;

    @Autowired
    private RuleReadService ruleReadService;

    @Autowired
    private RuleWriteService ruleWriteService;

    @Autowired
    private SpiderDataWriteService spiderDataWriteService;

    @Autowired
    private SpiderDataReadService spiderDataReadService;

    public void spider(Long ruleId, Long parserId, Integer maxPangNo) {
        log.info("start spider rule[id={},node={}]", ruleId, parserId);
        Rule rule = null;
        try {

            rule = ruleReadService.findOneById(ruleId);
            if (parserId == null) {
                parserId = rule.getParserId();
            }
            ParserNode node = parserNodeReadService.findOneById(parserId);

            ruleWriteService.updateStatus(rule.getId(), RuleStatus.WAITING.getValue());

            String parentUrl = rule.getUrl();
            Parser parser = parserRegistry.getParser(node.getKey());

            Map<String, String> ruleValues = Maps.newHashMap();
            ruleValues.put("keywords", rule.getKeyWords());
            ruleValues.put("videoTag", node.getVideoTag());
            ruleValues.put("imageTag", node.getImageTag());
            ruleValues.put("textTag", node.getTextTag());
            ruleValues.put("subTag", node.getSubTag());

            Boolean isSuccess = spider(parentUrl, maxPangNo, ruleValues, parser, rule);

            if (isSuccess) {
                ruleWriteService.updateStatus(rule.getId(), RuleStatus.FINISH.getValue());
            } else {
                ruleWriteService.updateStatus(rule.getId(), RuleStatus.ERROR.getValue());
            }

        } catch (Exception e) {
            log.error("spider data fail, error={}", Throwables.getStackTraceAsString(e));
            if (rule != null) {
                ruleWriteService.updateStatus(rule.getId(), RuleStatus.ERROR.getValue());
            }
        }

        log.info("end spider rule[id={},node={}]", ruleId, parserId);

    }

    private Boolean spider(String parentUrl, Integer pageNo,
                           Map<String, String> ruleValues,
                           Parser parser, Rule rule) {
        List<? extends SpiderWebData> datas;
        boolean isSuccess = false;
        String url = parentUrl;
        if (parentUrl.contains("{pageNo}")) {
            try {
                for (int i = 1; i < pageNo; i++) {
                    url = parentUrl.replace("{pageNo}", i + "");
                    ruleValues.put("url", url);
                    datas = parser.spider(ruleValues, rule);
                    if (datas.size() == 0) {
                        isSuccess = true;
                        break;
                    }

                    Stopwatch stopwatch = Stopwatch.createStarted();

                    saveDatas(datas, rule, url);

                    stopwatch.stop();
                    log.info("url = {} saved, cost {} ms", url, stopwatch.elapsed(TimeUnit.MILLISECONDS));
                    isSuccess = true;
                }
            } catch (ResponseException e) {
                isSuccess = true;
                log.error("spider data break by url {}, error={}", url, e.getMessage());
            } catch (Exception e) {
                log.error("spider data break by url {}, error={}", url, Throwables.getStackTraceAsString(e));
            }
        } else {
            try {
                ruleValues.put("url", url);
                datas = parser.spider(ruleValues, rule);

                Stopwatch stopwatch = Stopwatch.createStarted();

                saveDatas(datas, rule, rule.getUrl());

                stopwatch.stop();
                log.info("url = {} saved, cost {} ms", url, stopwatch.elapsed(TimeUnit.MILLISECONDS));

                isSuccess = true;
            } catch (ResponseException e) {
                isSuccess = true;
                log.error("spider data finish by url {}, error={}", url, e.getMessage());
            } catch (Exception e) {
                log.error("spider data finish by url {}, error={}", url, Throwables.getStackTraceAsString(e));
            }
        }
        return isSuccess;
    }

    private void saveDatas(List<? extends SpiderWebData> datas, Rule rule, String source) {
        if (datas.size() == 0) {
            return;
        }

        Set<SpiderData> result = Sets.newHashSet();
        SpiderData data;
        List<SpiderData> exists = spiderDataReadService.findByRuleId(rule.getId());
        Map<String, SpiderData> existByKey = exists.stream()
                .collect(Collectors.toMap(keyData -> keyData.getContent().trim().split("\\?")[0], valueData -> valueData));
        Set<String> existKey = Sets.newHashSet();

        for (SpiderWebData item : datas) {
            data = new SpiderData();
            data.setRuleId(rule.getId());
            data.setContent(item.getContent());
            data.setUrl(item.getUrl());

            String content = item.getContent().trim().split("\\?")[0];
            SpiderData exist = existByKey.get(content);

            if (exist != null) {
                if (exist.getType() != DataType.VIDEO.getValue()) {
                    continue;
                }
                data = exist;
                data.setContent(item.getContent());
            } else {
                data.setSource(source);
                data.setType(DataType.fromText(item.getType()).getValue());
                data.setLevel(item.getLevel());
                if ((Objects.equals(item.getType(), ScanType.TAG.getValue())
                        && item.getContent().contains("iframe")) || StringUtils.isEmpty(item.getContent())) {
                    continue;
                }
                if (item.getUrl().startsWith("/")) {
                    continue;
                }
            }

            if (!existKey.contains(content)) {
                existKey.add(content);
                result.add(data);
            }
        }

        if (result.isEmpty()) {
            if (datas.size() == 1) {
                throw new ResponseException("spiderData.spider.finish");
            } else {
                return;
            }
        }

        if (!spiderDataWriteService.creates(Lists.newArrayList(result))) {
            throw new ResponseException("spiderData.save.fail");
        }
    }

}
