package com.ychp.spider.bean.request;

import com.ychp.common.model.paging.PagingCriteria;
import lombok.Data;

/**
 * @author yingchengpeng
 * @date 2018/12/2
 */
@Data
public class ParserCriteria extends PagingCriteria {
    private static final long serialVersionUID = 7890645802598061688L;

    private Long userId;
}
