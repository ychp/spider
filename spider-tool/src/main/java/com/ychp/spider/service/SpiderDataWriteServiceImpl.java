package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.spider.enums.RuleStatus;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.repository.SpiderDataRepository;
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
public class SpiderDataWriteServiceImpl implements SpiderDataWriteService {

    @Autowired
    private SpiderDataRepository spiderDataRepository;

    @Override
    public Boolean create(SpiderData spiderData) {
        try {
            int count = spiderDataRepository.countByContent(spiderData.getContent());
            if (count > 1) {
                return true;
            }
            spiderData.setStatus(RuleStatus.INIT.getValue());

            return spiderDataRepository.create(spiderData);
        } catch (Exception e) {
            log.error("save spiderData fail, spiderData = {}, error = {}", spiderData, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean creates(List<SpiderData> spiderDatas) {
        try {
            for (SpiderData data : spiderDatas) {
                if (data.getStatus() == null) {
                    data.setStatus(RuleStatus.INIT.getValue());
                }
                if (data.getId() == null) {
                    spiderDataRepository.create(data);
                } else {
                    spiderDataRepository.update(data);
                }
            }

            return true;

        } catch (Exception e) {
            log.error("save spiderDatas fail, spiderDatas = {}, error = {}", spiderDatas, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean update(SpiderData spiderData) {
        if (spiderData.getId() == null) {
            throw new ResponseException("spiderData.id.not.empty");
        }
        try {

            return spiderDataRepository.update(spiderData);
        } catch (Exception e) {
            log.error("update spiderData fail, spiderData = {}, error = {}", spiderData, Throwables.getStackTraceAsString(e));
            throw new ResponseException("spiderData.update.fail");
        }
    }

    @Override
    public Boolean updateStatus(Long id, Integer status) {
        if (id == null) {
            throw new ResponseException("spiderData.id.not.empty");
        }
        try {
            spiderDataRepository.updateStatus(id, status);
            return true;
        } catch (Exception e) {
            log.error("update spiderData status fail, id = {}, status = {}, error = {}", id, status, Throwables.getStackTraceAsString(e));
            throw new ResponseException("spiderData.update.status.fail");
        }
    }

    @Override
    public Boolean delete(Long id) {
        if (id == null) {
            throw new ResponseException("spiderData.id.not.empty");
        }
        try {
            spiderDataRepository.delete(id);
            return true;
        } catch (Exception e) {
            log.error("delete spiderData status fail, id = {}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("spiderData.delete.fail");
        }
    }

    @Override
    public Boolean deleteBy(Long ruleId) {
        if (ruleId == null) {
            throw new ResponseException("spiderData.ruleId.not.empty");
        }
        try {

            spiderDataRepository.deleteBy(ruleId);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("delete spiderData by rule fail, ruleId = {}, error = {}", ruleId, Throwables.getStackTraceAsString(e));
            throw new ResponseException("spiderData.deleteBy.fail");
        }
    }
}
