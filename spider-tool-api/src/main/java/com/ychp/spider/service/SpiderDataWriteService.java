package com.ychp.spider.service;


import com.ychp.spider.model.SpiderData;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public interface SpiderDataWriteService {

    Boolean create(SpiderData spiderData);

    Boolean creates(List<SpiderData> spiderDatas);

    Boolean update(SpiderData spiderData);

    Boolean updateStatus(Long id, Integer status);

    Boolean delete(Long id);

    Boolean deleteBy(Long taskId);
}
