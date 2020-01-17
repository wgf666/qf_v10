package com.qf.mq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author chenzetao
 * @Date 2020/1/13
 * 消息消费者
 */
public class Receive {

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

        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("接收到的消息: "+msg);
            }
        };

        //通过rabbitmq服务器，告知已经消息了这个消息
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
