package com.ychp.user.api.service;

import com.ychp.user.api.bean.query.UserSummaryCriteria;
import com.ychp.user.model.UserSummary;
import com.ychp.common.model.paging.Paging;

/**
* @author yingchengpeng
* @date 2018/10/02
*/
public interface UserSummaryReadService {

    /**
     * 根据id查询
     *
     * @param userId 用户ID
     * @return 查询结果
     */
    UserSummary findByUserId(Long userId);

    /**
     * 根据条件获取分页
     *
     * @param criteria 条件
     * @return 分页结果
     */
    Paging<UserSummary> paging(UserSummaryCriteria criteria);

}