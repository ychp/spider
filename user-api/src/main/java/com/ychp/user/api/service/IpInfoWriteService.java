package com.ychp.user.api.service;

import com.ychp.user.model.IpInfo;

/**
* @author yingchengpeng
* @date 2018/08/11
*/
public interface IpInfoWriteService {

    /**
     * 创建
     * @param ipInfo 需要创建的数据
     * @return 主键
     */
    Long create(IpInfo ipInfo);

}