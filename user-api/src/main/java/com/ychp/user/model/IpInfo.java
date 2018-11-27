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
@ApiModel(description = "ip具体信息")
@ToString
@EqualsAndHashCode(of = {  "ip", "country", "province", "city", })
public class IpInfo implements Serializable {

    private static final long serialVersionUID = -2907696866673660899L;

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
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

}