package com.wgx.study.project.RabbitMQ;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 消息接收者aop
 * 接收消息成功时更新消息为消费成功
 */
@Aspect
@Component
public class RabbitMQHandlerAop {
}
