package com.ychp.spider.web.exception;

import com.google.common.base.Throwables;
import com.ychp.common.exception.InvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author yingchengpeng
 * @date 2017-08-27
 */
@Slf4j
@ControllerAdvice
public class InvalidExceptionResolver {

    private final MessageSource messageSource;

    @Autowired
    public InvalidExceptionResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ExceptionHandler(value = InvalidException.class)
    public ResponseEntity<String> OPErrorHandler(InvalidException se,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        Locale locale = request.getLocale();
        String uri = request.getRequestURI();
        log.error("request uri[{}] by error {} = {} fail, case {}", uri, se.getParamKey(), se.getParam(), Throwables.getStackTraceAsString(se));
        log.debug("ResponseException happened, locale={}, cause={}", locale, Throwables.getStackTraceAsString(se));
        String message = se.getErrorCode();
        try {
            message = messageSource.getMessage(se.getErrorCode(), null, se.getErrorCode(), locale);
        } catch (Exception e) {
            log.error("get message fail by code = {}, case {}", se.getErrorCode(), Throwables.getStackTraceAsString(e));
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
