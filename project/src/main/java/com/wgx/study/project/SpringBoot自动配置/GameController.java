package com.wgx.study.project.SpringBoot自动配置;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "SpringBoot自动配置", tags = "SpringBoot自动配置")
@RestController
@RequestMapping("/springbootAutoConfigBean")
public class GameController {

    @Autowired
    private Person person;

    @ApiOperation(value = "SpringBoot自动配置对象show")
    @PostMapping("show")
    public void show(){
        System.out.println(person);
    }
}
