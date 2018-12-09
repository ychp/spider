package com.ychp.spider.web.controller.web.spider;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.spider.service.SpiderDataWriteService;
import com.ychp.spider.web.async.ImageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yingchengpeng
 * @date 2018/12/02
 */
@Slf4j
@RestController
@RequestMapping("/api/album")
public class AlbumApi {

    @Autowired
    private SpiderDataWriteService spiderDataWriteService;

    @Autowired
    private AsyncPublisher asyncPublisher;

    /**
     * 清空相册
     */
    @PutMapping(value = "delete/{ruleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean delete(@PathVariable("ruleId") Long ruleId) {
        Boolean deleted = spiderDataWriteService.deleteBy(ruleId);
        return deleted;
    }

    /**
     * 下载相册
     */
    @PutMapping(value = "download/{ruleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean download(@PathVariable("ruleId") Long ruleId) {
        asyncPublisher.post(new ImageEvent(ruleId));
        return true;
    }
}
