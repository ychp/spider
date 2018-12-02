package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.spider.model.ParserNode;
import com.ychp.spider.dao.ParserNodeRepository;
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
public class ParserNodeWriteServiceImpl implements ParserNodeWriteService {

    @Autowired
    private ParserNodeRepository parserNodeRepository;

    @Override
    public Boolean create(ParserNode parserNode) {
        try {
            if(StringUtils.isEmpty(parserNode.getVideoTag())
                    &&StringUtils.isEmpty(parserNode.getImageTag())
                    &&StringUtils.isEmpty(parserNode.getTextTag())
                    &&StringUtils.isEmpty(parserNode.getSubTag())){
                parserNode.setSubTag("a");
            }
            return parserNodeRepository.create(parserNode);
        } catch (IllegalArgumentException e) {
            log.error("save parserNode fail, parserNode = {}, error = {}", parserNode, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean update(ParserNode parserNode) {
        try {
            return parserNodeRepository.update(parserNode);
        } catch (IllegalArgumentException e) {
            log.error("update parserNode fail, rule = {}, error = {}", parserNode, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            checkArgument(id != null, "parserNode.id.not.empty");

            parserNodeRepository.delete(id);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("delete parserNode fail, id = {}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }
}
