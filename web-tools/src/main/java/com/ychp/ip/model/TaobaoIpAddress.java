package com.ychp.ip.model;

import lombok.Data;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Data
public class TaobaoIpAddress {

    private Integer code;

    private TabaoData data;

    @Data
    public class TabaoData{

        private String country;

        private String country_id;

        private String area;

        private String area_id;

        private String region;

        private String region_id;

        private String city;

        private String city_id;

        private String county;

        private String county_id;

        private String isp;

        private String isp_id;

        private String ip;
    }
}
