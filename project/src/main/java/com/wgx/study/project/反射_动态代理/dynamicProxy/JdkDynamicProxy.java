package com.wgx.study.project.反射_动态代理.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于jdk实现动态代理的类(真实对象必须实现接口)
 */
public class JdkDynamicProxy implements InvocationHandler {

    private Object target = null;

    /**
     * 建立代理对象和真实对象的代理关系，并返回代理对象
     *
     * @param target 真实对象
     * @return 代理对象
     */
    public Object bind(Object target){
        this.target = target;
        Class<?> targetClass = target.getClass();
        //arg1：真实对象的类加载器、arg2：真实对象实现的接口列表、arg3：InvocationHandler接口的实现类，定义用于执行的代理逻辑
        Object proxy = Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), this);
        return proxy;
    }

    /**
     * 代理逻辑方法
     * 代理对象调用方法时会执行此逻辑
     *
     * @param proxy 代理对象
     * @param method 调用的方法
     * @param args 方法的参数列表
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行代理逻辑方法");
        System.out.println("调度真实对象之前的逻辑");
        //调用真实对象的方法
        Object obj = method.invoke(target, args);
        System.out.println("调度真实对象之后的逻辑");
        return obj;
    }
}


class JdkDynamicProxyTarget implements JdkDynamicProxyInterface{
    @Override
    public String sayHello() {
        String str = "Hello Java";
        System.out.println(str);
        return str;
    }
}

interface JdkDynamicProxyInterface{
    String sayHello();
}