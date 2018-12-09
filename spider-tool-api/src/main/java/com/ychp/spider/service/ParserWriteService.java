package com.ychp.spider.service;


import com.ychp.spider.model.Parser;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface ParserWriteService {

    Boolean create(Parser parser);

    Boolean update(Parser parser);

    Boolean delete(Long id);
}
