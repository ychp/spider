package com.ychp.spider.model;

import com.google.common.collect.Sets;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
public class ParserNode implements Serializable {

    private static final long serialVersionUID = 6872742791794333660L;

    private Long id;

    private Long userId;

    private String name;

    private String key;

    private String videoTag;

    private String imageTag;

    private String textTag;

    private String subTag;

    private Date createdAt;

    private Date updatedAt;

    public String getTags() {
        StringBuilder tags = new StringBuilder();
        Set<String> set = Sets.newHashSet();
        if (!StringUtils.isEmpty(this.videoTag)) {
            set.addAll(Arrays.asList(this.videoTag.split(",")));
        }
        if (!StringUtils.isEmpty(this.imageTag)) {
            set.addAll(Arrays.asList(this.imageTag.split(",")));
        }
        if (!StringUtils.isEmpty(this.textTag)) {
            set.addAll(Arrays.asList(this.textTag.split(",")));
        }
        if (!StringUtils.isEmpty(this.subTag)) {
            set.addAll(Arrays.asList(this.subTag.split(",")));
        }
        for (String str : set) {
            tags.append(str).append(",");
        }

        if (tags.length() == 0) {
            return "";
        }
        return tags.substring(0, tags.length() - 1);
    }
}
