package com.wgx.study.project.SpringBoot自动配置;/*
package com.example.demo.SpringBoot自动配置;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

//@Configuration
public class HikaricpPoolConfiguration {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    //因为如果IOC容器中有DataSource对象，那么默认配置(HikariCP数据源)就不会生效，所以这里为了注入多个DataSource对象，需要手动进行配置以及注入到IOC容器中
    @Bean("HikaricpPool")
    @Primary
    public DataSource getHikaricpPool(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }
}
*/
