package com.wgx.study.project.SpringCloud.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feignTest")
public class FeignTestController {

    @Autowired
    private FeignTestClient feignTestClient;

    @PostMapping("/hystrix/testZuulRetry")
    public String testZuulRetry(){
        return feignTestClient.testZuulRetry();
    }
}
