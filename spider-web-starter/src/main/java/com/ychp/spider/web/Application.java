package com.ychp.spider.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@AutoConfigureAfter(WebAutoConfiguration.class)
@SpringBootApplication
public class Application {

    public static void main(String[] args){
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);

    }


}
