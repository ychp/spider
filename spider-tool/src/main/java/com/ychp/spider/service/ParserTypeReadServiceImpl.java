package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.ParserTypeCriteria;
import com.ychp.spider.bean.response.ParserTypeInfo;
import com.ychp.spider.converter.ParserTypeConverter;
import com.ychp.spider.model.ParserType;
import com.ychp.spider.repository.ParserTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Service
public class ParserTypeReadServiceImpl implements ParserTypeReadService {

    @Resource
    private ParserTypeRepository parserTypeRepository;

    @Override
    public ParserTypeInfo findOneById(Long id) {
        try {
            return ParserTypeConverter.convert(parserTypeRepository.findById(id));
        } catch (Exception e) {
            log.error("find parser type fail by id={}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("parser.type.find.by.id.fail");
        }
    }

    @Override
    public Paging<ParserTypeInfo> paging(ParserTypeCriteria criteria) {
        try {
            Paging<ParserType> parserTypePaging = parserTypeRepository.paging(criteria.toMap());
            if(parserTypePaging.isEmpty()) {
                return Paging.empty();
            }

            return new Paging<>(parserTypePaging.getTotal(), ParserTypeConverter.convert(parserTypePaging.getDatas()));
        } catch (Exception e) {
            log.error("paging parser type fail, params = {}, error = {}",
                    criteria, Throwables.getStackTraceAsString(e));
            throw new ResponseException("parser.type.paging.fail");
        }
    }

    @Override
    public List<ParserTypeInfo> list(ParserTypeCriteria criteria) {
        try {
            return ParserTypeConverter.convert(parserTypeRepository.list(criteria.toMap()));
        } catch (Exception e) {
            log.error("list parser type fail, params = {}, error = {}",
                    criteria, Throwables.getStackTraceAsString(e));
            throw new ResponseException("parser.type.list.fail");
        }
    }
}
