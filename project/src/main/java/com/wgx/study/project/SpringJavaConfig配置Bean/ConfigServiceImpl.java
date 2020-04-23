package com.wgx.study.project.SpringJavaConfig配置Bean;

import com.wgx.study.project.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfigServiceImpl implements ConfigService {

    /**
     * @@Autowired和@Qualifier注解(Spring的注解)：
     * @Autowired注解：通过类型注入SpringIOC容器中的bean
     * @Autowired+@Qualifier注解：通过类型+bean的名字注入SpringIOC容器中的bean
     *
     * @Resource注解(JavaWeb的注解)：
     * @Resource有两个重要的属性：name和type，指定name时通过bean的名字注入，指定type时通过类型注入。都不指定时通过该注解标识的属性名注入SpringIOC容器中的同名bean
     */

    @Autowired
    @Qualifier("userByDefaultConfig")
    private User userByDefaultConfig;

    @Resource(name = "userByCustomizeConfig")
    private User userByCustomizeConfig;

    @Override
    public void getUserByDefaultConfig() {
        System.out.println(userByDefaultConfig);
    }

    @Override
    public void getUserByCustomizeConfig() {
        System.out.println(userByCustomizeConfig);
    }
}
