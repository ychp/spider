package com.ychp.spider.web.async.listener;

import com.ychp.async.annontation.AsyncBean;
import com.ychp.async.annontation.AsyncSubscriber;
import com.ychp.spider.web.async.ImageEvent;
import com.ychp.spider.web.component.spider.Imager;
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
    private Imager imager;

    @AsyncSubscriber
    public void download(ImageEvent imageEvent) {
        imager.download(imageEvent.getRuleId());
    }

}
