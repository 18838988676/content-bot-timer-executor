package com.botbrain.timer.executor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Copyright：botBrain.ai
 * Author: WangMingChao
 * Date: 2019/11/13.
 * Description:
 */
@Configuration
public class ExecutorServiceConfigure {

    @Bean
    public ThreadPoolTaskExecutor jobExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(200);
        executor.setMaxPoolSize(300);
        executor.setQueueCapacity(0);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("job");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        return executor;
    }

}
