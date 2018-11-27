package com.ychp.user.service;

import com.ychp.user.bean.query.UserSummaryCriteria;
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
     * @param id 主键id
     * @return 查询结果
     */
    UserSummary findById(Long id);

    /**
     * 根据条件获取分页
     *
     * @param criteria 条件
     * @return 分页结果
     */
    Paging<UserSummary> paging(UserSummaryCriteria criteria);

}