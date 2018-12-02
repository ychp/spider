package com.ychp.spider.image.core;

import com.ychp.spider.image.tool.Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
@Component
public class WriterRegistry {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, Writer> parserBeans;

    @PostConstruct
    public void init(){
        registerActions();
    }

    private void registerActions() {
        parserBeans = applicationContext.getBeansOfType(Writer.class);
    }

    /**
     * 获取相应名称的动作
     * @param name Bean id
     * @return Action
     */
    public Writer getWriter(String name){
        return parserBeans.get(name);
    }

}
