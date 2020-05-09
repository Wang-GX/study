package com.wgx.study.project.common.controller;

import com.wgx.study.project.SpringAOP_SpringMVC拦截器_自定义注解.LogAspect;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Controller-->声明当前接口为普通接口，如果没有添加@ResponseBody注解，则接口返回数据渲染后的页面(依据是MVC的视图解析器)。
 * @RestController-->声明当前接口为Rest风格接口，接口返回json数据而非渲染后的页面。此时视图解析器不会生效。
 */
@Api(value = "公共接口",tags = "公共接口")
@RestController
@RequestMapping("/common")
public class CommonController {

    @LogAspect.MyAnnotation(name = "张三",score = {80})
    @ApiOperation(value = "SpringAOP测试")
    @PostMapping("/springAop")
    public void springAopTest() {
        System.out.println(1 / 0);
        System.out.println("测试Spring AOP");
    }
}
