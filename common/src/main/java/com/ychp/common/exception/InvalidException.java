package com.ychp.common.exception;

import lombok.Getter;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 2017/8/27
 */
public class InvalidException extends RuntimeException {

    private static final long serialVersionUID = -8674253694601200912L;

    @Getter
    private final String errorCode;
    @Getter
    private final String paramKey;
    @Getter
    private final Object param;

    public InvalidException(String errorCode, String paramKey, Object param) {
        super(errorCode);
        this.errorCode = errorCode;
        this.paramKey = paramKey;
        this.param = param;
    }

}
