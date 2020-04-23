package com.wgx.study.project.异步任务;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "本地异步任务",tags = "本地异步任务")
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncMethod asyncMethod;

    @PostMapping("/task")
    public void asyncTest() throws InterruptedException {
        System.out.println("当前线程名称：" + Thread.currentThread().getName());
        asyncMethod.asyncMethod();
        System.out.println("主线程执行完毕");
    }

}
