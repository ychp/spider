package com.ychp.spider.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018/12/2
 */
public enum DataStatus {
    //
    INIT(0,"已抓取"),
    WAITING(1,"下载数据中"),
    WRONG(-2,"链接不可用"),
    SUCCESS(3,"链接可用"),
    ERROR(-1,"下载数据失败"),
    FINISH(2,"下载数据完毕");

    @Getter
    private int value;

    private String desc;

    DataStatus(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public static DataStatus fromValue(int value){
        for(DataStatus status : DataStatus.values()){
            if(Objects.equals(status.getValue(), value)){
                return status;
            }
        }
        return null;
    }

    public static String getDescFromValue(int value){
        for(DataStatus status : DataStatus.values()){
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
