package com.ychp.spider.web.controller.web.spider;

import com.google.common.collect.Lists;
import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.spider.bean.request.RuleCriteria;
import com.ychp.spider.enums.RuleStatus;
import com.ychp.spider.model.Rule;
import com.ychp.spider.service.RuleReadService;
import com.ychp.spider.service.RuleWriteService;
import com.ychp.spider.web.async.SpiderAllEvent;
import com.ychp.spider.web.async.SpiderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018/12/02
 */
@Slf4j
@RestController
@RequestMapping("/api/rule")
public class RuleApis {


    @Autowired
    private RuleWriteService ruleWriteService;

    @Autowired
    private RuleReadService ruleReadService;

    @Autowired
    private AsyncPublisher asyncPublisher;

    /**
     * 创建
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean create(@RequestBody Rule rule) {
        return ruleWriteService.create(rule);
    }


    /**
     * 修改
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean update(@RequestBody Rule rule) {
        return ruleWriteService.update(rule);
    }

    /**
     * 详情页
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rule detail(@PathVariable("id") Long id) {
        return ruleReadService.findOneById(id);
    }

    /**
     * 删除
     */
    @PutMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean del(@PathVariable("id") Long id) {
        return ruleWriteService.delete(id);
    }

    /**
     * 抓取
     */
    @GetMapping(value = "{id}/spider", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean spider(@PathVariable("id") Long id, @RequestParam(value = "parserId", required = false) Long parserId) {
        Boolean updated = ruleWriteService.updateStatus(id, RuleStatus.WAITING.getValue());
        asyncPublisher.post(new SpiderEvent(id, parserId));
        return updated;
    }

    /**
     * 全部抓取
     */
    @GetMapping(value = "spiderAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean spider() {

        List<Rule> rules = ruleReadService.findNoSpider(new RuleCriteria());

        log.info("spider rules[ size = {} ]", rules.size());
        List<Rule> finalRules = Lists.newArrayList();

        for (Rule rule : rules) {
            if (Objects.equals(rule.getStatus(), RuleStatus.DOWNLOAD.getValue())
                    || Objects.equals(rule.getStatus(), RuleStatus.DOWNLOAD_ALL.getValue())
                    || Objects.equals(rule.getStatus(), RuleStatus.FINISH.getValue())) {
                log.error("rule has spider by id={}", rule.getId());
                continue;
            }
            finalRules.add(rule);
        }

        List<SpiderEvent> spiderEvents = Lists.newArrayList();
        SpiderEvent spiderEvent;
        for (Rule rule : finalRules) {
            spiderEvent = new SpiderEvent();
            spiderEvent.setRuleId(rule.getId());
            spiderEvents.add(spiderEvent);
        }

        asyncPublisher.post(new SpiderAllEvent(spiderEvents));

        return true;
    }

    /**
     * 清除缓存
     */
    @PutMapping(value = "/clearCache", produces = MediaType.APPLICATION_JSON_VALUE)
    public void clearCache() {
        ruleWriteService.invalidate();
    }

    /**
     * 清除缓存
     */
    @GetMapping(value = "/copy", produces = MediaType.APPLICATION_JSON_VALUE)
    public void copy() {
        String urls = "";
        List<String> urlList = Lists.newArrayList(urls.split(","));
        ruleWriteService.copy(urlList);
    }

}
