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
public class JsonRule extends BaseRule {

    private static final long serialVersionUID = -5943263253260163974L;

    private Boolean isPaging;

    private String pagingKey;

    private List<String> keyWord = Lists.newArrayList();

    private String dataKey;

    private List<String> contentKeys;

    private String detailKey;

    private String detailPre;

    private String dataType;

    @Override
    protected String getType() {
        return ParserTypeEnums.JSON.getValue();
    }
}
