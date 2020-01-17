package com.qf.rabbitmq.simple;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author chenzetao
 * @Date 2020/1/13
 * 消息生产者
 */
@Component
public class Send {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String msg){
        rabbitTemplate.convertAndSend("springboot-simple-queue",msg);
    }

}
