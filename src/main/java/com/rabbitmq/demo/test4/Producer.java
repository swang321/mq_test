package com.rabbitmq.demo.test4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.demo.util.FactoryUtil;

import java.io.IOException;

/**
 * @Author: whh
 * @Date: 2020/6/22 16:23
 * @Description: mq  轮询  每个消费者 只 能处理完一个消息才可以处理下一个消息
 *          int prefetchCount = 1;
 *         channel.basicQos(prefetchCount);
 */
public class Producer {

    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException {

        Connection connection = FactoryUtil.getConnectionFactory();
        Channel channel = FactoryUtil.getChannel(connection);

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg;

        for (int i = 0; i < 10; i++) {
            msg = "msg from producer :" + i;
            System.out.println(msg);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }

        FactoryUtil.closeConnectionAndChannel(connection, channel);

    }

}
