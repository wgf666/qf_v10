package com.qf.service.consumer;

import com.qf.search.api.ISearchService;
import com.qf.v10.rabbitmqContant.RabbitmqContant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author chenzetao
 * @Date 2020/1/14
 */
@Component
public class Consumer {

    @Autowired
    private ISearchService searchService;

    @RabbitListener(queues = RabbitmqContant.SEARCH_QUEUE)
    public void receive(Long productId){
        searchService.updateById(productId);
    }
}
