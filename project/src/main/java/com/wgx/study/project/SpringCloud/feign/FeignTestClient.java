package com.wgx.study.project.SpringCloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("project-service")//映射到对应的服务中(Eureka的客户端)
public interface FeignTestClient {

    @PostMapping("hystrix/testZuulRetry")//映射到对应服务的指定路径(Feign客户端集成了Ribbon负载均衡)
    String testZuulRetry();
}
