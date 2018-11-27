package com.ychp.spider.web.freemarker;

import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 * @author yingchengpeng
 * Date: 16/7/30
 */
@Primary
@Component
@ConfigurationProperties(prefix = "spring.freemarker")
public class FreemarkerProperties extends FreeMarkerProperties {

    private Map<String,Object> variables = new HashMap<String, Object>();

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
