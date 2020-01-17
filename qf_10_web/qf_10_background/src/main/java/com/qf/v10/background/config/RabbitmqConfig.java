package com.qf.v10.background.config;

import com.qf.v10.rabbitmqContant.RabbitmqContant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author chenzetao
 * @Date 2020/1/14
 */
@Configuration
public class RabbitmqConfig {

    //定义交换机
    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange(RabbitmqContant.BACKGROUND_TOPIC_EXCHANGE);
    }
}
