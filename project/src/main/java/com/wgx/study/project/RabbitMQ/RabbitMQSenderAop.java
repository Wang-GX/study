package com.wgx.study.project.RabbitMQ;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 消息发送者aop
 * 发送消息成功时保存消息到数据库
 */
@Aspect
@Component
public class RabbitMQSenderAop {

    private ThreadLocal<String> methodNameLocalThread = new ThreadLocal<>();
    private static Logger logger = LoggerFactory.getLogger(RabbitMQSenderAop.class);

    @Pointcut("execution(* com.example.demo.RabbitMQ.*Sender.*(..))")
    public void pointcut() {
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint) {

        //获取方法名，并与当前线程进行绑定，执行结束后清除数据
        methodNameLocalThread.set(joinPoint.getSignature().getName());
        //获取方法参数
        Object[] args = joinPoint.getArgs();
        //logger.info(String.format("参数1:%s，参数2:%s，参数3:%s",args[0],args[1],args[2]));
        logger.info(String.format("参数1:%s，参数2:%s，参数3:%s",1,2,3));

        methodNameLocalThread.remove();
    }
}
