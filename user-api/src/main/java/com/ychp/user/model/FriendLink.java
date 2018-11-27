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
@ApiModel(description = "友情链接")
@ToString
@EqualsAndHashCode(of = {  "webName", "link", "visible", "priority", })
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 5026806616946337131L;

    @Getter
    @Setter
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty("网站名称")
    private String webName;

    @Getter
    @Setter
    @ApiModelProperty("链接")
    private String link;

    @Getter
    @Setter
    @ApiModelProperty("是否可见，0.不可见，1.可见")
    private Boolean visible;

    @Getter
    @Setter
    @ApiModelProperty(value = "优先级", example = "1")
    private Long priority;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

}