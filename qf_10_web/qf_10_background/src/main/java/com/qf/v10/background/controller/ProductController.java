package com.qf.v10.background.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.qf.item.api.ItemService;
import com.qf.search.api.ISearchService;
import com.qf.v10.api.IProductService;
import com.qf.v10.entity.TProduct;
import com.qf.v10.rabbitmqContant.RabbitmqContant;
import com.qf.v10.vo.ProductVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2020/1/4
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;

    @Reference
    private ISearchService searchService;

    @Reference
    private ItemService itemService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("list")
    public String list(Model model){
        List<TProduct> list = productService.list();
        model.addAttribute("list",list);
        return "product/list";
    }

    @RequestMapping("page/{startIndex}/{pageSize}")
    public String page(Model model,
                       @PathVariable Integer startIndex,
                       @PathVariable Integer pageSize){
        PageInfo<TProduct> pageInfo = productService.getPage(startIndex,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "product/list";
    }

    //添加商品信息
    @PostMapping("add")
    public String add(ProductVO productVO){
        //返回添加该商品后的商品ID,后续有用
        Long productId = productService.add(productVO);
        //发送消息
        rabbitTemplate.convertAndSend(RabbitmqContant.BACKGROUND_TOPIC_EXCHANGE,"product.add",productId);
        //id-->mapper-->product-->document-->索引库
        //做增量复制的操作
//        searchService.updateById(productId);
        //生成详情页页面
//        itemService.createHtmlById(productId);
        return "redirect:/product/page/1/1";
    }
}
