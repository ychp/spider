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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RuleCriteria extends PagingCriteria {
    private static final long serialVersionUID = 3912105131436001335L;

    private Integer status;
}
