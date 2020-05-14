package com.wgx.study.project.reflect_dynamicproxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于jdk实现动态代理的类
 */
public class JdkDynamicProxyChainOfResponsibility implements InvocationHandler {

    private Object target = null;//真实对象

    private String interceptorClass = null;//拦截器的全限定名


    /**
     * 建立代理对象和真实对象的代理关系，并返回代理对象
     *
     * @param target 真实对象
     * @return 代理对象
     */
    public Object bind(Object target, String interceptorClass) {
        this.target = target;
        this.interceptorClass = interceptorClass;
        Class<?> targetClass = target.getClass();
        //arg1：真实对象的类加载器、arg2：真实对象实现的接口列表、arg3：InvocationHandler接口的实现类，定义用于执行的代理逻辑
        ClassLoader classLoader = targetClass.getClassLoader();
        Class<?>[] interfaces = targetClass.getInterfaces();
        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, this);
        return proxy;
    }

    /**
     * 代理逻辑方法
     * 代理对象调用方法时会执行此逻辑
     *
     * @param proxy  代理对象
     * @param method 被调用的方法
     * @param args   方法的参数列表
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //如果没有设置拦截器则直接反射原有方法
        if (interceptorClass == null) {
            return method.invoke(target, args);
        }
        //通过反射生成拦截器
        Interceptor interceptor = (Interceptor) Class.forName(interceptorClass).newInstance();
        //调用拦截器的前置方法
        Object result = null;
        if (interceptor.before(proxy, target, method, args)) {
            //如果返回true，则反射原有对象方法
            result = method.invoke(target, args);
        } else {
            //如果返回false，则调用拦截器的around方法
            interceptor.around(proxy, target, method, args);
        }
        //调用拦截器的后置方法
        interceptor.after(proxy, target, method, args);
        return result;

    }
}