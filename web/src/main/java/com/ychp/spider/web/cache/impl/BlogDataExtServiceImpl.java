package com.ychp.spider.web.cache.impl;

import com.ychp.cache.ext.DataExtService;
import com.ychp.common.util.SessionContextUtils;

import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-09-07
 */
public class BlogDataExtServiceImpl implements DataExtService {

    @Override
    public void fillExtInfo(Map<String, Object> datas) {
        datas.put("currentUser", SessionContextUtils.currentUser());
    }
}
