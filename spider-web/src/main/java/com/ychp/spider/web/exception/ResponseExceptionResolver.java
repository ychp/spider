package com.ychp.spider.web.exception;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.ychp.common.exception.ResponseException;
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
import java.util.Map;
import java.util.Objects;

/**
 * @author yingchengpeng
 * @date 2017-08-27
 */
@Slf4j
@ControllerAdvice
public class ResponseExceptionResolver {

    private final MessageSource messageSource;

    @Autowired
    public ResponseExceptionResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ExceptionHandler(value = ResponseException.class)
    public ResponseEntity<String> OPErrorHandler(ResponseException se,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        Locale locale = request.getLocale();
        String uri = request.getRequestURI();
        Map<String, String[]> parameterMap = Maps.newHashMap();
        parameterMap.putAll(request.getParameterMap());
        Integer status = se.getStatus();
        if (!Objects.equals(status, 401)){
            log.error("request uri[{}] by params = {} fail, error = {}, case {}",
                        uri, parameterMap, se.getErrorCode(), Throwables.getStackTraceAsString(se));
            log.debug("ResponseException happened, locale = {}, cause = {}",
                    locale, Throwables.getStackTraceAsString(se));
        }
        String message = se.getErrorCode();
        try {
            message = messageSource.getMessage(se.getErrorCode(), null, se.getErrorCode(), locale);
        } catch (Exception e) {
            log.error("get message fail by code = {}, case {}",
                    se.getErrorCode(), Throwables.getStackTraceAsString(e));
        }
        return new ResponseEntity<>(message, HttpStatus.valueOf(se.getStatus()));
    }
}
