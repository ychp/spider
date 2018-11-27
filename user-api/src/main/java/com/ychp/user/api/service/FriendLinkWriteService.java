package com.ychp.user.api.service;

import com.ychp.user.model.FriendLink;

/**
* @author yingchengpeng
* @date 2018/08/11
*/
public interface FriendLinkWriteService {

    /**
     * 创建
     * @param friendLink 需要创建的数据
     * @return 主键
     */
    Long create(FriendLink friendLink);

    /**
     * 更新
     * @param friendLink 需要更新的数据
     * @return 操作结果
     */
    Boolean update(FriendLink friendLink);

    /**
     * 根据主键id删除数据
     * @param id 主键
     * @return 操作结果
     */
    Boolean delete(Long id);
}