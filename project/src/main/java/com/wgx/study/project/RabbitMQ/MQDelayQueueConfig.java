package com.wgx.study.project.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQDelayQueueConfig {

    @Autowired
    private DirectExchange exchange;

    //交换机
    public static final String DEAD_LETTER_EXCHANGE = "DEAD_LETTER_EXCHANGE";

    //队列
    public static final String DELAY_QUEUE = "DELAY_QUEUE";
    public static final String DEAD_LETTER_QUEUE = "DEAD_LETTER_QUEUE";

    //routingKey
    public static final String routingKey_DELAY = "DELAY";
    public static final String routingKey_DEAD_LETTER = "DEAD_LETTER";

    //延迟队列TTL
    public static final int DELAY_QUEUE_TTL = 10000;

    /**
     * 声明延迟队列
     * 设置延迟队列中的消息要推送到的死信交换机
     * @return
     */
    @Bean("delayQueue")
    public Queue delayQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", DELAY_QUEUE_TTL);//设置延迟队列消息的过期时间(毫秒)，过期的消息会被发送到绑定的死信交换机(必须)
        arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);//设置死信交换机(必须)
        //可选设置：设置发送到死信队列的消息的routingKey，当消息从延迟队列发送到死信交换机时，会重置消息的routingKey为此处设置的值。如果不设置，则保持消息原来的routingKey不变。
        arguments.put("x-dead-letter-routing-key", routingKey_DEAD_LETTER);
        Queue queue = new Queue(DELAY_QUEUE, true, false, false, arguments);
        queue.setIgnoreDeclarationExceptions(true);
        return queue;
    }

    /**
     * 延迟队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding bindingDelayQueue() {
        return BindingBuilder.bind(delayQueue()).to(exchange).with(routingKey_DELAY);
    }

    /**
     * 声明死信交换机(和普通交换机没区别)
     */
    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange(){
        DirectExchange directExchange = new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
        directExchange.setIgnoreDeclarationExceptions(true);
        return directExchange;
    }

    /**
     * 声明死信队列(和普通队列没区别)
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue(){
        Queue queue = new Queue(DEAD_LETTER_QUEUE, true, false, false);
        queue.setIgnoreDeclarationExceptions(true);
        return queue;
    }

    /**
     * 死信队列绑定死信交换机
     *
     * @return
     */
    @Bean
    public Binding bindingDeadLetterQueue() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(routingKey_DEAD_LETTER);
        //return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(routingKey_DELAY);//如果不重新设置routingKey，则保持消息原来的routingKey不变。
    }
}
