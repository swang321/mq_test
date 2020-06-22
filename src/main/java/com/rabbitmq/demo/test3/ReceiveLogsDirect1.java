package com.rabbitmq.demo.test3;

import com.rabbitmq.client.*;
import com.rabbitmq.demo.util.FactoryUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @Author: whh
 * @Date: 2020/6/22 15:58
 * @Description:
 */
public class ReceiveLogsDirect1 {

    private static final String THEME_TYPE = "direct";

    private static final String EXCHANGE_NAME="direct_logs";

    private static final String [] LOG_LEVEL_ARR={"debug","info","error"};


    public static void main(String[] args) throws IOException {

        Connection connection = FactoryUtil.getConnectionFactory();
        Channel channel = FactoryUtil.getChannel(connection);
        channel.exchangeDeclare(EXCHANGE_NAME,THEME_TYPE);

        // 设置日志级别
        int rand = new Random().nextInt(3);
        String severity=LOG_LEVEL_ARR[rand];
        System.out.println("-------------- "+severity);
        // 创建一个非持久的、唯一的、自动删除的队列
        String queue = channel.queueDeclare().getQueue();

        // 绑定交换器和队列
        // queueBind(String queue, String exchange, String routingKey)
        // 参数1 queue ：队列名
        // 参数2 exchange ：交换器名
        // 参数3 routingKey ：路由键名
        channel.queueBind(queue,EXCHANGE_NAME,severity);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + msg + "'");
            }
        };
        
        channel.basicConsume(queue,true,consumer);

    }

}
















