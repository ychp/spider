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
@ApiModel(description = "用户信息")
@ToString
@EqualsAndHashCode(of = {  "userId", "homePage", "avatar", "gender", "birth",   })
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 457843353048085690L;

    @Getter
    @Setter
    @ApiModelProperty("主键")
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty("用户id")
    private Long userId;

    @Getter
    @Setter
    @ApiModelProperty("主页")
    private String homePage;

    @Getter
    @Setter
    @ApiModelProperty("头像")
    private String avatar;

    @Getter
    @Setter
    @ApiModelProperty("性别：male,female")
    private String gender;

    @Getter
    @Setter
    @ApiModelProperty("真实姓名")
    private String realName;

    @Getter
    @Setter
    @ApiModelProperty("出生日期")
    private Date birth;

    @Getter
    @Setter
    @ApiModelProperty(value = "国家ID", example = "1")
    private Long countryId;

    @Getter
    @Setter
    @ApiModelProperty(value = "省份ID", example = "11")
    private Long provinceId;

    @Getter
    @Setter
    @ApiModelProperty(value = "城市ID", example = "111")
    private Long cityId;

    @Getter
    @Setter
    @ApiModelProperty("国家")
    private String country;

    @Getter
    @Setter
    @ApiModelProperty("省份")
    private String province;

    @Getter
    @Setter
    @ApiModelProperty("城市")
    private String city;

    @Getter
    @Setter
    @ApiModelProperty("简介")
    private String synopsis;

    @Getter
    @Setter
    @ApiModelProperty("职业")
    private String profile;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

}