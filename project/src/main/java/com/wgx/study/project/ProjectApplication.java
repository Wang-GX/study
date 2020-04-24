package com.wgx.study.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@MapperScan(basePackages = "com.wgx.study.project.common.mapper")
//@EnableScheduling//开启@Scheduled注解标识的定时任务
/**
 * 注意：这里有一个坑
 * 如果只使用@Repository注解标记Mapper接口，但是没有在引导类上添加@MapperScan注解扫描这个Mapper接口，启动时会报错。错误信息为找不到这个Mapper接口，注入失败。
 * 错误原因：@Repository是Spring提供的注解，用来标注dao层对象而不是接口！如：
 * @Repository
 * public class UserDaoImpl implements UserDao{...}
 * 如果使用Mapper接口的动态代理对象来操作数据库，那么这个注解就已经没用了，将他标注在Mapper接口上本来就是错误的用法，即使IDEA会显示出一个Spring bean的标记。
 * 但是接口是不能创建对象的，所以无法注入到IOC容器中。TODO 所以正确的使用方式是：在引导类上添加@MapperScan注解扫描Mapper接口，而Mapper接口上什么注解都不需要添加。
 * 而@MapperScan是Mybatis提供的注解，它的作用就是扫描指定包下的映射器(包括Mapper接口和xml映射文件)中的Mapper接口，生成基于接口的动态代理对象并注入到IOC容器中，
 * 然后通过@Autowired注解注入到Service层，以此来操作数据库。如：
 * @Autowired
 * private UserMapper userMapper;//注意这里使用的是多态，即实际注入的是IOC容器中的动态代理对象，时刻要明确，接口是不能创建对象的，通过多态引用的一定是它的实现类对象
 *
 */
//@EnableEurekaClient
@EnableDiscoveryClient
//@EnableHystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
@SpringBootApplication
//@SpringCloudApplication //等效于@EnableDiscoveryClient+@EnableCircuitBreaker+@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

}
