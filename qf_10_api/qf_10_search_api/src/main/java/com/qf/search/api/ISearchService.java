package com.qf.search.api;

import com.qf.v10.entity.TProduct;
import com.qf.v10.pojo.PageResultBean;
import com.qf.v10.pojo.ResultBean;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2020/1/8
 */
public interface ISearchService {
    ResultBean initData();

    ResultBean updateById(Long id);

    List<TProduct> queryByKeyWords(String keywords);

    PageResultBean<TProduct> queryByKeyWords(String keywords, Integer pageIndex, Integer pageSize);
}
