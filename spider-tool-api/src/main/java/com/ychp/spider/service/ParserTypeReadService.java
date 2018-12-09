package com.ychp.spider.service;


import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.ParserTypeCriteria;
import com.ychp.spider.bean.response.ParserTypeInfo;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface ParserTypeReadService {

    /**
     * 查询爬虫类型
     * @param id 编号
     * @return 爬虫类型
     */
    ParserTypeInfo findOneById(Long id);

    /**
     * 分页获取爬虫类型
     * @param criteria 条件
     * @return 爬虫类型分页
     */
    Paging<ParserTypeInfo> paging(ParserTypeCriteria criteria);

    /**
     * 获取所有爬虫类型
     * @param criteria 条件
     * @return 爬虫类型
     */
    List<ParserTypeInfo> list(ParserTypeCriteria criteria);

}
