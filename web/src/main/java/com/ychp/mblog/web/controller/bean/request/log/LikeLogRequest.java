package com.ychp.spider.web.controller.bean.request.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Data
@ApiModel(description = "点赞记录信息")
public class LikeLogRequest implements Serializable {

	private static final long serialVersionUID = -3648581534475512258L;

	@ApiModelProperty(value = "目标id", example = "1")
	private Long aimId;

	@ApiModelProperty(value = "目标类型:1.文章,2.说说,3.照片", example = "1")
	private Integer type;
}
