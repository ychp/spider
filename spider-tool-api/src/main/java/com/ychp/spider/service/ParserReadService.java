package com.ychp.spider.service;


import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.ParserCriteria;
import com.ychp.spider.bean.response.ParserDetailInfo;
import com.ychp.spider.bean.response.ParserInfo;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface ParserReadService {

    /**
     * 查询爬虫
     * @param id 编号
     * @return 爬虫
     */
    ParserDetailInfo findOneById(Long id);

    /**
     * 分页获取爬虫
     * @param criteria 条件
     * @return 爬虫分页
     */
    Paging<ParserInfo> paging(ParserCriteria criteria);

    /**
     * 获取爬虫
     * @param criteria 条件
     * @return 爬虫
     */
    List<ParserInfo> list(ParserCriteria criteria);

}
