package com.qf.mq.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author chenzetao
 * @Date 2020/1/13
 * 消息的生产者
 */
public class Send {

    //定义一个交换机的名称
    private final static String EXCHANGE_NAME = "fanout-exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //连接上rabbitmq的服务器
        ConnectionFactory factory = new ConnectionFactory();
        //设置连接服务器的参数
        factory.setHost("192.168.206.130");
        factory.setVirtualHost("/czt");
        factory.setUsername("czt");
        factory.setPassword("123");
        factory.setPort(5672);

        //得到连接对象
        Connection connection = factory.newConnection();
        //基本连接对象到了channel，类似session
        Channel channel = connection.createChannel();
        //声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //发送消息
        for (int i = 1; i <= 10; i++) {
            String msg ="学习的第"+i+"种方式!";
            channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        }
        System.out.println("发送消息成功!");
    }
}
