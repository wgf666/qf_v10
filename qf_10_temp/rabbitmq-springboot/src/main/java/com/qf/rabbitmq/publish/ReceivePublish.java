package com.qf.rabbitmq.publish;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author chenzetao
 * @Date 2020/1/13
 */
@Component
public class ReceivePublish {

    @RabbitListener(queues = "springboot-fanout-queue-1")
    public void receive1(String msg){
        System.out.println("接收到的信息: "+msg);
    }

    @RabbitListener(queues = "springboot-fanout-queue-2")
    public void receive2(String msg){
        System.out.println("接收到的信息: "+msg);
    }
}
