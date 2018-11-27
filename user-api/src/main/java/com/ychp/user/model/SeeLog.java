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
* @date 2018/08/11
*/
@ApiModel(description = "访问履历")
@ToString
@EqualsAndHashCode(of = {  "ip", "deviceId", "url", "uri", })
public class SeeLog implements Serializable {

    private static final long serialVersionUID = -8768019865836160762L;

    @Getter
    @Setter
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty("ip地址")
    private String ip;

    @Getter
    @Setter
    @ApiModelProperty(value = "设备id", example = "1")
    private Long deviceId;

    @Getter
    @Setter
    @ApiModelProperty("访问页面")
    private String url;

    @Getter
    @Setter
    @ApiModelProperty("请求")
    private String uri;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

}