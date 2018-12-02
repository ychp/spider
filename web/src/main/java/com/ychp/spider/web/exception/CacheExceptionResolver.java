package com.ychp.spider.web.exception;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.ychp.cache.exception.CacheException;
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

/**
 * @author yingchengpeng
 * @date 2018-08-15
 */
@Slf4j
@ControllerAdvice
public class CacheExceptionResolver {

    private final MessageSource messageSource;

    @Autowired
    public CacheExceptionResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ExceptionHandler(value = CacheException.class)
    public ResponseEntity<String> OPErrorHandler(CacheException ce,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        Locale locale = request.getLocale();
        String uri = request.getRequestURI();
        Map<String, String[]> parameterMap = Maps.newHashMap();
        parameterMap.putAll(request.getParameterMap());
        log.error("request uri[{}] by params = {} fail, case {}", uri, parameterMap, Throwables.getStackTraceAsString(ce));
        log.debug("ResponseException happened, locale={}, cause={}", locale, Throwables.getStackTraceAsString(ce));
        String message = ce.getMessage();
        try {
            message = messageSource.getMessage(ce.getMessage(), null, ce.getMessage(), locale);
        } catch (Exception e) {
            log.error("get message fail by code = {}, case {}", ce.getMessage(), Throwables.getStackTraceAsString(e));
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
