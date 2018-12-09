package com.ychp.spider.bean.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yingchengpeng
 * @date 2018/12/9
 */
@Data
public class TaskDetailInfo implements Serializable {

    private static final long serialVersionUID = -8103599768992979631L;

    private Long id;

    private Long userId;

    private Long parserId;

    private String name;

    private String url;

    private String spiderRule;

    private Integer status;

    private String statusStr;

    private Date createdAt;

    private Date updatedAt;

}
