package com.ychp.spider.parser.rule;

import com.google.common.collect.Lists;
import com.ychp.spider.parser.enums.ParserTypeEnums;
import lombok.Data;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
public class HtmlRule extends BaseRule {

    private static final long serialVersionUID = -5943263253260163974L;

    private List<String> keyWord = Lists.newArrayList();

    private List<String> tags = Lists.newArrayList();

    private List<String> videoTag = Lists.newArrayList();

    private List<String> imageTag = Lists.newArrayList();

    private List<String> textTag = Lists.newArrayList();

    private List<String> subTag = Lists.newArrayList();

    private String pagingKey;

    private String offsetKey;

    private Integer limit;

    private String firstPageKey;

    @Override
    protected String getType() {
        return ParserTypeEnums.HTML.getValue();
    }
}
