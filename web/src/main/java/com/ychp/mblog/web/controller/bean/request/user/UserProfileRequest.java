package com.ychp.spider.web.controller.bean.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@ToString
@ApiModel(description = "需要保存的用户信息")
public class UserProfileRequest implements Serializable {

	private static final long serialVersionUID = -3132782891889934402L;

	/**
	 * 昵称
	 */
	@Getter
	@Setter
	@ApiModelProperty("昵称")
	private String nickName;

	/**
	 * 主页
	 */
	@Getter
	@Setter
	@ApiModelProperty("主页")
	private String homePage;

	/**
	 * 头像
	 */
	@Getter
	@Setter
	@ApiModelProperty("头像")
	private String avatar;

	/**
	 * 性别：male,female
	 */
	@Getter
	@Setter
	@ApiModelProperty("性别：male,female")
	private String gender;

	/**
	 * 真实姓名
	 */
	@Getter
	@Setter
	@ApiModelProperty("真实姓名")
	private String realName;

	/**
	 * 出生日期
	 */
	@Getter
	@Setter
	@ApiModelProperty("出生日期")
	private Date birth;

	/**
	 * 国家ID
	 */
	@Getter
	@Setter
	@ApiModelProperty(value = "国家ID", example = "1")
	private Long countryId;

	/**
	 * 省份ID
	 */
	@Getter
	@Setter
	@ApiModelProperty(value = "省份ID", example = "11")
	private Long provinceId;

	/**
	 * 城市ID
	 */
	@Getter
	@Setter
	@ApiModelProperty(value = "城市ID", example = "111")
	private Long cityId;

	/**
	 * 国家
	 */
	@Getter
	@Setter
	@ApiModelProperty("国家")
	private String country;

	/**
	 * 省份
	 */
	@Getter
	@Setter
	@ApiModelProperty("省份")
	private String province;

	/**
	 * 城市
	 */
	@Getter
	@Setter
	@ApiModelProperty("城市")
	private String city;

	/**
	 * 简介
	 */
	@Getter
	@Setter
	@ApiModelProperty("简介")
	private String synopsis;

	/**
	 * 职业
	 */
	@Getter
	@Setter
	@ApiModelProperty("职业")
	private String profile;
}
