package com.qf.v10.vo;

import com.qf.v10.entity.TProduct;

import java.io.Serializable;

/**
 * @Author chenzetao
 * @Date 2020/1/6
 */
public class ProductVO implements Serializable{
    private TProduct product;
    private String productDesc;

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
