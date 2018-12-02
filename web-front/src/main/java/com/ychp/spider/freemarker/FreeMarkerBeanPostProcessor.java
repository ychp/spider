package com.ychp.spider.freemarker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Desc:
 * @author yingchengpeng
 * Date: 16/7/30
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({FreemarkerProperties.class})
public class FreeMarkerBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private FreemarkerProperties properties;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof FreeMarkerConfigurer) {
            FreeMarkerConfigurer configurer = (FreeMarkerConfigurer) bean;
            log.info("set freeMark variables = {}", properties.getVariables());
            configurer.setFreemarkerVariables(properties.getVariables());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
