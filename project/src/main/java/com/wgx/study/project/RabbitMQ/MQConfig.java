package com.wgx.study.project.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {

    /**
     * TODO 注意：项目一旦启动绑定关系就确认了，如果需要改动绑定关系只能在RabbitMQ的控制台上删除掉原有的绑定关系后重新启动项目
     */

    /**
     * 绑定关系可以在配置类中完成(个人觉得这种方式更清晰)，也可以在定义生产者或者消费者时通过注解完成。
     */

    //交换机
    public static final String EXCHANGE = "EXCHANGE";

    //队列
    public static final String QUEUE_RECEIVE = "QUEUE_RECEIVE";
    public static final String QUEUE_RECEIVE_BACK = "QUEUE_RECEIVE_BACK";

    //routingKey
    public static final String routingKey_RECEIVE = "RECEIVE";
    public static final String routingKey_RECEIVE_BACK = "RECEIVE_BACK";


    /**
     * 声明交换机(Direct模式)
     *
     * @return
     */
    @Bean("exchange")
    public DirectExchange exchangeDirect() {
        //durable：持久化交换机
        //autoDelete：表示当所有绑定到此交换机的队列都不再使用时，是否自动删除此交换机
        DirectExchange directExchange = new DirectExchange(EXCHANGE, true, false);
        //设置忽略声明异常(避免创建重复队列以及创建异常的时候启动异常)
        directExchange.setIgnoreDeclarationExceptions(true);
        return directExchange;
    }

    /**
     * 声明队列
     *
     * @return
     */
    @Bean("receiveQueue")
    public Queue queueReceive() {
        //durable：持久化队列
        /*
        exclusive：是否声明该队列为排他队列
        如果一个队列被声明为排他队列，该队列仅对首次声明它的连接(Connection)可见，如果试图在一个不同的连接中重新声明或访问（如publish，consume）该排他性队列，会得到资源被锁定的错误。并在连接断开时自动删除。
        这里需要注意三点：其一，排他队列是基于连接可见的，同一连接的不同信道是可以同时访问同一个连接创建的排他队列的。
        其二，“首次”，如果一个连接已经声明了一个排他队列，其他连接是不允许建立同名的排他队列的，这个与普通队列不同。
        其三，即使该队列是持久化的，一旦连接关闭或者客户端退出，该排他队列都会被自动删除的。这种队列适用于只限于一个客户端发送读取消息的应用场景。
        */
        //autoDelete：表示当没有消费者端监听此队列时，是否自动删除此队列
        Queue queue = new Queue(QUEUE_RECEIVE, true, false, false);
        //设置忽略声明异常(避免创建重复队列以及创建异常的时候启动异常)
        queue.setIgnoreDeclarationExceptions(true);
        return queue;
    }

    @Bean("receiveBackQueue")
    public Queue queueReceiveBack() {
        //队列持久化
        Queue queue = new Queue(QUEUE_RECEIVE_BACK, true, false, false);
        //设置忽略声明异常(避免创建重复队列以及创建异常的时候启动异常)
        queue.setIgnoreDeclarationExceptions(true);
        return queue;
    }

    /**
     * 队列绑定交换机(Direct模式的绑定：需要routingKey)
     *
     * @return
     */
    @Bean
    public Binding bindingReceive() {
        return BindingBuilder.bind(queueReceive()).to(exchangeDirect()).with(routingKey_RECEIVE);
    }


    @Bean
    public Binding bindingReceiveBack() {
        return BindingBuilder.bind(queueReceiveBack()).to(exchangeDirect()).with(routingKey_RECEIVE_BACK);
    }


//    /**
//     * 声明交换机(Fanout模式)
//     * @return
//     */
//    @Bean
//    public FanoutExchange exchangeFanout() {
//        return new FanoutExchange("exchangeFanout", true, false);
//    }
//
//    @Bean
//    public Queue queueFanout() {
//        return new Queue("queueFanout", true, false, false);
//    }
//
//
//    /**
//     * 队列绑定交换机(Fanout模式的绑定：不需要routingKey)
//     *
//     * @return
//     */
//    @Bean
//    public Binding bindingFanout() {
//        return BindingBuilder.bind(queueFanout()).to(exchangeFanout());
//    }


}