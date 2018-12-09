package com.ychp.spider.bean.request;

import com.ychp.common.model.paging.PagingCriteria;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author yingchengpeng
 * @date 2018/12/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataCriteria extends PagingCriteria {
    private static final long serialVersionUID = 7890645802598061688L;

    private Long userId;

    private Long taskId;

    private Integer status;

    private Integer type;
}
