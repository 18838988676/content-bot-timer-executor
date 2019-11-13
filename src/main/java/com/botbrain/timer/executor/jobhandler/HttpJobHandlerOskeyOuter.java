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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
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
    private RestTemplate innerRestTemplate;
    @Autowired
    private ConfigFeignClient configFeignClient;
    @Autowired
    private ThreadPoolTaskExecutor jobService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        // http://attention/v1/inner/{os_key}/update/topic/content
        //http://localhost:8839/attention/v1/inner/2WIQRCZAPA/update/topic/content

        // request
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        try {
            // connection
            URL realUrl = new URL(param);
            connection = (HttpURLConnection) realUrl.openConnection();

            // connection setting
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");

            // do connection
            connection.connect();

            //Map<String, List<String>> map = connection.getHeaderFields();


            List<Map<String, Object>> osList = configFeignClient.findAll(3, "").getData();

            List<Future<Response>> resultList = new ArrayList<>();
            for (Map<String, Object> dataOsInfo : osList) {
                resultList.add(jobService.submit(() -> {
                    ResponseEntity<Response> responseEntity = innerRestTemplate.getForEntity(param, Response.class);

                    return responseEntity.getBody();
                }));
            }

            StringBuilder msg = new StringBuilder();
            for (Future<Response> responseFuture : resultList) {
                Response result = responseFuture.get();

            }




            return SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log(e);
            return FAIL;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e2) {
                XxlJobLogger.log(e2);
            }
        }

    }

}
