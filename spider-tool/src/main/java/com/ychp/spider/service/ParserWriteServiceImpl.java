package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.spider.model.Parser;
import com.ychp.spider.repository.ParserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Service
public class ParserWriteServiceImpl implements ParserWriteService {

    @Autowired
    private ParserRepository parserRepository;

    @Override
    public Boolean create(Parser parser) {
        try {
            return parserRepository.create(parser);
        } catch (IllegalArgumentException e) {
            log.error("save parser fail, parser = {}, error = {}", parser, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean update(Parser parser) {
        try {
            return parserRepository.update(parser);
        } catch (IllegalArgumentException e) {
            log.error("update parser fail, rule = {}, error = {}", parser, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            checkArgument(id != null, "parser.id.not.empty");

            parserRepository.delete(id);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("delete parser fail, id = {}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }
}
