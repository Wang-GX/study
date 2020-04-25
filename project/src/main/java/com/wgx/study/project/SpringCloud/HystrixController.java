package com.wgx.study.project.SpringCloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Hystrix分布式微服务降级或熔断", tags = "Hystrix分布式微服务降级或熔断")
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private HystrixService hystrixService;

    @PostMapping("/testRequestTimeOut")
    public String testRequestTimeOutFallback() {
        return hystrixService.testRequestTimeOutFallback();
    }

    @PostMapping("/testRequestTimeOutDefaultFallBack")
    public String testRequestTimeOutDefaultFallback() {
        return hystrixService.testRequestTimeOutDefaultFallback();
    }

    @PostMapping("/testCircuitBreaker")
    public String testCircuitBreaker() {
        return hystrixService.testCircuitBreaker();
    }


    @PostMapping("/successRequest")
    public String successRequest() {
        return hystrixService.successRequest();
    }


    //HystrixDashboard的bean对象
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
