package com.wgx.study.project.SpringBoot自动配置;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

//这个类用来展示SpringIOC容器中的bean
@Component
public class SpringIocShow implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(SpringIocShow.class);

    /**
     * Spring容器会在创建该Bean之后，自动调用该Bean的setApplicationContext方法
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringIocShow.applicationContext == null) {
            SpringIocShow.applicationContext = applicationContext;
        }

        //DataSource dataSource2 = applicationContext.getBean(DataSource.class);
        //DataSource DruidPool = applicationContext.getBean("DruidPool",DataSource.class);
        //DataSource HikaricpPool = applicationContext.getBean("HikaricpPool",DataSource.class);
        //System.out.println(DruidPool);
        //System.out.println(HikaricpPool);
        //System.out.println(DruidPool.getClass());
        //System.out.println(HikaricpPool.getClass());

        logger.info("----------------------------------- SPRING IOC SHOW START-----------------------------------");

//        System.out.println("------Bean 总计:" + applicationContext.getBeanDefinitionCount());
//        String[] names = applicationContext.getBeanDefinitionNames();
//        for (String name : names) {
//            System.out.println(">>>>>>" + name);
//        }

        logger.info("------------------------------------ SPRING IOC SHOW END ------------------------------------");


    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}