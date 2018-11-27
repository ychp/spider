package com.ychp.spider.web;

import com.ychp.async.publisher.AsyncPublisher;
import com.ychp.async.subscriber.Dispatcher;
import com.ychp.async.subscriber.SubscriberRegistry;
import com.ychp.blog.BlogApiAutoConfig;
import com.ychp.blog.impl.BlogAutoConfiguration;
import com.ychp.cache.CacheAutoConfiguration;
import com.ychp.cache.ext.DataExtService;
import com.ychp.common.captcha.CaptchaGenerator;
import com.ychp.file.cos.CosAutoConfiguration;
import com.ychp.ip.IPServiceAutoConfiguration;
import com.ychp.spider.web.cache.impl.BlogDataExtServiceImpl;
import com.ychp.spider.web.interceptors.SessionInterceptor;
import com.ychp.redis.RedisAutoConfiguration;
import com.ychp.session.SkySessionAutoConfiguration;
import com.ychp.user.UserApiAutoConfig;
import com.ychp.user.UserAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@ComponentScan
@EnableWebMvc
@EnableScheduling
@Configuration
@Import({IPServiceAutoConfiguration.class,
        CosAutoConfiguration.class,
        RedisAutoConfiguration.class,
        CacheAutoConfiguration.class,
        SkySessionAutoConfiguration.class,
        UserApiAutoConfig.class,
        UserAutoConfiguration.class,
        BlogApiAutoConfig.class,
        BlogAutoConfiguration.class})
public class WebAutoConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(sessionInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
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
