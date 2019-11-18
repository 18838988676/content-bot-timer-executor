package com.botbrain.timer.executor.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Copyright：botBrain.ai
 * Author: WangMingChao
 * Date: 2019/11/13.
 * Description:
 */
@Configurable
@Component
public class RequestClientConfigure {


    @LoadBalanced
    @Bean("innerRestTemplate")
    public RestTemplate innerRestTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(5000);
        simpleClientHttpRequestFactory.setReadTimeout(10000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    @Bean("outerRestTemplate")
    public RestTemplate outerRestTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(5000);
        simpleClientHttpRequestFactory.setReadTimeout(10000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

}
