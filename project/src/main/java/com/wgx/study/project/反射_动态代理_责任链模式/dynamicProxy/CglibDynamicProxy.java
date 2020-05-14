package com.wgx.study.project.反射_动态代理_责任链模式.dynamicProxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 基于jdk实现动态代理的类
 */
public class CglibDynamicProxy implements MethodInterceptor {

    /**
     * 建立代理对象和真实对象的代理关系，并返回代理对象
     *
     * @param targetClass 真实对象的Class对象
     * @return 代理对象
     */
    public Object bind(Class targetClass) {
        //cglib的字节码增强器
        Enhancer enhancer = new Enhancer();
        //设置创建代理对象的类型
        enhancer.setSuperclass(targetClass);
        //MethodInterceptor接口的实现类，定义用于执行的代理逻辑
        enhancer.setCallback(this);
        //创建代理对象
        Object proxy = enhancer.create();
        return proxy;
    }

    /**
     * 代理逻辑方法
     * 代理对象调用方法时会执行此逻辑
     *
     * @param proxy 代理对象
     * @param method 调用的方法
     * @param args 方法的参数列表
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("执行代理逻辑方法");
        System.out.println("调度真实对象之前的逻辑");
        //调用真实对象的方法
        Object obj = methodProxy.invokeSuper(proxy, args);
        System.out.println("调度真实对象之后的逻辑");
        return obj;
    }
}

class CglibDynamicProxyTarget {

    public String sayHello() {
        String str = "Hello Java";
        System.out.println(str);
        return str;
    }
}

class CglibDynamicProxyTest{
    public static void main(String[] args) {
        //1、测试cglib实现动态代理
        CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy();
        CglibDynamicProxyTarget proxy2 = (CglibDynamicProxyTarget)cglibDynamicProxy.bind(CglibDynamicProxyTarget.class);
        //代理对象调用方法
        String str2 = proxy2.sayHello();
        System.out.println(str2);
    }
}

