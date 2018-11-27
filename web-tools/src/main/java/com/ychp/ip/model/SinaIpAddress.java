package com.ychp.ip.model;

import lombok.Data;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Data
public class SinaIpAddress {

    private Integer ret;

    private Integer start;

    private Integer end;

    private String country;

    private String province;

    private String city;

    private String district;

    private String isp;

    private String type;

    private String desc;

}
