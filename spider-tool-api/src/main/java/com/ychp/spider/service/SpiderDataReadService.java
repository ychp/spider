package com.ychp.spider.service;

import com.ychp.common.model.paging.Paging;
import com.ychp.spider.bean.request.DataCriteria;
import com.ychp.spider.dto.AlbumDto;
import com.ychp.spider.model.SpiderData;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface SpiderDataReadService {

    SpiderData findOneById(Long id);

    Paging<SpiderData> paging(DataCriteria criteria);

    List<AlbumDto> findAlbum();

}
