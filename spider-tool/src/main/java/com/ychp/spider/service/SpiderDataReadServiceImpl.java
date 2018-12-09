package com.ychp.spider.service;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ychp.common.exception.ResponseException;
import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.DataCriteria;
import com.ychp.spider.dto.AlbumDto;
import com.ychp.spider.enums.RuleStatus;
import com.ychp.spider.model.Rule;
import com.ychp.spider.model.SpiderData;
import com.ychp.spider.repository.RuleRepository;
import com.ychp.spider.repository.SpiderDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Service
public class SpiderDataReadServiceImpl implements SpiderDataReadService {

    @Autowired
    private SpiderDataRepository spiderDataRepository;

    @Autowired
    private RuleRepository ruleRepository;

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

            List<Long> ids = spiderDataRepository.findFirstIds();

            if(CollectionUtils.isEmpty(ids)){
                return result;
            }
            List<SpiderData> datas = spiderDataRepository.findByIds(ids);

            List<Long> ruleIds = Lists.transform(datas, SpiderData::getRuleId);

            if(!ruleIds.isEmpty()){
                Map<Long, Map<String, Object>> countMap = spiderDataRepository.countByRule();
                List<Rule> rules = ruleRepository.findByIds(ruleIds);

                Map<Long, Rule> ruleMap = parserMap(rules);
                for(SpiderData data : datas){
                    albumDto = new AlbumDto();
                    albumDto.setRuleId(data.getRuleId());
                    if(ruleMap.get(data.getRuleId())!= null) {
                        albumDto.setStatus(ruleMap.get(data.getRuleId()).getStatus());
                        albumDto.setStatusStr(ruleMap.get(data.getRuleId()).getStatusStr());
                        albumDto.setName(ruleMap.get(data.getRuleId()).getName());
                    }
                    albumDto.setImage(data.getUrl());
                    Map<String, Object> count = countMap.get(data.getRuleId());
                    albumDto.setSize(((Long) count.get("count")).intValue());
                    if(albumDto.getStatus() == RuleStatus.DOWNLOAD.getValue() || albumDto.getStatus() == RuleStatus.DOWNLOAD_ALL.getValue()){
                       finalDatas.add(albumDto);
                    } else {
                        initDatas.add(albumDto);
                    }
                }
            }
            finalDatas.sort(Comparator.comparingInt(AlbumDto::getSize));
            initDatas.sort(Comparator.comparingInt(AlbumDto::getSize));
            result.addAll(initDatas);
            result.addAll(finalDatas);
            return result;
        } catch (Exception e){
            log.error("find album fail, error = {}", Throwables.getStackTraceAsString(e));
            throw new ResponseException("album.find.fail");
        }
    }

    @Override
    public SpiderData findBy(String content, String url, Long ruleId) {
        try {
            return spiderDataRepository.findBy(content, url, ruleId);
        } catch (Exception e){
            log.error("count data fail, content = {}, url = {}, ruleId = {}, error = {}",
                    content, url, ruleId, Throwables.getStackTraceAsString(e));
            throw new ResponseException("data.find.fail");
        }
    }

    @Override
    public List<SpiderData> findByRuleId(Long ruleId) {
        try {
            return spiderDataRepository.findByRuleId(ruleId);
        } catch (Exception e){
            log.error("count data fail, ruleId = {}, error = {}",
                    ruleId, Throwables.getStackTraceAsString(e));
            throw new ResponseException("data.find.fail");
        }
    }

    private Map<Long,Rule> parserMap(List<Rule> rules) {
        Map<Long, Rule> map = Maps.newHashMap();
        for(Rule rule : rules){
            map.put(rule.getId(), rule);
        }
        return map;
    }
}
