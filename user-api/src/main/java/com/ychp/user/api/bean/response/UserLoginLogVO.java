package com.ychp.user.api.bean.response;

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
@ApiModel(description = "页面展示用的用户登录数据对象")
public class UserLoginLogVO implements Serializable {
	private static final long serialVersionUID = -3080156210814707416L;

	@Getter
	@Setter
	private Long id;

	/**
	 * 用户ID
	 */
	@Getter
	@Setter
	@ApiModelProperty(value = "用户ID", example = "1")
	private Long userId;

	/**
	 * 用户名
	 */
	@Getter
	@Setter
	@ApiModelProperty("用户名")
	private String name;

	/**
	 * 昵称
	 */
	@Getter
	@Setter
	@ApiModelProperty("昵称")
	private String nickName;

	/**
	 * ip
	 */
	@Getter
	@Setter
	@ApiModelProperty("ip")
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
