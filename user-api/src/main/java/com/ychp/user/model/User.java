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
* @date 2018/08/08
*/
@ApiModel(description = "用户表")
@ToString
@EqualsAndHashCode(of = { "name", "nickName", "mobile", "email", "password", "salt", "status" })
public class User implements Serializable {

    private static final long serialVersionUID = -2796900462003393210L;

    @Getter
    @Setter
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty("用户名")
    private String name;

    @Getter
    @Setter
    @ApiModelProperty("昵称")
    private String nickName;

    @Getter
    @Setter
    @ApiModelProperty("手机号码")
    private String mobile;

    @Getter
    @Setter
    @ApiModelProperty("邮箱")
    private String email;

    @Getter
    @Setter
    @ApiModelProperty("密码")
    private String password;

    @Getter
    @Setter
    @ApiModelProperty("秘钥")
    private String salt;

    @Getter
    @Setter
    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

}