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
@ApiModel(description = "访问记录信息")
public class SeeLogRequest implements Serializable {

	private static final long serialVersionUID = 3636653204641921531L;
	@ApiModelProperty("完整的访问地址")
	private String url;

	@ApiModelProperty("访问路径")
	private String uri;
}
