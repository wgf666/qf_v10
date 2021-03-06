package com.qf.mq.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author chenzetao
 * @Date 2020/1/13
 * 消息消费者
 * 限流+手工回复
 */
public class Receive2 {

    //定义一个队列
    private final static String QUEUE_NAME = "publish-queue-2";

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
        final Channel channel = connection.createChannel();
        //表示队列中放一个消息，处理完后，再放下一个消息
        channel.basicQos(1);
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"nba.*");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"cba.#");
        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("Receive2-->接收到的消息: "+msg);

                //出现很慢的问题
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //手工回复消息
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        //通过rabbitmq服务器，告知已经消息了这个消息
//        channel.basicConsume(QUEUE_NAME,true,consumer);
        //手工回复消息给服务器
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
