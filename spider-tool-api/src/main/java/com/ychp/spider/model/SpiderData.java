package com.ychp.spider.model;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.ychp.common.util.MD5Util;
import com.ychp.spider.enums.DataStatus;
import com.ychp.spider.enums.DataType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@ToString
@EqualsAndHashCode(of = {"uniqueCode"})
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
    private Long parserId;

    @Getter
    @Setter
    private Long taskId;

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
    private String uniqueCode;

    @Getter
    @Setter
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

    public String generateUniqueCode() {
        Map<String, String> params = Maps.newHashMapWithExpectedSize(10);
        params.put("taskId", taskId.toString());
        params.put("content", content);
        params.put("url", url);
        params.put("type", type.toString());

        String source = Joiner.on("&").withKeyValueSeparator("=").join(new TreeMap<>(params));
        return MD5Util.md5Encode(source);

    }

    public String getStatusStr() {
        return DataStatus.getDescFromValue(this.status);
    }

    public String getTypeStr() {
        return DataType.getDescFromValue(this.type);
    }

}
