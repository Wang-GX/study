package com.wgx.study.project.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.wgx.study.project.common.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessageHandler {

    @RabbitListener(queues = MQConfig.QUEUE_RECEIVE)
    //消费者的参数类型必须要与生产者发送的消息类型一致
    //convertAndSend方法的可发送消息的类型为Object
    public void receive(Message message, Channel channel) throws IOException {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            //System.out.println(1/0);
            //这里的messageId对应的就是发送消息时封装的CorrelationData对象的id属性
            String messageId = (String) message.getMessageProperties().getHeaders().get("messageId");
            String payload = new String(message.getBody());
            log.info("领取消费消息，消息index：" + deliveryTag + "，消息id：" + messageId + "，消息内容：" + payload);
            channel.basicAck(deliveryTag, true);

        } catch (Exception e) {

            e.printStackTrace();
            channel.basicNack(deliveryTag, true, false);
        }
    }

    @RabbitListener(queues = MQConfig.QUEUE_RECEIVE_BACK)
    public void receiveBack(Message message, Channel channel) throws IOException {

        //获取消息(在RabbitMQ队列中的)索引
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {

            //获取消息id
            String messageId = message.getMessageProperties().getMessageId();
            //String correlationId = message.getMessageProperties().getCorrelationId();//通过这个方法获取不到，目前只能在生产者回调函数中才能获取

            //获取消息内容
            //String payload = new String(message.getBody());//如果消息是字符串
            User payload = (User) SerializationUtils.deserialize(message.getBody());//如果消息是对象

            //System.out.println(1/0);

            log.info("领取回退消费消息，消息index：" + deliveryTag + "，消息id：" + messageId + "，消息内容：" + payload);

            //消费者手动确认(ACK)
            //deliveryTag：消息的索引
            //false：表示只确认当前索引表示的一条消息
            //true：表示批量确认当前索引及该索引之前的所有消息
            channel.basicAck(deliveryTag, true);

        } catch (Exception e) {
            e.printStackTrace();

            //消费者否认消息：该方法只能否认当前索引表示的一条消息
            //requeue(参数2)：被拒绝的消息是否重新投递到队列。false表示不将被否认消息重新放回队列，直接丢弃。true表示将被否认的消息重新放回队列，重新消费。
            //channel.basicReject(deliveryTag, false);

            //消费者否认消息：该方法可以批量否认
            //multiple(参数2)：是否批量，requeue(参数3)：被拒绝的消息是否重新投递到队列(同上)。
            channel.basicNack(deliveryTag, true, false);
        }
    }

    @RabbitListener(queues = MQDelayQueueConfig.DEAD_LETTER_QUEUE)
    public void delay(Message message, Channel channel) throws IOException {
        //获取消息(在RabbitMQ队列中的)索引
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            String payload = new String(message.getBody());
            System.out.println("已接收来自死信队列的延迟消息：" + payload);
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(deliveryTag, true, false);
        }
    }
}


/**
 * 从messageProperties获取消息唯一id，到redis中查询该id是否存在，以此来判断是否要消费这条消息
 * 如果该id存在，说明这条消息还没有被消费过，消费这条消息
 * 如果该id不存在，说明这条消息已经被消费过，不消费这条消息
 */