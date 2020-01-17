package com.qf.v10.background.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.v10.api.IProductTypeService;
import com.qf.v10.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2020/1/4
 */
@RestController
@RequestMapping("productType")
public class ProductTypeController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("list")
    public List<TProductType> list(){
        return productTypeService.list();
    }

    @RequestMapping("listByJsonP")
    public String jsonp(String callback){
        System.out.println("callback: "+callback);
        List<TProductType> list = productTypeService.list();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return callback+"("+json+")";
    }

}
