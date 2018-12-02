package com.ychp.spider.web;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.async.subscriber.Dispatcher;
import com.ychp.async.subscriber.SubscriberRegistry;
import com.ychp.cache.CacheAutoConfiguration;
import com.ychp.cache.ext.DataExtService;
import com.ychp.common.captcha.CaptchaGenerator;
import com.ychp.file.cos.CosAutoConfiguration;
import com.ychp.session.SkySessionAutoConfiguration;
import com.ychp.spider.SpiderAutoConfiguration;
import com.ychp.spider.freemarker.FreeMarkerBeanPostProcessor;
import com.ychp.spider.resolver.ExceptionHandlerResolver;
import com.ychp.spider.web.cache.impl.BlogDataExtServiceImpl;
import com.ychp.spider.web.interceptors.LoginCheckInterceptor;
import com.ychp.spider.web.interceptors.SessionInterceptor;
import com.ychp.spring.boot.starter.redis.RedisAutoConfiguration;
import com.ychp.user.UserAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@ComponentScan
@EnableScheduling
@Configuration
@Import({CosAutoConfiguration.class,
        RedisAutoConfiguration.class,
        CacheAutoConfiguration.class,
        SkySessionAutoConfiguration.class,
        UserAutoConfiguration.class,
        SpiderAutoConfiguration.class,
        FreeMarkerBeanPostProcessor.class})
public class WebAutoConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(sessionInterceptor());
        registry.addInterceptor(new LoginCheckInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");

        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");

        super.addViewControllers(registry);
    }

    @Override
    protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new ExceptionHandlerResolver());
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }


    @Bean
    public CaptchaGenerator captchaGenerator() {
        return new CaptchaGenerator();
    }

    @Bean
    public SubscriberRegistry subscriberRegistry() {
        return new SubscriberRegistry();
    }

    @Bean
    public Dispatcher dispatcher(SubscriberRegistry subscriberRegistry) {
        return new Dispatcher(subscriberRegistry);
    }

    @Bean
    public AsyncPublisher asyncPublisher() {
        return new AsyncPublisher();
    }

    @Bean
    public DataExtService dataExtService() {
        return new BlogDataExtServiceImpl();
    }
}
