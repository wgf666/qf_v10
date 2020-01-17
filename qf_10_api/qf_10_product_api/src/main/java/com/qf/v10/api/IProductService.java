package com.qf.v10.api;

import com.github.pagehelper.PageInfo;
import com.qf.v10.base.IBaseService;
import com.qf.v10.entity.TProduct;
import com.qf.v10.vo.ProductVO;

/**
 * @Author chenzetao
 * @Date 2020/1/4
 */
public interface IProductService extends IBaseService<TProduct> {
    PageInfo<TProduct> getPage(Integer startIndex, Integer pageSize);

    Long add(ProductVO productVO);
}
