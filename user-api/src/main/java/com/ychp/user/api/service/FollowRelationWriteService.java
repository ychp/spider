package com.ychp.user.api.service;

import com.ychp.user.model.FollowRelation;

/**
* @author yingchengpeng
* @date 2018/10/01
*/
public interface FollowRelationWriteService {

    /**
     * 创建
     * @param followRelation 需要创建的数据
     * @return 主键
     */
    Long create(FollowRelation followRelation);

    /**
     * 更新
     * @param followRelation 需要更新的数据
     * @return 操作结果
     */
    Boolean update(FollowRelation followRelation);

    /**
     * 根据主键关注信息删除数据
     * @param followeeId 被关注人
     * @param followerId 关注人
     * @return 操作结果
     */
    Boolean delete(Long followeeId, Long followerId);
}