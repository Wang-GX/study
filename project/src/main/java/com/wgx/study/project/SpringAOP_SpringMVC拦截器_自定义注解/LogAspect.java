package com.wgx.study.project.SpringAOP_SpringMVC拦截器_自定义注解;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * AOP：可以切入任意方法(连接点)
 * 连接点(JoinPoint)：Spring允许你通知（Advice）的地方。
 * 切入点(Pointcut)：筛选的连接点。
 * 通知、增强处理(Advice)：就是你想要的功能。
 * 切面(Aspect)：切面是通知和切入点的结合。
 * 目标对象(targer)
 * 代理对象(proxy)
 * 织入(weaving)：将切面应用到目标对象来创建新的代理对象的过程。
 */


@Aspect
/**
 * AOP的约定流程：
 * 参考：https://www.cnblogs.com/lx-1024/p/8033765.html
 * around before-->before-->target-->around after-->after-->afterReturning
 * around before-->before-->target-->after-->afterThrowing
 *
 * @Order注解可以指定多个通知同时作用时的执行顺序
 * 数值越小则前置越先执行，后置越后执行。即如果环绕通知1的数值 < 环绕通知2的数值，则执行顺序为：
 *           环绕通知1前置-->环绕通知2前置-->目标方法-->环绕通知2后置-->环绕通知1后置
 * 可以看出这是就一个典型的责任链模式的顺序
 */
@Order(1)
@Component
public class LogAspect {

    //声明一个String类型的变量，并与当前线程进行绑定，可以通过set和get方法设置和获取值，当前线程执行结束时，需要通过remove清除值。
    private ThreadLocal<String> methodNameLocalThread = new ThreadLocal<>();

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //声明切入点(以方法为最小颗粒度)
    @Pointcut("execution(* com.wgx.study.project.common.controller..*.*(..))")
    public void pointcut() {
    }

    //声明通知类型和通知逻辑以及作用范围(通知和切入点的结合称为切面）
    //TODO 环绕通知与其他通知最好不要同时使用！执行顺序可能存在问题。优先使用其他通知。
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        //获取请求调用的类名
        Class<?> className = joinPoint.getTarget().getClass();
        //获取Method对象
        Method method = className.getMethod(joinPoint.getSignature().getName());
        //判断方法上是否存在指定注解
        MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
        if (myAnnotation != null) {
            //获取注解属性的值
            System.out.println("存在myAnnotation注解");
            System.out.println("name：" + myAnnotation.name() + "，age：" + myAnnotation.age() + "，score：" + Arrays.toString(myAnnotation.score()));
        }
        //获取方法名
        methodNameLocalThread.set(joinPoint.getSignature().getName());
        logger.info("around：" + methodNameLocalThread.get() + "方法开始执行，入参：" + JSONObject.toJSONString(joinPoint.getArgs(), SerializerFeature.WriteMapNullValue));
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error(methodNameLocalThread.get() + "方法出现异常! 异常信息为：" + throwable.getMessage());
        }
        logger.info("around：" + methodNameLocalThread.get() + "方法执行完毕，出参：" + JSONObject.toJSONString(proceed, SerializerFeature.WriteMapNullValue));
        return proceed;
    }

    @Before("pointcut()")
    //前置通知：在切入点方法执行之前执行
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()")
    //后置通知：在切入点方法执行之后执行
    public void after() {
        logger.info("after");
    }

    @AfterReturning(pointcut = "pointcut()", returning = "response")
    //返回通知：在切入点方法返回之后执行(可以理解为是回调)
    public void afterReturning(Object response) {
        logger.info("afterReturning" + response);
        methodNameLocalThread.remove();
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    //异常通知：在切入点方法抛出异常时执行
    public void afterThrowing(Throwable ex) {
        logger.info("afterThrowing" + ex);
        methodNameLocalThread.remove();
    }

    @Documented
    @Target(value = ElementType.METHOD)//注解的使用范围
    @Retention(value = RetentionPolicy.RUNTIME)//注解的生命周期
    public @interface MyAnnotation {
        String name();
        int age() default 18;
        int[] score();
    }

}
