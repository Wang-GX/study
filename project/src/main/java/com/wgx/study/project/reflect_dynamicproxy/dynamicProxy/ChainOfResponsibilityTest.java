package com.wgx.study.project.reflect_dynamicproxy.dynamicProxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 责任链模式测试
 * TODO https://blog.csdn.net/zixiao_ying/article/details/79358708
 * 模拟场景：一张审批单需要通过组长->经理->总经理层层审批
 */
public class ChainOfResponsibilityTest {
    public static void main(String[] args) {
        JdkDynamicProxyChainOfResponsibility jdkDynamicProxyChainOfResponsibility = new JdkDynamicProxyChainOfResponsibility();
//        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy();
//        JdkDynamicProxyInterface proxy1 = (JdkDynamicProxyInterface)jdkDynamicProxy.bind(new JdkDynamicProxyTarget());
//        JdkDynamicProxyInterface proxy2 = (JdkDynamicProxyInterface)jdkDynamicProxy.bind(proxy1);
//        JdkDynamicProxyInterface proxy3 = (JdkDynamicProxyInterface)jdkDynamicProxy.bind(proxy2);

        //TODO 如果使用同一个JdkDynamicProxyChainOfResponsibility对象去实现层层代理，生成的proxy1和jdkDynamicProxyChainOfResponsibility对象是同一个对象，在调用bind方法的this.target = target时，会调用toString方法栈溢出(此处还没搞明白!)
        //创建JdkDynamicProxyTarget对象的代理对象proxy1
        JdkDynamicProxyInterface proxy1 = (JdkDynamicProxyInterface)jdkDynamicProxyChainOfResponsibility.bind(new JdkDynamicProxyTarget(), "com.wgx.study.project.reflect_dynamicproxy.dynamicProxy.Interceptor1");
        //创建JdkDynamicProxyTarget对象的代理对象proxy2
        JdkDynamicProxyInterface proxy2 = (JdkDynamicProxyInterface)jdkDynamicProxyChainOfResponsibility.bind(proxy1,"com.wgx.study.project.reflect_dynamicproxy.dynamicProxy.Interceptor2");
        //创建JdkDynamicProxyTarget对象的代理对象proxy3
        JdkDynamicProxyInterface proxy3 = (JdkDynamicProxyInterface)jdkDynamicProxyChainOfResponsibility.bind(proxy2,"com.wgx.study.project.reflect_dynamicproxy.dynamicProxy.Interceptor3");
        proxy3.sayHello();

    }
}





/**
 * 定义拦截器接口
 */
interface Interceptor {
    public boolean before(Object proxy, Object target, Method method, Object[] args);

    public void around(Object proxy, Object target, Method method, Object[] args);

    public void after(Object proxy, Object target, Method method, Object[] args);
}

/**
 * 拦截器1
 */
class Interceptor1 implements Interceptor {

    @Override
    public boolean before(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("拦截器1的before方法");
        return true;
    }

    @Override
    public void around(Object proxy, Object target, Method method, Object[] args) {
    }

    @Override
    public void after(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("拦截器1的after方法");
    }
}

/**
 * 拦截器2
 */
class Interceptor2 implements Interceptor {

    @Override
    public boolean before(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("拦截器2的before方法");
        return true;
    }

    @Override
    public void around(Object proxy, Object target, Method method, Object[] args) {
    }

    @Override
    public void after(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("拦截器2的after方法");
    }
}

/**
 * 拦截器3
 */
class Interceptor3 implements Interceptor {

    @Override
    public boolean before(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("拦截器3的before方法");
        return true;
    }

    @Override
    public void around(Object proxy, Object target, Method method, Object[] args) {
    }

    @Override
    public void after(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("拦截器3的after方法");
    }
}


