package com.qf.rabbitmq.publish;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author chenzetao
 * @Date 2020/1/13
 * 消息生产者
 */
@Component
public class SendPublish {

    @Autowired
    private RabbitTemplate rabbitTemplate;

  /*  public void send(String msg){

        rabbitTemplate.convertAndSend("springboot-fanout-exchange","",msg);
    }*/

    public void send(String msg){

        rabbitTemplate.convertAndSend("springboot-topic-exchange","nba.com",msg);
    }

}
