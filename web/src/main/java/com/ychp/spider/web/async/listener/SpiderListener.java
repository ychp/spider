package com.ychp.spider.web.async.listener;

import com.google.common.base.Throwables;
import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.spider.service.RuleWriteService;
import com.ychp.spider.web.async.*;
import com.ychp.spider.web.component.spider.Imager;
import com.ychp.spider.web.component.spider.Spider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yingchengpeng
 * @date 2018/12/02
 */
@Slf4j
@AsyncBean
public class SpiderListener {

    @Autowired
    private Spider spider;

    @Autowired
    private Imager imager;

    @Autowired
    private RuleWriteService ruleWriteService;

    @AsyncSubscriber
    public void spider(SpiderEvent spiderEvent){
        spider.spider(spiderEvent.getRuleId(), spiderEvent.getParserId(), 5000);
    }

    @AsyncSubscriber
    public void spiderAll(SpiderAllEvent spiderAllEvent){
        spiderAllEvent.getRules()
                .forEach(spiderEvent ->
                        spider.spider(spiderEvent.getRuleId(), spiderEvent.getParserId(), 5000));
        spiderAllEvent.getRules().forEach(spiderEvent ->
                ruleWriteService.countRuleDatas(spiderEvent.getRuleId()));
    }

    @AsyncSubscriber
    public void download(ImageEvent imageEvent){
        imager.download(imageEvent.getRuleId());
    }

    @AsyncSubscriber
    public void count(DataCountEvent dataCountEvent){
        log.info("start count rule[id={}]", dataCountEvent.getRuleId());
        try {
            ruleWriteService.countRuleDatas(dataCountEvent.getRuleId());
        }catch (Exception e){
            log.error("count data fail, error={}", Throwables.getStackTraceAsString(e));
        }

        log.info("end count rule[id={}]", dataCountEvent.getRuleId());

    }

    @AsyncSubscriber
    public void spiderUpdate(SpiderUpdateEvent spiderEvent){
        spider.spider(spiderEvent.getRuleId(), spiderEvent.getParserId(), 3);
    }

}
