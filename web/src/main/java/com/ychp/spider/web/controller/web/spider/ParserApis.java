package com.ychp.spider.web.controller.web.spider;

import com.ychp.common.util.SessionContextUtils;
import com.ychp.spider.model.Parser;
import com.ychp.spider.service.ParserWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ParserWriteService parserWriteService;

    /**
     * 创建
     */
    @PostMapping
    public Boolean create(@RequestBody Parser parser) {
        parser.setUserId(SessionContextUtils.getUserId());
        return parserWriteService.create(parser);
    }

    /**
     * 修改
     */
    @PutMapping
    public Boolean update(@RequestBody Parser parser) {
        return parserWriteService.update(parser);
    }

    /**
     * 删除
     */
    @DeleteMapping(value = "/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return parserWriteService.delete(id);
    }

}
