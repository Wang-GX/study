package com.wgx.study.project.SpringBoot自动配置;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@Component：和下面的注解任选其一，如果选择这个注解，则需要删除配置类中的@EnableConfigurationProperties(MyProperties.class)注解
@Data
@ConfigurationProperties(prefix = "game.config")
@PropertySource(value = "classpath:my.properties")
public class MyProperties {

    //人物
    private String personName;
    @Value("${game.config.sex2}")
    private Integer sex = 1;
    //测试默认配置，测试OK！
    //TODO：根据SpringJavaConfig配置Bean中的描述，通过@Value(直接赋值和@Value的道理是一样的，如果同时存在，最终取通过@Value注解读取到的值)和前缀同时注入属性值时，最终使用的是通过前缀注入的值，如果前缀没有注入值，那么就使用@Value注入的值，也就是所谓的默认值。
    //即多种注入方式同时存在时最终的结果为：@ConfigurationProperties前缀注入>@Value注入>类中直接赋值
    //这也是SpringBoot的核心思想：约定由于配置。通过大量的默认配置+少量的自定义配置，来实现项目的快速开发。
    private Integer age;
    //武器
    private String weaponName;
    private Integer attackPower;
    //防具
    private String clothsName;
    private Integer defensivePower;

}




