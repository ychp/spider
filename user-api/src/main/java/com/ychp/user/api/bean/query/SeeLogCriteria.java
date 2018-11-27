package com.ychp.user.api.bean.query;

import com.ychp.common.model.paging.PagingCriteria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yingchengpeng
 * @date 2018/08/11
 */
@ApiModel(description = "查询类型")
@ToString
public class SeeLogCriteria extends PagingCriteria {

    private static final long serialVersionUID = -6916539295351897512L;

    /**
     * ip地址
     */
    @Getter
    @Setter
    @ApiModelProperty("ip地址")
    private String ip;

    /**
     * 访问页面
     */
    @Getter
    @Setter
    @ApiModelProperty("访问页面")
    private String url;

    /**
     * 请求
     */
    @Getter
    @Setter
    @ApiModelProperty("请求")
    private String uri;

}