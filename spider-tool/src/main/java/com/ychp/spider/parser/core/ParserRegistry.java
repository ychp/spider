package com.ychp.spider.parser.core;

import com.ychp.spider.parser.BaseParser;
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
public class ParserRegistry {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, BaseParser> parserBeans;

    @PostConstruct
    public void init(){
        registerActions();
    }

    private void registerActions() {
        parserBeans = applicationContext.getBeansOfType(BaseParser.class);
    }

    /**
     * 获取相应名称的动作
     * @param name Bean id
     * @return Action
     */
    public BaseParser getParser(String name){
        return parserBeans.get(name);
    }

}
