package com.wgx.study.project.RedisTest;

import com.wgx.study.project.ProjectApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectApplication.class)
public class RedisTest {

/*
    @Autowired
    private RedisTemplate redisTemplate;

 */

    @Autowired
    private StringRedisTemplate redisTemplate;//继承自RedisTemplate<String,String>，value必须为String类型

    @Test
    public void testString(){
        ValueOperations<String, String> stringOperations = redisTemplate.opsForValue();
        //存储数据
        stringOperations.set("key","value");
        //存储数据,并设置剩余时间为5s
        stringOperations.set("key","value",5, TimeUnit.SECONDS);
        //获取数据
        String value = stringOperations.get("key");
        System.out.println(value);
        //修改数据
        stringOperations.set("key","newValue");
        //TODO 删除数据，该方法适用于所有数据类型，根据key删除，用来删除字符串类型
        redisTemplate.delete("key");

    }

    //TODO 注意：对于所有数据类型，一旦key没有对应的value，那么该key立即从redis中消失。尤其是对于hash、list、set、zset这些一个key可能对应多条数据的类型要特别注意

    @Test
    public void testHash(){
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        //存储数据
        hashOperations.put("key","hkey1","hvalue1");
        hashOperations.put("key","hkey2","hvalue2");
        //获取所有hkey
        Set<Object> hkeys = hashOperations.keys("key");
        System.out.println(hkeys);
        //获取单个hvalue
        Object hvalue = hashOperations.get("key", "hkey1");
        System.out.println(hvalue);
        //获取所有hvalue
        Map<Object, Object> hvalues = hashOperations.entries("key");
        System.out.println(hvalues);
        //修改数据
        hashOperations.put("key","hkey1","hvalue11");
        hashOperations.put("key","hkey2","hvalue22");
        //删除数据，该方法可以批量删除。如果删除的key或hkey不存在也不会报错。
        hashOperations.delete("key","hkey1","hkey2");
    }

    @Test
    public void testList(){
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        //存储数据
        //leftPush：先存储的数据会被压到最右(下)面，被leftPushAll方法替代
        //rightPush：先存储的数据会被压到最左(上)面，被rightPushAll方法替代
        listOperations.leftPushAll("key1","value1","value2","value3");
        listOperations.rightPushAll("key2","value1","value2","value3");
        //获取指定索引位置上的数据，自左(上)向右(下)
        String value = listOperations.index("key1", 0L);
        System.out.println(value);
        //获取指定范围内的数据，可以获取list集合中所有数据
        List<String> range = listOperations.range("key1", 0L, listOperations.size("key1"));
        System.out.println(range);
        //修改指定索引位置上的数据，自左(上)向右(下)
        listOperations.set("key1",0L,"value11");
        //删除指定索引位置上的数据，该索引上的数据必须与第三个参数的值一致才能删除成功
        listOperations.remove("key1",0L,"value11");

    }

    @Test
    public void testSet(){
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        //存储数据，重复数据会被自动剔除
        setOperations.add("key","value1","value2","value3");
        //获取数据
        Set<String> value = setOperations.members("key");
        System.out.println(value);
        //修改数据：无法修改，重复调用add方法只能使set集合中的元素越来越多(重复元素不会被添加进来)
        //删除数据，该方法可以批量删除。如果删除的key或hkey不存在也不会报错。
        setOperations.remove("key","value0","value1","value2","value3");

    }

    @Test
    public void testZSet(){

    }



}
