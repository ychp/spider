package com.ychp.user.api.bean.query;

import com.ychp.common.model.paging.PagingCriteria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yingchengpeng
 * @date 2018/10/02
 */
@ApiModel(description = "查询类型")
@ToString
public class UserSummaryCriteria extends PagingCriteria {

    private static final long serialVersionUID = -2025982018499410466L;

    @Getter
    @Setter
    @ApiModelProperty("主键")
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty("用户")
    private Long userId;

    @Getter
    @Setter
    @ApiModelProperty("关注人数量")
    private Long followerNum;

    @Getter
    @Setter
    @ApiModelProperty("粉丝数量")
    private Long fansNum;

}