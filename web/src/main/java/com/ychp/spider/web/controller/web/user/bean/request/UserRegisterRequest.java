package com.ychp.spider.web.controller.web.user.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@ToString
@ApiModel(description = "注册信息")
public class UserRegisterRequest implements Serializable {

	private static final long serialVersionUID = -3132782891889934402L;

	/**
	 * 用户名
	 */
	@Getter
	@Setter
	@ApiModelProperty("用户名")
	private String name;

	/**
	 * 密码
	 */
	@Getter
	@Setter
	@ApiModelProperty("密码")
	private String password;

	/**
	 * 校验码
	 */
	@Getter
	@Setter
	@ApiModelProperty("校验码")
	private String captcha;

}
