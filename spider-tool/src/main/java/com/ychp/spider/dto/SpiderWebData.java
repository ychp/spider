package com.ychp.spider.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
public class SpiderWebData implements Serializable {

    private static final long serialVersionUID = -8511819220238823672L;

    private String dataRef;

    private String content;

    private String url;

    private String source;

    private String keyword;

    private String type;

    private Integer level;
}
