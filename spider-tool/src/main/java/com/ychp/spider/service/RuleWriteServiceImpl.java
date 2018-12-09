package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.ychp.common.exception.ResponseException;
import com.ychp.spider.enums.DataType;
import com.ychp.spider.enums.RuleStatus;
import com.ychp.spider.model.Parser;
import com.ychp.spider.model.Rule;
import com.ychp.spider.cache.ParserCacher;
import com.ychp.spider.cache.RuleCacher;
import com.ychp.spider.repository.RuleRepository;
import com.ychp.spider.repository.SpiderDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Service
public class RuleWriteServiceImpl implements RuleWriteService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private RuleCacher ruleCacher;

    @Autowired
    private ParserCacher parserCacher;

    @Autowired
    private SpiderDataRepository spiderDataRepository;

    @Override
    public Boolean create(Rule rule) {
        try {

            checkArgument(!StringUtils.isEmpty(rule.getUrl()), "rule.url.not.empty");

            rule.setStatus(RuleStatus.INIT.getValue());

            List<Parser> parsers = parserCacher.listAll();

            return ruleRepository.create(rule);
        } catch (IllegalArgumentException e) {
            log.error("save rule fail, rule = {}, error = {}", rule, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean update(Rule rule) {
        try {
            checkArgument(rule.getId() != null, "rule.id.not.empty");

            return ruleRepository.update(rule);
        } catch (IllegalArgumentException e) {
            log.error("update rule fail, rule = {}, error = {}", rule, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean updateStatus(Long id, Integer status) {
        try {
            checkArgument(id != null, "rule.id.not.empty");
            checkArgument(status != null, "rule.status.not.empty");

            ruleRepository.updateStatus(id, status);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("update rule status fail, id = {}, status = {}, error = {}", id, status, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            checkArgument(id != null, "rule.id.not.empty");

            ruleRepository.delete(id);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("delete rule status fail, id = {}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public void invalidate() {
        ruleCacher.invalidate();
    }

    @Override
    public void copy(List<String> urls) {
        Rule rule = ruleRepository.findById(2L);
        Rule rule1;
        for(String url : urls){
            rule1 = new Rule();
            rule1.setUrl(url);
            rule1.setStatus(rule.getStatus());
            ruleRepository.create(rule1);
        }
    }

    @Override
    public void countRuleDatas(Rule rule) {
        if(Objects.equals(rule.getStatus(), RuleStatus.INIT.getValue())){
            return;
        }
        try {
            Long ruleId = rule.getId();
            Map<String, Object> params = Maps.newHashMap();
            params.put("ruleId", ruleId);
            params.put("type", DataType.IMAGE.getValue());
            Integer imageCount = spiderDataRepository.count(params);
            params.put("type", DataType.VIDEO.getValue());
            Integer videoCount = spiderDataRepository.count(params);
            params.put("type", DataType.TEXT.getValue());
            Integer textCount = spiderDataRepository.count(params);

            ruleRepository.updateCount(ruleId, imageCount, videoCount, textCount);
        } catch (Exception e) {
            log.error("summary rule datas fail, rule = {}, error = {}", rule, Throwables.getStackTraceAsString(e));
            throw new ResponseException("rule.data.count.fail");
        }
    }

    @Override
    public Boolean countRuleDatas(Long id) {
        try {
            Map<String, Object> params = Maps.newHashMap();
            params.put("ruleId", id);
            params.put("type", DataType.IMAGE.getValue());
            Integer imageCount = spiderDataRepository.count(params);
            params.put("type", DataType.VIDEO.getValue());
            Integer videoCount = spiderDataRepository.count(params);
            params.put("type", DataType.TEXT.getValue());
            Integer textCount = spiderDataRepository.count(params);

            ruleRepository.updateCount(id, imageCount, videoCount, textCount);
        } catch (Exception e) {
            log.error("summary rule datas fail, rule = {}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("rule.data.count.fail");
        }
        return true;
    }
}
