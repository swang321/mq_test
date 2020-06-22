package com.rabbitmq.demo.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: whh
 * @Date: 2020/6/22 15:13
 * @Description:
 */
public class FactoryUtil {

    /**
     * 创建一个链接
     */
    public static Connection getConnectionFactory()  {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 的主机名
        factory.setHost("localhost");
        // 创建一个连接
        try {
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException();
        }
    }

    /**
     * 通过 connection 获取 channel
     */
    public static Channel getChannel(Connection connection) {
        try {
            return connection.createChannel();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * 关闭
     */
    public static void closeConnectionAndChannel(Connection connection, Channel channel)  {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
