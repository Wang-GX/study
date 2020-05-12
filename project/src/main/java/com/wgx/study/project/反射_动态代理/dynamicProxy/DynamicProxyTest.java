package com.wgx.study.project.反射_动态代理.dynamicProxy;

public class DynamicProxyTest {

    public static void main(String[] args) {
        //1、测试Jdk实现动态代理
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy();
        //使用真实对象实现的接口声明代理对象的类型
        JdkDynamicProxyInterface proxy1 = (JdkDynamicProxyInterface)jdkDynamicProxy.bind(new JdkDynamicProxyTarget());
        //代理对象调用方法
        String str1 = proxy1.sayHello();
        System.out.println(str1);

        //1、测试cglib实现动态代理
        CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy();
        CglibDynamicProxyTarget proxy2 = (CglibDynamicProxyTarget)cglibDynamicProxy.bind(CglibDynamicProxyTarget.class);
        //代理对象调用方法
        String str2 = proxy2.sayHello();
        System.out.println(str2);

    }
}



