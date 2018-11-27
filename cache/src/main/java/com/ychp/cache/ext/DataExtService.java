package com.ychp.cache.ext;

import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-09-07
 */
public interface DataExtService {

    /**
     * 填充自定义数据
     * @param datas 数据集
     */
    void fillExtInfo(Map<String, Object> datas);
}
