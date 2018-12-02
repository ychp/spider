package com.ychp.spider.service;


import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.RuleCriteria;
import com.ychp.spider.model.Rule;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface RuleReadService {

    Rule findOneById(Long id);

    Paging<Rule> paging(RuleCriteria criteria);

    List<Rule> findNoSpider(RuleCriteria criteria);

    List<Rule> listAll();

    Rule findByCode(String code);

}
