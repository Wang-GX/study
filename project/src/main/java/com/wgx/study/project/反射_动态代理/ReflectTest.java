package com.wgx.study.project.反射_动态代理;

import io.swagger.models.auth.In;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectTest {
    public static void main(String[] args) throws Exception {
        //获取Class实例化对象，Class类的泛型无意义，使用？即可
        Class<?> studentClass = Class.forName("com.wgx.study.project.反射_动态代理.Student");
        Object obj = studentClass.newInstance();
        Method study = studentClass.getMethod("study");
        study.invoke(obj);

    }
}

class Student extends Parent {

    private String name;
    private Integer age;

    public Student() {
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void study() {
        System.out.println("good good study");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Parent {

    public void work() {
        System.out.println("good good work");
    }
}
