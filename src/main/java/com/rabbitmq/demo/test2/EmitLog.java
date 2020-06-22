package com.rabbitmq.demo.test2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.demo.util.FactoryUtil;

import java.io.IOException;

/**
 * @Author: whh
 * @Date: 2020/6/22 15:12
 * @Description:    发布订阅 模式
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";
    private static final String THEME_TYPE = "fanout";

    public static void main(String[] args) throws IOException {

        // 创建连接
        Connection connection = FactoryUtil.getConnectionFactory();
        Channel channel = FactoryUtil.getChannel(connection);

        // 指定一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME,THEME_TYPE);

        String msg ="msg log";

        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println(" [x] Sent '" + msg + "'");

        FactoryUtil.closeConnectionAndChannel(connection,channel);

    }

}
