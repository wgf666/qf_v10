package com.qf.service.config;

import com.qf.v10.rabbitmqContant.RabbitmqContant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author chenzetao
 * @Date 2020/1/14
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue queueOne(){
        return new Queue(RabbitmqContant.SEARCH_QUEUE,true,false,false,null);
    }

    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange(RabbitmqContant.BACKGROUND_TOPIC_EXCHANGE);
    }

    @Bean
    public Binding bind(TopicExchange getTopicExchange,Queue queueOne){
        return BindingBuilder.bind(queueOne).to(getTopicExchange).with("product.*");
    }



}
