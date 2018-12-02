package com.ychp.spider.enums;

import lombok.Getter;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
public enum ScanType {
    //
    VIDEO("video","视频"),
    IMAGE("image","图片"),
    TEXT("text","文本"),
    TAG("tag","标签");

    @Getter
    private String value;

    private String desc;

    ScanType(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString(){
        return this.desc;
    }

}
