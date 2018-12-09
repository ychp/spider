package com.ychp.spider.bean.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yingchengpeng
 * @date 2018/12/9
 */
@Data
public class ParserDetailInfo implements Serializable {

    private static final long serialVersionUID = -8103599768992979631L;

    private Long id;

    private String name;

    private Long parserTypeId;

    private String parserType;

    private String parserTypeKey;

    private String spiderRule;

    private Date createdAt;

    private Date updatedAt;
}
