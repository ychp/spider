package com.ychp.user.api.service;

import com.ychp.user.api.bean.request.SeeLogCreateRequest;

/**
* @author yingchengpeng
* @date 2018/08/11
*/
public interface SeeLogWriteService {

    /**
     * 创建
     * @param request 需要创建的数据
     * @return 主键
     */
    Long create(SeeLogCreateRequest request);

}