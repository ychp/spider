package com.ychp.user.api.service;

import com.ychp.common.model.paging.Paging;
import com.ychp.user.api.bean.query.FriendLinkCriteria;
import com.ychp.user.model.FriendLink;

import java.util.List;

/**
* @author yingchengpeng
* @date 2018/08/11
*/
public interface FriendLinkReadService {

    /**
     * 根据id查询
     *
     * @param id 主键id
     * @return 查询结果
     */
    FriendLink findById(Long id);

    /**
     * 根据条件获取分页
     *
     * @param criteria 条件
     * @return 分页结果
     */
    Paging<FriendLink> paging(FriendLinkCriteria criteria);

    /**
     * 获取所有可见的数据
     * @return 数据
     */
    List<FriendLink> listVisible();
}