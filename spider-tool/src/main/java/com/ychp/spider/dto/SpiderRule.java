package com.ychp.spider.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
public class SpiderRule implements Serializable {

    private static final long serialVersionUID = -5943263253260163974L;

    private String urlRegex;

    private List<String> keyWord = Lists.newArrayList();

    private List<String> tags = Lists.newArrayList();

    private List<String> videoTag = Lists.newArrayList();

    private List<String> imageTag = Lists.newArrayList();

    private List<String> textTag = Lists.newArrayList();

    private List<String> subTag = Lists.newArrayList();

    private String keyWords;

}
