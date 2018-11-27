package com.ychp.user.api.service;

import com.ychp.user.model.IpInfo;

import java.util.List;

/**
* @author yingchengpeng
* @date 2018/08/11
*/
public interface IpInfoReadService {

    /**
     * 获取ip信息
     *
     * @param ip ip地址
     * @return 结果
     */
    IpInfo findByIp(String ip);

    /**
     * 获取ip信息
     *
     * @param ips ip地址
     * @return 结果
     */
    List<IpInfo> findByIps(List<String> ips);

}