package com.ychp.common.model.paging;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 2017/8/27
 */
public class PageInfo {

    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";

    private  Integer offset;
    private Integer limit;

    public static PageInfo of(Integer pageNo, Integer size) {
        return new PageInfo(pageNo, size);
    }

    public PageInfo(Integer pageNo, Integer size) {
        pageNo = MoreObjects.firstNonNull(pageNo, 1);
        size = MoreObjects.firstNonNull(size, 20);
        limit = size > 0 ? size : 20;
        offset = (pageNo - 1) * size;
        offset = offset > 0 ? offset : 0;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Map<String, Object> toMap() {
        return toMap(null, null);
    }

    public Map<String, Object> toMap(String offsetKey, String limitKey) {
        Map<String, Object> param = Maps.newHashMapWithExpectedSize(2);
        param.put(isNullOrEmpty(offsetKey) ? OFFSET : offsetKey, offset);
        param.put(isNullOrEmpty(limitKey) ? LIMIT : limitKey, limit);

        return param;
    }
}


