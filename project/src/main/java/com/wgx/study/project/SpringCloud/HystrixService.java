package com.wgx.study.project.SpringCloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

@Service
public class HystrixService {

    /**
     * 测试请求超时
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback")
    public String testRequestTimeOut(){
        try {
            Thread.sleep(3000);
        }catch (Exception e){

        }
        return "SUCCESS";
    }

    public String fallback(){
        return "ERROR";
    }

}
