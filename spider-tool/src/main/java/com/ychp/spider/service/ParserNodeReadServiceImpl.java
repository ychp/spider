package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.ParserCriteria;
import com.ychp.spider.model.ParserNode;
import com.ychp.spider.dao.ParserNodeRepository;
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
public class ParserNodeReadServiceImpl implements ParserNodeReadService {

    @Autowired
    private ParserNodeRepository parserNodeRepository;

    @Override
    public ParserNode findOneById(Long id) {
        try {
            return parserNodeRepository.findById(id);
        } catch (Exception e){
            log.error("find parserNode fail by id={}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("parserNode.find.by.id.fail");
        }
    }

    @Override
    public Paging<ParserNode> paging(ParserCriteria criteria) {
        try {
            return parserNodeRepository.paging(criteria.toMap());
        } catch (Exception e){
            log.error("paging parserNode fail, params = {}, error = {}", criteria, Throwables.getStackTraceAsString(e));
            throw new ResponseException("parserNode.paging.fail");
        }
    }

    @Override
    public List<ParserNode> list(ParserCriteria criteria) {
        try {
            Paging<ParserNode> paging = parserNodeRepository.paging(criteria.toMap());
            return paging.getDatas();
        } catch (Exception e){
            log.error("list parserNode fail, params = {}, error = {}", criteria, Throwables.getStackTraceAsString(e));
            throw new ResponseException("parserNode.list.fail");
        }
    }
}
