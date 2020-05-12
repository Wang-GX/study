package com.wgx.study.project.反射_动态代理.reflect;

import java.lang.reflect.Method;

public class ReflectTest {
    public static void main(String[] args) throws Exception {
        //获取Class实例化对象，Class类的泛型无意义，使用？即可
        Class<?> studentClass = Class.forName("com.wgx.study.project.反射_动态代理.reflect.Student");
        Class<?> codingClass = Class.forName("com.wgx.study.project.反射_动态代理.reflect.Coding");
        System.out.println(codingClass);
        Object obj = studentClass.newInstance();
        Object o = codingClass.newInstance();
        System.out.println(o);
        Method study = studentClass.getMethod("study");
        study.invoke(obj);
    }
}

class Student extends Parent implements Coding{

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

    @Override
    public void coding() {
        System.out.println("java is the best language");
    }
}

class Parent {

    public void work() {
        System.out.println("good good work");
    }
}

/**
 * 敲代码接口
 */
interface Coding{

    void coding();
}
