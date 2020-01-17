package com.qf.index.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v10.api.IProductTypeService;
import com.qf.v10.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2020/1/8
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("show")
    public String show(Model model){
        List<TProductType> list = productTypeService.list();
        model.addAttribute("productList",list);
        return "index";
    }

    @RequestMapping("list")
    @ResponseBody
    public List<TProductType> getList(){
       return  productTypeService.list();
    }
}
