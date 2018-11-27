package com.ychp.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
* @author yingchengpeng
* @date 2018/10/02
*/
@ApiModel(description = "用户数据统计")
@ToString
@EqualsAndHashCode(of = { "userId", "followerNum", "fansNum",})
public class UserSummary implements Serializable {

    private static final long serialVersionUID = -2539232267941671372L;

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

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

}