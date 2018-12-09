package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.DataCriteria;
import com.ychp.spider.dto.AlbumDto;
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
public class SpiderDataReadServiceImpl implements SpiderDataReadService {

    @Resource
    private SpiderDataRepository spiderDataRepository;

    @Override
    public SpiderData findOneById(Long id) {
        try {
            return spiderDataRepository.findById(id);
        } catch (Exception e){
            log.error("find data fail by id={}, error = {}", id, Throwables.getStackTraceAsString(e));
            throw new ResponseException("data.find.by.id.fail");
        }
    }

    @Override
    public Paging<SpiderData> paging(DataCriteria criteria) {
        try {
            return spiderDataRepository.paging(criteria.toMap());
        } catch (Exception e){
            log.error("paging data fail, params = {}, error = {}", criteria, Throwables.getStackTraceAsString(e));
            throw new ResponseException("data.paging.fail");
        }
    }

    @Override
    public List<AlbumDto> findAlbum() {
        try {
            List<AlbumDto> result = Lists.newArrayList();
            List<AlbumDto> initDatas = Lists.newArrayList();
            List<AlbumDto> finalDatas = Lists.newArrayList();
            AlbumDto albumDto;

            return result;
        } catch (Exception e){
            log.error("find album fail, error = {}", Throwables.getStackTraceAsString(e));
            throw new ResponseException("album.find.fail");
        }
    }

}
