package com.ychp.user.api.service;

import com.ychp.user.model.DeviceInfo;

/**
* @author yingchengpeng
* @date 2018/08/11
*/
public interface DeviceInfoWriteService {

    /**
     * 创建
     * @param deviceInfo 需要创建的数据
     * @return 主键
     */
    Long create(DeviceInfo deviceInfo);

}