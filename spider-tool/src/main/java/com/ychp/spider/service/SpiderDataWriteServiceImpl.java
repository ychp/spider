package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.ychp.common.exception.ResponseException;
import com.ychp.spider.enums.DataStatus;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.repository.SpiderDataRepository;
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
public class SpiderDataWriteServiceImpl implements SpiderDataWriteService {

    @Resource
    private SpiderDataRepository spiderDataRepository;

    @Override
    public Boolean create(SpiderData spiderData) {
        try {
            spiderData.setStatus(DataStatus.INIT.getValue());

            return spiderDataRepository.create(spiderData);
        } catch (Exception e) {
            log.error("save spiderData fail, spiderData = {}, error = {}",
                    spiderData, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean creates(List<SpiderData> spiderDatas) {
        try {
            for (SpiderData data : spiderDatas) {
                if (data.getStatus() == null) {
                    data.setStatus(DataStatus.INIT.getValue());
                }
                if (data.getId() == null) {
                    spiderDataRepository.create(data);
                } else {
                    spiderDataRepository.update(data);
                }
            }

            return true;

        } catch (Exception e) {
            log.error("save spiderDatas fail, spiderDatas = {}, error = {}",
                    spiderDatas, Throwables.getStackTraceAsString(e));
            throw new ResponseException(e.getMessage());
        }
    }

    @Override
    public Boolean update(SpiderData spiderData) {
        try {
            spiderDataRepository.update(spiderData);
            return true;
        } catch (Exception e) {
            log.error("update spiderData fail, spiderData = {}, error = {}",
                    spiderData, Throwables.getStackTraceAsString(e));
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
            log.error("update spiderData status fail, id = {}, status = {}, error = {}",
                    id, status, Throwables.getStackTraceAsString(e));
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
            log.error("delete spiderData status fail, id = {}, error = {}",
                    id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("spiderData.delete.fail");
        }
    }

    @Override
    public Boolean deleteBy(Long taskId) {
        if (taskId == null) {
            throw new ResponseException("spiderData.taskId.not.empty");
        }
        try {

            spiderDataRepository.deleteBy(taskId);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("delete spiderData by task fail, taskId = {}, error = {}",
                    taskId, Throwables.getStackTraceAsString(e));
            throw new ResponseException("spiderData.deleteBy.fail");
        }
    }
}
