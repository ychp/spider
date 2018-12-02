package com.ychp.spider.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
@EqualsAndHashCode(of = {"url", "content", "dataRef"})
public class SpiderItem implements Serializable {

    private static final long serialVersionUID = -5488263663945759126L;
    private String content;

    private String url;

    private Integer level;

    private String dataRef;

}
