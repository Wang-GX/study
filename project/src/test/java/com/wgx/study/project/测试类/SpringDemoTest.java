package com.wgx.study.project.测试类;

import com.wgx.study.project.common.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})//Spring项目，通过读取*.xml配置文件加载Spring的IOC容器
public class SpringDemoTest {

    @Autowired
    private User user;

    @Test
    public void test(){
        System.out.println(user);
    }

}
