package com.qf.rabbitmq.simple;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author chenzetao
 * @Date 2020/1/13
 */
@Component
public class Receive {

    @RabbitListener(queues = "springboot-simple-queue")
    public void receive(String msg){
        System.out.println("接收到的信息: "+msg);
    }
}
