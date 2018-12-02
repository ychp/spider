package com.ychp.spider.model;

import com.ychp.spider.enums.DataStatus;
import com.ychp.spider.enums.DataType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@ToString
@EqualsAndHashCode(of = {"url", "content", "ruleId"})
public class SpiderData implements Serializable {

    private static final long serialVersionUID = -997469089147821243L;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long userId;

    @Getter
    @Setter
    private Long ruleId;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String source;

    @Getter
    @Setter
    private Integer type;

    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    private Integer level;

    @Getter
    @Setter
    private Integer status;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

    public String getStatusStr(){
        return DataStatus.getDescFromValue(this.status);
    }

    public String getTypeStr(){
        return DataType.getDescFromValue(this.type);
    }

}
