package com.qf.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.search.api.ISearchService;
import com.qf.v10.entity.TProduct;
import com.qf.v10.pojo.ResultBean;
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
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("initData")
    @ResponseBody
    public ResultBean init(){
       return searchService.initData();
    }


    @RequestMapping("queryByKeyWords")
    public String queryByKeyWords(String keywords, Model model){
        List<TProduct> productList = searchService.queryByKeyWords(keywords);
        model.addAttribute("list",productList);
        return "search";
    }



}
