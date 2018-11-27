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
@ApiModel(description = "页面展示用的用户数据对象")
public class FollowRelationVO implements Serializable {
	private static final long serialVersionUID = -3080156210814707416L;

	@Getter
	@Setter
	@ApiModelProperty("关注人")
	private Long followerId;

	@Getter
	@Setter
	@ApiModelProperty("被关注人")
	private Long followeeId;

	@Getter
	@Setter
	@ApiModelProperty("是否订阅")
	private Boolean isSubscribe;

	@Getter
	@Setter
	@ApiModelProperty("关注人或被关注人名称")
	private String name;

	@Getter
	@Setter
	@ApiModelProperty("关注人或被关注人头像")
	private String avatar;
}
