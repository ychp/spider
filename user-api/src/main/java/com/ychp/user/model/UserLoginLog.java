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
* @date 2018/08/09
*/
@ApiModel(description = "用户登录履历")
@ToString
@EqualsAndHashCode(of = { "userId", "ip" })
public class UserLoginLog implements Serializable {

    private static final long serialVersionUID = -8933676789498260924L;

    @Getter
    @Setter
    @ApiModelProperty("主键")
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty("用户ID")
    private Long userId;

    @Getter
    @Setter
    @ApiModelProperty("ip地址")
    private String ip;

    @Getter
    @Setter
    @ApiModelProperty("登录时间")
    private Date loginAt;

    @Getter
    @Setter
    @ApiModelProperty("注销时间")
    private Date logoutAt;

}