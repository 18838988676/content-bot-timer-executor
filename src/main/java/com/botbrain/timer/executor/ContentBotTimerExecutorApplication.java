package com.botbrain.timer.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ContentBotTimerExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentBotTimerExecutorApplication.class, args);
    }

}
