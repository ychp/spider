package com.ychp.user.api.service;

import com.ychp.user.model.UserSummary;

/**
* @author yingchengpeng
* @date 2018/10/02
*/
public interface UserSummaryWriteService {

    /**
     * 创建
     * @param userSummary 需要创建的数据
     * @return 主键
     */
    Long create(UserSummary userSummary);

    /**
     * 更新
     * @param userSummary 需要更新的数据
     * @return 操作结果
     */
    Boolean increase(UserSummary userSummary);

}