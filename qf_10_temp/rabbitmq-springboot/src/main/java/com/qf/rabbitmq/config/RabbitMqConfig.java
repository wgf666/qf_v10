package com.qf.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author chenzetao
 * @Date 2020/1/13
 */
@Configuration
public class RabbitMqConfig {

    //定义一定义队列
    @Bean
    public Queue getQueue(){
        return new Queue("springboot-simple-queue",true,false,false,null);
    }
    @Bean
    public Queue getQueueOne(){
        return new Queue("springboot-fanout-queue-1",true,false,false,null);
    }

    @Bean
    public Queue getQueueTwo(){
        return new Queue("springboot-fanout-queue-2",true,false,false,null);
    }

    @Bean
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("springboot-fanout-exchange");
    }

    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange("springboot-topic-exchange");
    }

    //把队列绑定到交换机
    @Bean
    public Binding bind1(FanoutExchange getFanoutExchange,Queue getQueueOne){
        return BindingBuilder.bind(getQueueOne).to(getFanoutExchange);
    }

    //把队列绑定到交换机
    @Bean
    public Binding bind2(FanoutExchange getFanoutExchange,Queue getQueueTwo){
        return BindingBuilder.bind(getQueueTwo).to(getFanoutExchange);
    }

    @Bean
    public Binding bind3(TopicExchange getTopicExchange,Queue getQueueOne){
        return BindingBuilder.bind(getQueueOne).to(getTopicExchange).with("nba.*");
    }

}
