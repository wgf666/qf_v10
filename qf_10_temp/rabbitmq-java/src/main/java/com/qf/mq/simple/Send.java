package com.qf.mq.simple;

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

    //定义一个队列
    private final static String QUEUE_NAME = "simple-queue";

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
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        //发送消息
        String msg ="第一次使用rabbitmq发送消息!";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("发送消息成功!");
    }
}
