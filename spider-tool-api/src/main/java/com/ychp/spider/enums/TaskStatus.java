package com.ychp.spider.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2018/12/2
 */
public enum TaskStatus {
    //
    INIT(0,"待抓取"),
    PROCESS(1,"抓取中"),
    FINISH(2,"抓取完成");

    @Getter
    private int value;

    private String desc;

    TaskStatus(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public static TaskStatus fromValue(int value){
        for(TaskStatus status : TaskStatus.values()){
            if(Objects.equals(status.getValue(), value)){
                return status;
            }
        }
        return null;
    }

    public static String getDescFromValue(int value){
        for(TaskStatus status : TaskStatus.values()){
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
