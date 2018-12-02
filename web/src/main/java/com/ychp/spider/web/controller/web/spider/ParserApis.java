package com.ychp.spider.web.controller.web.spider;

import com.ychp.spider.model.ParserNode;
import com.ychp.spider.service.ParserNodeReadService;
import com.ychp.spider.service.ParserNodeWriteService;
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
@RequestMapping("/api/parser")
public class ParserApis {


    @Autowired
    private ParserNodeWriteService parserNodeWriteService;

    @Autowired
    private ParserNodeReadService parserNodeReadService;

    /**
     * 创建
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean create(@RequestBody ParserNode parser){
        return parserNodeWriteService.create(parser);
    }


    /**
     * 修改
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean update(@RequestBody ParserNode parser){
        return parserNodeWriteService.update(parser);
    }

    /**
     * 详情页
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParserNode detail(@PathVariable("id")Long id){
        return parserNodeReadService.findOneById(id);
    }

    /**
     * 删除
     */
    @PutMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean del(@PathVariable("id")Long id){
        return parserNodeWriteService.delete(id);
    }


}
