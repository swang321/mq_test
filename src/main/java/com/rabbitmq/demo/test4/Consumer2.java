package com.rabbitmq.demo.test4;

import com.rabbitmq.client.*;
import com.rabbitmq.demo.util.FactoryUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: whh
 * @Date: 2020/6/22 16:28
 * @Description:
 */
public class Consumer2 {

    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException {

        Connection connection = FactoryUtil.getConnectionFactory();
        Channel channel = FactoryUtil.getChannel(connection);

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //设置最大服务转发消息数量
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("[2]:receive msg:" + msg);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //另外需要在每次处理完成一个消息后，手动发一次应答。
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }


}
