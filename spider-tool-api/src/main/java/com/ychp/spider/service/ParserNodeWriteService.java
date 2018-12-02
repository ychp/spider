package com.ychp.spider.service;


import com.ychp.spider.model.ParserNode;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface ParserNodeWriteService {

    Boolean create(ParserNode parserNode);

    Boolean update(ParserNode parserNode);

    Boolean delete(Long id);
}
