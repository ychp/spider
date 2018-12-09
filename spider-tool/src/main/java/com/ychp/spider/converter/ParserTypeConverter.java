package com.ychp.spider.converter;

import com.ychp.spider.bean.response.ParserTypeInfo;
import com.ychp.spider.model.ParserType;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yingchengpeng
 * @date 2018/12/9
 */
public class ParserTypeConverter {

    private static final BeanCopier PARSER_TYPE_COPIER = BeanCopier.create(ParserType.class, ParserTypeInfo.class, false);

    public static ParserTypeInfo convert(ParserType parserType) {
        if(parserType == null) {
            return null;
        }
        ParserTypeInfo typeInfo = new ParserTypeInfo();
        PARSER_TYPE_COPIER.copy(parserType, typeInfo, null);
        return typeInfo;
    }

    public static List<ParserTypeInfo> convert(List<ParserType> parserTypes) {
        return parserTypes.stream().map(ParserTypeConverter::convert).collect(Collectors.toList());
    }
}
