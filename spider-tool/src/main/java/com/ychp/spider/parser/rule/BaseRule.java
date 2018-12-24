package com.ychp.spider.parser.rule;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Data
public abstract class BaseRule implements Serializable {

    private static final long serialVersionUID = -5943263253260163974L;

    protected abstract String getType();

}
