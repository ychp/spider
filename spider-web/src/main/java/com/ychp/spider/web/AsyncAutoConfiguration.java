package com.ychp.spider.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncAutoConfiguration {

    /**
     * 自定义异步线程池
     * @return 线程池
     */
    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Async-Executor");
        executor.setMaxPoolSize(10);
        executor.setCorePoolSize(5);

        // 设置拒绝策略
        executor.setRejectedExecutionHandler((r, executor1) -> log.error("thread reject with name = {}", r.toString()));
        // 使用预定义的异常处理类 todo
        // executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;
    }
}
