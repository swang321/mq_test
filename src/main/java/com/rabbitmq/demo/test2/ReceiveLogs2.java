package com.rabbitmq.demo.test2;

import com.rabbitmq.client.*;
import com.rabbitmq.demo.util.FactoryUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: whh
 * @Date: 2020/6/22 15:25
 * @Description:
 */
public class ReceiveLogs2 {

    private static final String EXCHANGE_NAME = "logs";
    private static final String THEME_TYPE = "fanout";

    public static void main(String[] args) throws IOException {

        Connection connection = FactoryUtil.getConnectionFactory();
        Channel channel = FactoryUtil.getChannel(connection);
        //指定一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME,THEME_TYPE);
        // 创建一个非持久的 唯一的自动删除的队列
        String queueName = channel.queueDeclare().getQueue();

        // 绑定交换器和队列
        // queueBind(String queue, String exchange, String routingKey)
        // 参数1 queue ：队列名
        // 参数2 exchange ：交换器名
        // 参数3 routingKey ：路由键名
        channel.queueBind(queueName,EXCHANGE_NAME,"");

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + msg + "'");
            }
        };

        channel.basicConsume(queueName,true,consumer);

    }

}
