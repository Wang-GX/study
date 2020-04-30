package com.wgx.study.project.SpringCloud;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@DefaultProperties(defaultFallback = "defaultFallBack")//可以在类上指明统一的失败降级方法
public class HystrixService {

    /**
     * 测试请求超时
     *
     * @return
     */
    //@HystrixCommand注解加在方法上表示对该方法使用降级或熔断
    //fallbackMethod：指定降级方法
    //@HystrixProperty注解了设置hystrix属性的方法。可以在HystrixPropertiesManager类中找到对应的name值
    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            //设置该方法触发降级的超时时间为5s
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),//特定配置的优先级高于配置文件中的全局配置
    })
    public String testRequestTimeOutFallback() {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {

        }
        return "SUCCESS";
    }

    @HystrixCommand
    public String testRequestErrorAndDefaultFallback(Integer id) {
        if (true){
            throw new RuntimeException();
        }
        return "SUCCESS";
    }

    @HystrixCommand(fallbackMethod = "circuitBreakerFallBack", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启熔断机制
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),//触发熔断的最小请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//熔断器从打开状态到半开状态的休眠时长
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),//触发熔断的失败请求最小占比
    })
    public String testCircuitBreaker(Integer id) {
        if (id < 0){
            throw new RuntimeException();
        }
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String fallback() {
        return "ERROR";
    }

    //注意：默认降级方法不能有参数，此时原方法可以有参数。但是返回值类型仍然必须和原方法保持一致
    public String defaultFallBack() {
        return "默认提示：对不起，网络太拥挤了！";
    }

    //注意：降级方法的参数和返回值类型必须和原方法保持一致
    public String circuitBreakerFallBack(Integer id) {
        return "id 不能为负数，请稍后再试，id:" + id;
    }


    public String testZuulRetry() {
        try {
            Thread.sleep(30000);
        } catch (Exception e) {

        }
        return null;
    }
}
