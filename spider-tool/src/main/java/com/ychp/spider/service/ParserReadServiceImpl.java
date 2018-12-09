package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.ParserCriteria;
import com.ychp.spider.bean.response.ParserDetailInfo;
import com.ychp.spider.bean.response.ParserInfo;
import com.ychp.spider.cache.ParserTypeCacher;
import com.ychp.spider.converter.ParserConverter;
import com.ychp.spider.model.Parser;
import com.ychp.spider.model.ParserType;
import com.ychp.spider.repository.ParserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Service
public class ParserReadServiceImpl implements ParserReadService {

    @Autowired
    private ParserRepository parserRepository;

    @Autowired
    private ParserTypeCacher parserTypeCacher;

    @Override
    public ParserDetailInfo findOneById(Long id) {
        try {
            Parser parser = parserRepository.findById(id);
            if (parser == null) {
                return null;
            }
            ParserType type = parserTypeCacher.findById(parser.getParserTypeId());

            return ParserConverter.convertDetail(parser, type);
        } catch (Exception e) {
            log.error("find parser fail by id={}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("parser.find.by.id.fail");
        }
    }

    @Override
    public Paging<ParserInfo> paging(ParserCriteria criteria) {
        try {
            Paging<Parser> parserPaging = parserRepository.paging(criteria.toMap());
            if (parserPaging.isEmpty()) {
                return Paging.empty();
            }
            List<ParserInfo> parserInfos = parserPaging.getDatas().stream()
                    .map(parser -> {
                        ParserType type = parserTypeCacher.findById(parser.getParserTypeId());
                        return ParserConverter.convert(parser, type);
                    }).collect(Collectors.toList());
            return new Paging<>(parserPaging.getTotal(), parserInfos);
        } catch (Exception e) {
            log.error("paging parser fail, params = {}, error = {}", criteria, Throwables.getStackTraceAsString(e));
            throw new ResponseException("parser.paging.fail");
        }
    }

    @Override
    public List<ParserInfo> list(ParserCriteria criteria) {
        try {
            return parserRepository.list(criteria.toMap()).stream()
                    .map(parser -> {
                        ParserType type = parserTypeCacher.findById(parser.getParserTypeId());
                        return ParserConverter.convert(parser, type);
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("list parser fail, params = {}, error = {}", criteria, Throwables.getStackTraceAsString(e));
            throw new ResponseException("parser.list.fail");
        }
    }
}
