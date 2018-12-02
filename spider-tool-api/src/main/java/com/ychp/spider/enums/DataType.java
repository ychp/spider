package com.ychp.spider.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018/12/2
 */
public enum DataType {
    //
    VIDEO(1,"video","视频"),
    IMAGE(2,"image","图片"),
    TEXT(3,"text","文本"),
    TAG(4,"tag","标签");

    @Getter
    private int value;

    @Getter
    private String text;

    private String desc;

    DataType(int value, String text, String desc){
        this.value = value;
        this.text = text;
        this.desc = desc;
    }

    public static DataType fromValue(int value){
        for(DataType type : DataType.values()){
            if(Objects.equals(type.getValue(), value)){
                return type;
            }
        }
        return null;
    }

    public static DataType fromText(String text){
        for(DataType type : DataType.values()){
            if(Objects.equals(type.getText(), text)){
                return type;
            }
        }
        return null;
    }

    public static String getDescFromValue(int value){
        for(DataType type : DataType.values()){
            if(Objects.equals(type.getValue(), value)){
                return type.toString();
            }
        }
        return "";
    }

    @Override
    public String toString(){
        return this.desc;
    }
}
