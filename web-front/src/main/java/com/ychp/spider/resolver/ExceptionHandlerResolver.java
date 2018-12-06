package com.ychp.spider.resolver;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.ychp.common.exception.ResponseException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Desc:
 *
 * @author yingchengpeng
 * Date: 16/7/31
 */
public class ExceptionHandlerResolver extends ExceptionHandlerExceptionResolver {

    private String defaultErrorView;

    @PostConstruct
    public void init() {
        if (Strings.isNullOrEmpty(this.defaultErrorView)) {
            this.defaultErrorView = "/error";
        }
    }

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           HandlerMethod handlerMethod,
                                                           Exception exception) {

        if (handlerMethod == null) {
            return null;
        }

        Method method = handlerMethod.getMethod();

        if (method == null) {
            return null;
        }

        ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);
        RestController restController = AnnotationUtils.findAnnotation(handlerMethod.getBean().getClass(), RestController.class);
        if (responseBodyAnn != null || restController != null) {
            try {
                int status = 500;
                String message = exception.getMessage();
                if (exception instanceof ResponseException) {
                    status = ((ResponseException) exception).getStatus();
                }
                ResponseStatus responseStatusAnn = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
                if (responseStatusAnn != null) {
                    return new ModelAndView(this.defaultErrorView,
                            ImmutableMap.of("status", responseStatusAnn.value().value(), "message", responseStatusAnn.reason()));

                }
                PrintWriter out = null;
                try {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(status);
                    out = response.getWriter();
                    out.print(message);
                    return null;
                } catch (Exception e) {
                    return null;
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
