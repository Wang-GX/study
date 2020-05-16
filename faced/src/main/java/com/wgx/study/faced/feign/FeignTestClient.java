package com.wgx.study.faced.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "project-service", contextId = "example1")//映射到对应的服务中(Eureka的客户端)
public interface FeignTestClient {

    /**
     * 测试通过Feign请求时网关的超时处理和重试机制
     *
     * @return
     */
    @PostMapping("hystrix/testZuulRetry")//映射到对应服务的指定路径(Feign客户端集成了Ribbon负载均衡)
    String testZuulRetry();

    /**
     * 测试通过Feign请求时降级
     *
     * @return
     */
    @PostMapping("hystrix/testRequestTimeOutFallback")//映射到对应服务的指定路径(Feign客户端集成了Ribbon负载均衡)
    String testRequestTimeOutFallback();

    /**
     * 测试通过Feign请求时熔断
     *
     * @return
     */
    @PostMapping("hystrix/testCircuitBreaker/{id}")//映射到对应服务的指定路径(Feign客户端集成了Ribbon负载均衡)
    String testCircuitBreaker(@PathVariable("id") Integer id);

}
