package com.rabbitmq.demo.test1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: whh
 * @Date: 2020/6/22 11:45
 * @Description:
 */
public class Send {

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
        String msg="hello mq";

        // basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
        /*
         *      exchange     交换器
         *      routingKey   路由键
         *      props        消息的其他参数
         *      body        消息体
         */
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("发送的消息  msg:"+msg);

        channel.close();

        connection.close();

    }

}
