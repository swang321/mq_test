package com.rabbitmq.demo.test3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.demo.util.FactoryUtil;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: whh
 * @Date: 2020/6/22 15:53
 * @Description:     路由
 */
public class EmitLogDirect {

    private static final String THEME_TYPE = "direct";

    private static final String EXCHANGE_NAME="direct_logs";

    private static final String [] LOG_LEVEL_ARR={"debug","info","error"};

    public static void main(String[] args) throws IOException {

        Connection connection = FactoryUtil.getConnectionFactory();
        Channel channel = FactoryUtil.getChannel(connection);

        channel.exchangeDeclare(EXCHANGE_NAME,THEME_TYPE);

        for (int i = 0; i < 10; i++) {
            int rand = new Random().nextInt(3);
            String severity = LOG_LEVEL_ARR[rand];
            String msg= "send-MSG log : [" +severity+ "]" + UUID.randomUUID().toString();
            // 发布消息至交换器
            channel.basicPublish(EXCHANGE_NAME,severity,null,msg.getBytes());
            System.out.println(" [x] Sent '" + msg + "'");
        }

        FactoryUtil.closeConnectionAndChannel(connection,channel);

    }

}
