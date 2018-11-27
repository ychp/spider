package com.ychp.ip.model;

import lombok.Data;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Data
public class IpAddress {

    private Boolean success;

    private String country;

    private String province;

    private String city;

    private String isp;

    public Boolean isSuccess(){
        return success == null ? false : success;
    }
}
