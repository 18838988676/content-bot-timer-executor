package com.botbrain.timer.executor.jobhandler;

import com.botbrain.response.Response;
import com.botbrain.sdk.inner.client.config.ConfigFeignClient;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 跨平台Http任务  外网调用
 *
 * @author xuxueli 2018-09-16 03:48:34
 */
@JobHandler(value = "HttpJobHandlerOskeyOuter")
@Component
public class HttpJobHandlerOskeyOuter extends IJobHandler {

    @Autowired
    private RestTemplate outerRestTemplate;
    @Autowired
    private ConfigFeignClient configFeignClient;
    @Autowired
    private ThreadPoolTaskExecutor jobExecutor;

    private ReturnT res = ReturnT.SUCCESS;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        // request
        try {
            List<Future<Response>> resultList = new ArrayList<>();
                resultList.add(jobExecutor.submit(() -> {
            ResponseEntity<Response> responseEntity = outerRestTemplate.getForEntity(param, Response.class);
                    return responseEntity.getBody();
                }));
            StringBuilder msg = new StringBuilder();
            for (Future<Response> responseFuture : resultList) {
                Response result = responseFuture.get();
                System.out.println("result:" + result);
                if (!result.succeeded())
                    res = ReturnT.FAIL;
                msg.append("msg:" + result.getMsg());
                msg.append("code:" + result.getCode());
                msg.append("cost:" + result.getCost());
                msg.append("data:" + result.getData());
                msg.append("\r\n");
            }
            XxlJobLogger.log(msg.toString());
            return res;
        } catch (Exception e) {
            XxlJobLogger.log(e);
            return FAIL;
        }

    }

}
