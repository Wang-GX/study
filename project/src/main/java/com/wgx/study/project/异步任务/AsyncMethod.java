package com.wgx.study.project.异步任务;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务需要单独定义一个类，并且被Spring扫描到
 * TODO 使用@Async注解出现的循环依赖问题
 */
@Component
public class AsyncMethod {

    @Async
    public void asyncMethod() throws InterruptedException {
        System.out.println("执行异步任务线程名称：" + Thread.currentThread().getName());
        Thread.sleep(4000);
        System.out.println("异步线程唤醒");
    }

}
