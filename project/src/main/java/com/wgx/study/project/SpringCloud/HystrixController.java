package com.wgx.study.project.SpringCloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Hystrix分布式微服务降级或熔断", tags = "Hystrix分布式微服务降级或熔断")
@Slf4j
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private HystrixService hystrixService;

    @PostMapping("/testRequestTimeOutFallback")
    public String testRequestTimeOutFallback() {
        return hystrixService.testRequestTimeOutFallback();
    }

    @PostMapping("/testRequestTimeOutDefaultFallBack/{id}")
    public String testRequestErrorAndDefaultFallback(@PathVariable("id") Integer id) {
        return hystrixService.testRequestErrorAndDefaultFallback(id);
    }

    @PostMapping("/testCircuitBreaker/{id}")
    public String testCircuitBreaker(@PathVariable("id") Integer id) {
        return hystrixService.testCircuitBreaker(id);
    }

    /**
     * 该方法用于测试网关的Ribbon重试配置是否生效
     * 该方法用于测试网关的fallback是否生效
     * 如果未开启重试，则网关请求project服务时由于请求超时执行网关fallback回调
     * 如果开启了重试，则网关请求project服务时由于请求超时会先进行重试，最终执行网关fallback回调
     *
     * @return
     */
    @PostMapping("/testZuulRetry")
    public String testZuulRetry() {
        log.info("testZuulRetry");
        return hystrixService.testZuulRetry();
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
