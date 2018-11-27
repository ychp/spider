package com.ychp.user.api.bean.query;

import com.ychp.common.model.paging.PagingCriteria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yingchengpeng
 * @date 2018/08/09
 */
@ApiModel(description = "登录日志查询类型")
@ToString
public class UserLoginLogCriteria extends PagingCriteria {

    private static final long serialVersionUID = 6924174147673966776L;

    /**
     * 用户ID
     */
    @Getter
    @Setter
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    /**
     * ip地址
     */
    @Getter
    @Setter
    @ApiModelProperty("ip地址")
    private String ip;


}