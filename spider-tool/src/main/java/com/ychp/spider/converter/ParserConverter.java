package com.ychp.spider.converter;

import com.ychp.spider.bean.response.ParserDetailInfo;
import com.ychp.spider.bean.response.ParserInfo;
import com.ychp.spider.model.Parser;
import com.ychp.spider.model.ParserType;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @author yingchengpeng
 * @date 2018/12/9
 */
public class ParserConverter {

    private static final BeanCopier PARSER_DETAIL_COPIER = BeanCopier.create(Parser.class, ParserDetailInfo.class, false);
    private static final BeanCopier PARSER_COPIER = BeanCopier.create(Parser.class, ParserInfo.class, false);

    public static ParserDetailInfo convertDetail(Parser parser, ParserType type) {
        ParserDetailInfo parserInfo = new ParserDetailInfo();
        PARSER_DETAIL_COPIER.copy(parser, parserInfo, null);
        parserInfo.setParserType(type.getName());
        parserInfo.setParserTypeKey(type.getKey());
        return parserInfo;
    }

    public static ParserInfo convert(Parser parser, ParserType type) {
        ParserInfo parserInfo = new ParserInfo();
        PARSER_COPIER.copy(parser, parserInfo, null);
        parserInfo.setParserType(type.getName());
        return parserInfo;
    }

}
