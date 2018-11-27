package com.ychp.user.api.service;

import com.ychp.user.model.Address;

import java.util.List;

/**
* @author yingchengpeng
* @date 2018/08/10
*/
public interface AddressReadService {

    /**
     * 根据id查询
     *
     * @param id 主键id
     * @return 查询结果
     */
    Address findById(Long id);

    /**
     * 根据父节点获取数据
     *
     * @param pid 条件
     * @return 结果
     */
    List<Address> findByPid(Long pid);

}