package com.ychp.spider.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018/12/2
 */
public enum RuleStatus {
    //
    INIT(0,"初始化"),
    WAITING(1,"抓取数据中"),
    FINISH(2,"抓取数据完毕"),
    DOWNLOAD(3,"下载图片数据完毕"),
    DOWNLOAD_ALL(4,"下载所有数据完毕"),
    ERROR(-1,"抓取数据出错");

    @Getter
    private int value;

    private String desc;

    RuleStatus(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public static RuleStatus fromValue(int value){
        for(RuleStatus status : RuleStatus.values()){
            if(Objects.equals(status.getValue(), value)){
                return status;
            }
        }
        return null;
    }

    public static String getDescFromValue(int value){
        for(RuleStatus status : RuleStatus.values()){
            if(Objects.equals(status.getValue(), value)){
                return status.toString();
            }
        }
        return "";
    }

    @Override
    public String toString(){
        return this.desc;
    }
}
