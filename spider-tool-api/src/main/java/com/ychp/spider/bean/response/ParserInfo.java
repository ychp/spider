package com.ychp.spider.bean.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yingchengpeng
 * @date 2018/12/9
 */
@Data
public class ParserInfo implements Serializable {

    private static final long serialVersionUID = -8103599768992979631L;

    private Long id;

    private String name;

    private String url;

    private Long parserTypeId;

    private String parserType;

    private Date createdAt;

    private Date updatedAt;

}
