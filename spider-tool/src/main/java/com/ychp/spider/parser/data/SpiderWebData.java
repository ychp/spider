package com.ychp.spider.parser.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
public class SpiderWebData implements Serializable {

    private static final long serialVersionUID = -8511819220238823672L;

    private String content;

    private String url;

    private String source;

    private Integer type;

    private String path;

    private Integer level;

}
