package com.ychp.spider.parser.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018/12/10
 */
public enum ParserTypeEnums {

    //
    HTML("HTML","网页"),
    JSON("JSON","JSON字符串");

    @Getter
    private String value;

    private String desc;

    ParserTypeEnums(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public static ParserTypeEnums fromValue(String value){
        for(ParserTypeEnums type : ParserTypeEnums.values()){
            if(Objects.equals(type.getValue(), value)){
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return this.desc;
    }
}
