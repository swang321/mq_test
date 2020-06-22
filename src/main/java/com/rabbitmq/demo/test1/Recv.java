package com.rabbitmq.demo.test1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Author: whh
 * @Date: 2020/6/22 13:42
 * @Description:
 */
public class Recv {

    private final static String QUEUE_NAME="queue_test";


    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置 主机名
        connectionFactory.setHost("localhost");
        //创建一个链接
        Connection connection = connectionFactory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        /*  指定一个队列
         *  queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
         *  queue  队列名
         *  durable 是否持久化
         *  exclusive 仅创建者可以使用的私有队列，断开后自动删除
         *  arguments 其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DefaultConsumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body, StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + msg + "'");
            }
        };

        // basicConsume(String queue, boolean autoAck, Consumer callback)
        // 参数1 queue ：队列名
        // 参数2 autoAck ： 是否自动ACK
        // 参数3 callback ： 消费者对象的一个接口，用来配置回调
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

}
