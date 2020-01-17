package com.qf.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.v10.api.IProductService;
import com.qf.v10.base.IBaseDao;
import com.qf.v10.base.impl.BaseServiceImpl;
import com.qf.v10.entity.TProduct;
import com.qf.v10.entity.TProductDesc;
import com.qf.v10.mapper.TProductDescMapper;
import com.qf.v10.mapper.TProductMapper;
import com.qf.v10.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author chenzetao
 * @Date 2020/1/4
 */
@Service
@Component
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private TProductDescMapper productDescMapper;

    @Override
    public IBaseDao<TProduct> getBaseDao() {
        return productMapper;
    }

    @Override
    public PageInfo<TProduct> getPage(Integer startIndex, Integer pageSize) {
        PageHelper.startPage(startIndex,pageSize);
        List<TProduct> list = productMapper.list();
        PageInfo<TProduct> pageInfo = new PageInfo<TProduct>(list,3);
        return pageInfo;
    }

    @Override
    public Long add(ProductVO productVO) {
        //添加商品信息，返回该商品ID(主键回填)
        TProduct product = productVO.getProduct();
        product.setFlag(true);
        product.setCreateTime(new Date());
        product.setUpdateTime(product.getCreateTime());
        product.setCreateUser(1L);
        product.setUpdateUser(product.getCreateUser());
        productMapper.insertSelective(product);
        //添加商品描述信息
        TProductDesc desc = new TProductDesc();
        desc.setProductId(product.getId());
        desc.setProductDesc(productVO.getProductDesc());
        productDescMapper.insert(desc);
        return product.getId();
    }
}
