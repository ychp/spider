package com.ychp.spider.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
public class ParserType implements Serializable {

    private static final long serialVersionUID = 6872742791794333660L;

    private Long id;

    private Boolean justAdmin;

    private String name;

    private String key;

    private Date createdAt;

    private Date updatedAt;

}
