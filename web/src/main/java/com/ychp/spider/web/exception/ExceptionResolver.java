package com.ychp.spider.web.exception;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.ychp.cache.exception.CacheException;
import com.ychp.common.exception.InvalidException;
import com.ychp.common.exception.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-08-15
 */
@Slf4j
@ControllerAdvice
public class ExceptionResolver {

    private final MessageSource messageSource;

    @Autowired
    public ExceptionResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseBody
    @ExceptionHandler(value = CacheException.class)
    public Object cacheErrorHandler(CacheException ce,
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    HandlerMethod handlerMethod) {
        return errorHandle(ce, request, response, handlerMethod);
    }

    @ResponseBody
    @ExceptionHandler(value = InvalidException.class)
    public Object invalidErrorHandler(InvalidException se,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      HandlerMethod handlerMethod) {
        return errorHandle(se, request, response, handlerMethod);
    }

    @ResponseBody
    @ExceptionHandler(value = ResponseException.class)
    public Object responseErrorHandler(ResponseException re,
                                       HttpServletRequest request,
                                       HttpServletResponse response,
                                       HandlerMethod handlerMethod) {
        return errorHandle(re, request, response, handlerMethod);
    }

    private Object errorHandle(RuntimeException re,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               HandlerMethod handlerMethod) {
        Locale locale = request.getLocale();
        printErrorInfo(request, re, locale);
        String message = re.getMessage();
        try {
            message = messageSource.getMessage(re.getMessage(), null, re.getMessage(), locale);
        } catch (Exception e) {
            log.error("get message fail by code = {}, case {}", re.getMessage(), Throwables.getStackTraceAsString(e));
        }

        if (handlerMethod == null) {
            return null;
        }

        Method method = handlerMethod.getMethod();

        if (method == null) {
            return null;
        }

        ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);
        RestController restController = AnnotationUtils.findAnnotation(handlerMethod.getBean().getClass(), RestController.class);
        if (responseBodyAnn == null && restController == null) {
            try {
                ResponseStatus responseStatusAnn = AnnotationUtils.findAnnotation(re.getClass(), ResponseStatus.class);
                if (responseStatusAnn != null) {
                    return new ModelAndView("/error",
                            ImmutableMap.of("status", responseStatusAnn.value().value(), "message", responseStatusAnn.reason()));

                }
            } catch (Exception e) {
                return null;
            }
        }

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void printErrorInfo(HttpServletRequest request, Exception ex, Locale locale) {
        String uri = request.getRequestURI();
        Map<String, String> parameterMap = Maps.newHashMap();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            parameterMap.put(entry.getKey(), Joiner.on(",").join(entry.getValue()));
        }
        log.error("request uri[{}] by params = {} fail, case {}", uri, parameterMap, Throwables.getStackTraceAsString(ex));
        log.debug("Exception happened, locale={}, cause={}", locale, Throwables.getStackTraceAsString(ex));
    }
}
