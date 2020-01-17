package com.qf.mq.topic;

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
    private final static String EXCHANGE_NAME = "topic-exchange";

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
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //发送消息
        channel.basicPublish(EXCHANGE_NAME,"nba.com",null,"明天有NBA的比赛!".getBytes());
        channel.basicPublish(EXCHANGE_NAME,"cba.com",null,"昨天有CBA全明星赛!".getBytes());
        channel.basicPublish(EXCHANGE_NAME,"cba.java.ee",null,"昨天有CBA有java编程比赛!".getBytes());
        System.out.println("发送消息成功!");
    }
}
