package com.wgx.study.project.SpringJavaConfig配置Bean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Spring配置bean",tags = "Spring配置bean")
@RestController
@RequestMapping("/springConfigBean")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 读取默认配置文件application-{profile}.properties
     * @return
     */
    @ApiOperation(value = "读取默认配置文件装配bean")
    @PostMapping(value = "default")
    public void getUserByDefaultConfig(){
        configService.getUserByDefaultConfig();
    }

    /**
     * 读取自定义配置文件my.properties
     * @return
     */
    @ApiOperation(value = "读取自定义配置文件装配bean")
    @PostMapping(value = "customize")
    public void getUserByCustomizeConfig(){
        configService.getUserByCustomizeConfig();
    }

}
