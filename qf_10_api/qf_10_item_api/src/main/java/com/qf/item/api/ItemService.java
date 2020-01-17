package com.qf.item.api;

import com.qf.v10.pojo.ResultBean;

import java.util.List;

/**
 * @Author chenzetao
 * @Date 2020/1/10
 */
public interface ItemService {

    ResultBean createHtmlById(Long productId);

    ResultBean createBatchHtmlById(List<Long> idList);
}
