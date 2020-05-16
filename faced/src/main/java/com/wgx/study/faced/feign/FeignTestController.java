package com.wgx.study.faced.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feignTest")
public class FeignTestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignTestController.class);

    @Autowired
    private FeignTestClient feignTestClient;

    @PostMapping("/hystrix/testZuulRetry")
    public String testZuulRetry(){
        LOGGER.info("testZuulRetry");
        return feignTestClient.testZuulRetry();
    }

    @PostMapping("/hystrix/testRequestTimeOutFallback")
    public String testRequestTimeOutFallback(){
        LOGGER.info("testRequestTimeOutFallback");
        return feignTestClient.testRequestTimeOutFallback();
    }

    @PostMapping("/hystrix/testCircuitBreaker/{id}")
    public String testCircuitBreaker(@PathVariable("id") Integer id){
        LOGGER.info("testCircuitBreaker");
        return feignTestClient.testCircuitBreaker(id);
    }

}
