package com.ychp.spider.model;

import com.ychp.spider.enums.RuleStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
public class Rule implements Serializable {

    private static final long serialVersionUID = 8221587782850134687L;

    private Long id;

    private Long userId;

    private String name;

    private String code;

    private String url;

    private String mainImage;

    private Long parserId;

    private Integer status;

    private Integer imageCount;

    private Integer videoCount;

    private String extra;

    private Date createdAt;

    private Date updatedAt;

    public String getStatusStr(){
        return RuleStatus.getDescFromValue(this.status);
    }

}
