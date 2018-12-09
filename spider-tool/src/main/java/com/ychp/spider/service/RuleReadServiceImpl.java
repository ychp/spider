package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.RuleCriteria;
import com.ychp.spider.model.Rule;
import com.ychp.spider.cache.RuleCacher;
import com.ychp.spider.repository.RuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Service
public class RuleReadServiceImpl implements RuleReadService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private RuleCacher ruleCacher;

    @Override
    public Rule findOneById(Long id) {
        try {
            return ruleRepository.findById(id);
        } catch (Exception e){
            log.error("find rule fail by id={}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("rule.find.by.id.fail");
        }
    }

    @Override
    public Paging<Rule> paging(RuleCriteria criteria) {
        try {
            return ruleRepository.paging(criteria.toMap());
        } catch (Exception e){
            log.error("paging rule fail, params = {}, error = {}", criteria, Throwables.getStackTraceAsString(e));
            throw new ResponseException("rule.paging.fail");
        }
    }

    @Override
    public List<Rule> findNoSpider(RuleCriteria criteria) {
        try {
            return ruleRepository.paging(criteria.toMap()).getDatas();
        } catch (Exception e){
            log.error("find rule fail, error = {}", Throwables.getStackTraceAsString(e));
            throw new ResponseException("rule.find.fail");
        }
    }

    @Override
    public List<Rule> listAll() {
        try {
            return ruleCacher.listAllWithDomain();
        } catch (Exception e){
            log.error("list rule fail, error = {}", Throwables.getStackTraceAsString(e));
            throw new ResponseException("rule.list.fail");
        }
    }

    @Override
    public Rule findByCode(String code) {
        try {
            return ruleRepository.findByCode(code);
        } catch (Exception e){
            log.error("find rule fail by code={}, error = {}", code, Throwables.getStackTraceAsString(e));
            throw new ResponseException("rule.find.by.code.fail");
        }
    }
}
