package com.ychp.user.api.service;

import com.ychp.user.model.DeviceInfo;

import java.util.List;

/**
* @author yingchengpeng
* @date 2018/08/11
*/
public interface DeviceInfoReadService {

    /**
     * 获取设备信息
     *
     * @param deviceInfo 设备信息
     * @return 结果
     */
    DeviceInfo findByUniqueInfo(DeviceInfo deviceInfo);

    /**
     * 根据id集合获取数据
     * @param ids id集合
     * @return 数据
     */
    List<DeviceInfo> findByIds(List<Long> ids);
}