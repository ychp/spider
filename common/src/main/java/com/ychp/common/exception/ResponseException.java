package com.ychp.common.exception;

import lombok.Getter;

/**
 * @author yingchengpeng
 * @date 2018-08-10
 */
public class ResponseException extends RuntimeException {

    private static final long serialVersionUID = -8674253694601200912L;

    @Getter
    private final String errorCode;
    @Getter
    private final Integer status;

    public ResponseException(String errorCode) {
        this(errorCode, 500, errorCode);
    }

    public ResponseException(Integer status, String errorCode) {
        this(errorCode, status, errorCode);
    }

    public ResponseException(String errorCode, String message) {
        this(errorCode, 500, message);
    }

    public ResponseException(String errorCode, Integer status, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public ResponseException(String errorCode, String message, Throwable cause) {
        this(errorCode, 500, message, cause);
    }

    public ResponseException(String errorCode, Integer status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorCode = errorCode;
    }
}
