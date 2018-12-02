package com.ychp.spider.web.controller.web.spider;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.DataCriteria;
import com.ychp.spider.enums.DataType;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.service.SpiderDataReadService;
import com.ychp.spider.service.SpiderDataWriteService;
import com.ychp.spider.image.core.WriterRegistry;
import com.ychp.spider.web.async.DataCountEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author yingchengpeng
 * @date 2018/12/02
 */
@Slf4j
@RestController
@RequestMapping("/api/data")
public class DataApis {

    @Autowired
    private SpiderDataWriteService spiderDataWriteService;

    @Autowired
    private SpiderDataReadService spiderDataReadService;

    @Autowired
    private AsyncPublisher asyncPublisher;

    @Autowired
    private WriterRegistry writerRegistry;

    /**
     * 详情页
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SpiderData detail(@PathVariable("id") Long id) {
        return spiderDataReadService.findOneById(id);
    }

    /**
     * 删除
     */
    @PutMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean del(@PathVariable("id") Long id) {
        SpiderData spiderData = spiderDataReadService.findOneById(id);
        if (spiderData.getPath() != null) {
            writerRegistry.getWriter("defaultWriter").deleteImage(spiderData.getPath());
        }

        Boolean deleted = spiderDataWriteService.delete(id);
        asyncPublisher.post(new DataCountEvent(id));
        return deleted;
    }

    /**
     * 相册
     */
    @GetMapping(value = "album", produces = MediaType.APPLICATION_JSON_VALUE)
    public Paging<SpiderData> result(DataCriteria dataCriteria) {
        dataCriteria.setType(DataType.IMAGE.getValue());
        return spiderDataReadService.paging(dataCriteria);
    }


}
