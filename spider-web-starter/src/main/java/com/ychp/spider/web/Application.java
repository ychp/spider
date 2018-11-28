package com.ychp.spider.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@EnableWebMvc
@SpringBootApplication
public class Application {

    public static void main(String[] args){
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }


}
