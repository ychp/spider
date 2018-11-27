package com.ychp.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
* @author yingchengpeng
* @date 2018/08/10
*/
@ApiModel(description = "地址数据表")
@ToString
@EqualsAndHashCode(of = {  "pid", "name", "level", "pinyin", "englishName", "unicodeCode", })
public class Address implements Serializable {

    private static final long serialVersionUID = 5539654528536111859L;

    @Getter
    @Setter
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    @Getter
    @Setter
    @ApiModelProperty(value = "父级ID", example = "0")
    private Long pid;

    @Getter
    @Setter
    @ApiModelProperty("名称")
    private String name;

    @Getter
    @Setter
    @ApiModelProperty(value = "级别", example = "1")
    private Long level;

    @Getter
    @Setter
    @ApiModelProperty("拼音")
    private String pinyin;

    @Getter
    @Setter
    @ApiModelProperty("英文名")
    private String englishName;

    @Getter
    @Setter
    @ApiModelProperty("ASCII码")
    private String unicodeCode;

}