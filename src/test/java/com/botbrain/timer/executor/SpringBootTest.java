package com.botbrain.timer.executor;


import com.botbrain.response.Response;
import com.botbrain.sdk.inner.client.config.ConfigFeignClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Copyright：botBrain.ai
 * Author: WangMingChao
 * Date: 2019/11/13.
 * Description:
 */
public class SpringBootTest {

//
    @Test
    public  void test2(){
        RestTemplate restTemplate=new RestTemplate();
        System.out.println(restTemplate.getForEntity("http://attention/v1/inner/DONG86N2WG/update/topic/content", Response.class));
    }
}
