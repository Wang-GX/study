package com.wgx.study.project.SpringJavaConfig配置Bean;

import com.wgx.study.project.common.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 读取格式为application-{profile}.properties的配置文件
 * 不需要在配置类中指定默认配置文件的位置(在application.properties中指定)
 */
@Configuration
@ConfigurationProperties(prefix = "father")//指定前缀批量注入
/**
 * 注入配置文件中的配置有两种方式：
 * 如果只是某个业务中需要获取配置文件中的某项值或者设置具体值，可以使用@Value；
 * 如果一个JavaBean中大量属性值要和配置文件进行映射，可以使用@ConfigurationProperties；
 */
public class DefaultConfig {

    @Value("${user.name}")//指定具体值单独注入
    //TODO(**) 为什么这个属性值一直都是User?
    private String userName;

    @Value("${user.sex}")
    private String userSex;

    @Value("${user.age}")
    private String userAge;

    //TODO 通过前缀+属性名注入，设置的属性必须要有set方法!!!(这里指的是配置类)，通过调用set方法注入name属性值
    private String name;

    /*public String getName() {
        return name;
    }*/

    public void setName(String name) {
        this.name = name;
    }


    @Value("${father.height2}")
    //TODO 通过@Value注入，属性名任意
    //通过@Value注入father.height2属性值，然后调用set方法注入father.height属性值，所以最终的值为father.height的属性值[通过debug观察]
    private String height;

/*    public String getHeight() {
        return height;
    }*/

    public void setHeight(String height) {
        this.height = height;
    }

    @Bean("userByDefaultConfig")
    public User getUser(){
        User user = new User();
        user.setUserName(userName);
        user.setUserSex(userSex);
        user.setUserAge(userAge);
        user.setFatherName(name);
        user.setFatherHeight(height);
        return user;
    }
}
