package com.wgx.study.project.反射_动态代理_责任链模式.dynamicProxy;


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
        Object result = null;
        //调用拦截器的前置方法
        //invoke执行的顺序是proxy3->proxy2->proxy1->target，所以触发前置方法的顺序是proxy3的invoke方法->proxy2的invoke方法->proxy1的invoke方法
        if (interceptor.before(proxy, target, method, args)) {
            //如果返回true，则反射原有对象方法
            result = method.invoke(target, args);
        } else {
            //如果返回false，则调用拦截器的around方法
            interceptor.around(proxy, target, method, args);
        }
        //调用拦截器的后置方法
        interceptor.after(proxy, target, method, args);
        return result;//return的顺序的target->proxy1->proxy2->proxy3，所以触发后置方法时是proxy1的invoke方法->proxy2的invoke方法->proxy3的invoke方法

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

/**
 * 责任链模式测试
 * TODO https://blog.csdn.net/zixiao_ying/article/details/79358708
 * 模拟场景：一张审批单需要通过组长->经理->总经理层层审批
 */
class ChainOfResponsibilityTest {
    public static void main(String[] args) {
        //JdkDynamicProxyChainOfResponsibility jdkDynamicProxyChainOfResponsibility = new JdkDynamicProxyChainOfResponsibility();
        //TODO 如果需要代理多次，那么每一次都需要一个新的JdkDynamicProxyChainOfResponsibility对象。因为如果使用同一个JdkDynamicProxyChainOfResponsibility对象去实现，那么在代理对象调用方法时这个对象的成员变量(target和interceptorClass)始终是不变的，所以会出现递归调用死循环的问题。
        //创建JdkDynamicProxyTarget对象的代理对象proxy1
        JdkDynamicProxyInterface proxy1 = (JdkDynamicProxyInterface) new JdkDynamicProxyChainOfResponsibility().bind(new JdkDynamicProxyTarget(), "com.wgx.study.project.反射_动态代理_责任链模式.dynamicProxy.Interceptor1");
        //创建JdkDynamicProxyTarget对象的代理对象proxy2
        JdkDynamicProxyInterface proxy2 = (JdkDynamicProxyInterface) new JdkDynamicProxyChainOfResponsibility().bind(proxy1, "com.wgx.study.project.反射_动态代理_责任链模式.dynamicProxy.Interceptor2");
        //创建JdkDynamicProxyTarget对象的代理对象proxy3
        JdkDynamicProxyInterface proxy3 = (JdkDynamicProxyInterface) new JdkDynamicProxyChainOfResponsibility().bind(proxy2, "com.wgx.study.project.反射_动态代理_责任链模式.dynamicProxy.Interceptor3");
        //如果使用同一个JdkDynamicProxyChainOfResponsibility对象，那么在本例中：
        //代理对象proxy3调用方法：->反射创建拦截器3，调用拦截器3的before方法返回true
        //->代理对象proxy2调用方法：->反射创建拦截器3，调用拦截器3的before方法返回true(正确的结果应该是反射创建拦截器2，调用拦截器2的before方法返回true，但是由于使用的是同一个JdkDynamicProxyChainOfResponsibility对象，所以成员变量是不变的，导致无法调用前一个拦截器和代理对象的方法，出现递归调用)->
        proxy3.sayHello();

    }
}