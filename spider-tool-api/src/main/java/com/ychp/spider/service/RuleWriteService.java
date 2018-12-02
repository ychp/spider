package com.ychp.spider.service;

import com.ychp.spider.model.Rule;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface RuleWriteService {

    Boolean create(Rule rule);

    Boolean update(Rule rule);

    Boolean updateStatus(Long id, Integer status);

    Boolean delete(Long id);

    void invalidate();

    void copy(List<String> urls);

    void countRuleDatas(Rule rule);

    Boolean countRuleDatas(Long id);
}
