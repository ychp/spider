package com.ychp.user.api.service;

import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.query.FollowRelationCriteria;
import com.ychp.user.model.FollowRelation;

/**
* @author yingchengpeng
* @date 2018/10/01
*/
public interface FollowRelationReadService {

    /**
     * 根据id查询
     *
     * @param id 主键id
     * @return 查询结果
     */
    FollowRelation findById(Long id);

    /**
     * 根据条件获取分页
     *
     * @param criteria 条件
     * @return 分页结果
     */
    Paging<FollowRelation> paging(FollowRelationCriteria criteria);

}