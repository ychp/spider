package com.ychp.spider.service;


import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.ParserCriteria;
import com.ychp.spider.model.ParserNode;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface ParserNodeReadService {

    ParserNode findOneById(Long id);

    Paging<ParserNode> paging(ParserCriteria criteria);

    List<ParserNode> list(ParserCriteria criteria);

}
