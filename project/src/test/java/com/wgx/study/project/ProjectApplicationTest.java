package com.wgx.study.project;

import com.wgx.study.project.common.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(classes = ProjectApplication.class)
public class ProjectApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Test
    public void contextLoads() {
        System.out.println(userMapper.getUser());
    }

    @Test
    public void testRedisCommand(){
        System.out.println(redisTemplate.opsForValue().setIfAbsent("key", new Date().toString(), 3600L, TimeUnit.SECONDS));
    }

}
