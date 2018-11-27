package com.ychp.user.api.bean.query;

import com.ychp.common.model.paging.PagingCriteria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yingchengpeng
 * @date 2018/10/01
 */
@ApiModel(description = "关注关系查询类型")
@ToString
public class FollowRelationCriteria extends PagingCriteria {

    private static final long serialVersionUID = -1349147420116643307L;

    @Getter
    @Setter
    @ApiModelProperty("关注人")
    private Long followerId;

    @Getter
    @Setter
    @ApiModelProperty("被关注人")
    private Long followeeId;

}