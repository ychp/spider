package com.ychp.ip.model;

import lombok.Data;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Data
public class BaiduIpAddress {

    private Integer errNum;

    private String errMsg;

    private RetData retData;

    @Data
    public class RetData{

        private String ip;

        private String country;

        private String province;

        private String city;

        private String district;

        private String carrier;
    }
}
