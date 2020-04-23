package com.wgx.study.project.Lambda表达式;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String[] args) {

        List<String> list = Arrays.asList("张三", "李四", "王五", "赵六","猪八戒");
        list.forEach(System.out::println);

        //使用Stream流简化集合操作[示例]
        List<String> collect = list.stream().filter(e -> e.length() == 2).sorted().map(e -> e + "X").limit(3).collect(Collectors.toList());
        System.out.println(collect);

        //方法总结

    }
}
