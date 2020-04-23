package com.wgx.study.project.SpringCloud;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Hystrix", tags = "Hystrix")
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private HystrixService hystrixService;

    @PostMapping("/testRequestTimeOut")
    public String testRequestTimeOut(){
        return hystrixService.testRequestTimeOut();
    }
}
