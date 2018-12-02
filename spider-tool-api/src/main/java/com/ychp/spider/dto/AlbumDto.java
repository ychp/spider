package com.ychp.spider.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
public class AlbumDto implements Serializable {

    private static final long serialVersionUID = 1475912272984105303L;
    private Long ruleId;

    private String name;

    private Integer size;

    private String image;

    private Integer status;

    private String statusStr;
}
