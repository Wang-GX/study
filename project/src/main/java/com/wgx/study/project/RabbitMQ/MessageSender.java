package com.wgx.study.project.RabbitMQ;

import com.wgx.study.project.common.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@Slf4j
public class MessageSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void receive() {

        //生成10位随机码
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);

        //构建消息
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("messageId", uuid);
        //消息持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        //TODO 该对象只有id一个属性，用来标识消息的唯一性
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(uuid);
        Message message = new Message("GM00000001".getBytes(), messageProperties);//如果消息是字符串
//        User user = new User();
//        user.setAccountNo("GM00000001");
//        Message message = new Message(SerializationUtils.serialize(user), messageProperties);//如果消息是对象

        //设置回调确认
        rabbitTemplate.setConfirmCallback(confirmCallback);

        //投递消息
        //TODO 参数说明：交换机、routingKey、消息、correlationData(存在多个重载方法)
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQConfig.routingKey_RECEIVE, message, correlationData);

    }


    public void receiveBack() {

        //设置回调确认函数
        rabbitTemplate.setConfirmCallback(confirmCallback);

        for (int i = 0; i < 10; i++) {

            //生成10位随机码
            String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);

            //构建消息
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setMessageId(uuid);
            //消息持久化
            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            CorrelationData correlationData = new CorrelationData(uuid);
            //Message message = new Message("GM00000001".getBytes(), messageProperties);//如果消息是字符串
            User user = new User();
            user.setUserName("GM00000001");
            Message message = new Message(SerializationUtils.serialize(user), messageProperties);//如果消息是对象

            //投递消息
            //TODO 参数说明：交换机、routingKey、消息、correlationData
            rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQConfig.routingKey_RECEIVE_BACK, message, correlationData);
        }

    }

    /**
     * 延迟队列测试
     */
    public void delay() {
        //构建延迟消息
        String msg = "延迟消息";
        Message message = new Message(msg.getBytes(), new MessageProperties());
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQDelayQueueConfig.routingKey_DELAY, message);
        System.out.println("已向交换机发送延迟消息：" + msg + "(TTL：" + MQDelayQueueConfig.DELAY_QUEUE_TTL + ")");
    }

    /**
     * 延迟消息测试
     */
    public void delayMsg() {
        //构建延迟消息
        String msg = "延迟消息";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("1000");//手动设置消息的过期时间(取最小的TTL，参考txt文件)
        Message message = new Message(msg.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE, MQDelayQueueConfig.routingKey_DELAY, message);
        System.out.println("已向交换机发送延迟消息：" + msg + "(TTL：" + 1000 + ")");
    }

    //声明回调函数: 生产者确认(confirm)
    //生产者投递消息后，等待RabbitMQ的ACK(确认回调)。如果没有收到或者收到失败信息，则重试。如果收到成功消息则生产者业务结束。
    private final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {

            //如果消息发送失败，可以通过CorrelationData对象的id属性值来确定这条消息
            if (ack) {
                log.info("生产者消息确认成功，消息id为：" + correlationData.getId());
            } else {
                log.info("生产者消息确认失败，消息id为：" + correlationData.getId() + "失败原因：" + cause);
            }
        }
    };
}
