package com.qf.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v10.api.IProductTypeService;
import com.qf.v10.base.IBaseDao;
import com.qf.v10.base.impl.BaseServiceImpl;
import com.qf.v10.entity.TProductType;
import com.qf.v10.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author chenzetao
 * @Date 2020/1/4
 */
@Service
@Component
public class ProductTypeServiceImpl extends BaseServiceImpl<TProductType> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper productTypeMapper;

    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return productTypeMapper;
    }
}
