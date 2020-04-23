package com.wgx.study.project.多线程_本地线程锁_线程池;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "本地线程",tags = "本地线程")
@RestController
@RequestMapping("/localThread")
public class LocalLockController {

    @Autowired
    private LocalLockService service;

    /**
     * 本地线程锁测试
     */
    @ApiOperation(value = "本地线程锁测试")
    @PostMapping("/localLock")
    public void localLock(){
        service.localLockTest();
    }

}
