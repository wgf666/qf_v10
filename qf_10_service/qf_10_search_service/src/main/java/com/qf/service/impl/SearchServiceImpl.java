package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.search.api.ISearchService;
import com.qf.v10.entity.TProduct;
import com.qf.v10.mapper.TProductMapper;
import com.qf.v10.pojo.PageResultBean;
import com.qf.v10.pojo.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author chenzetao
 * @Date 2020/1/8
 */
@Service
@Component
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResultBean initData() {
        //查询数据库，返回list
        List<TProduct> list = productMapper.list();
        //赋值
        for (TProduct product : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_price",product.getPrice());
            document.setField("product_sale_point",product.getSalePoint());
            document.setField("product_image",product.getImage());

            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return ResultBean.error("添加到索引库失败");
            }
            try {
                solrClient.commit();
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return ResultBean.error("添加到索引库失败");
            }
        }

        return ResultBean.success("添加到索引库成功");
    }

    @Override
    public ResultBean updateById(Long id) {
        TProduct product = productMapper.selectByPrimaryKey(id);

            SolrInputDocument document = new SolrInputDocument();
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_price",product.getPrice());
            document.setField("product_sale_point",product.getSalePoint());
            document.setField("product_image",product.getImage());

            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return ResultBean.error("添加到索引库失败");
            }
            try {
                solrClient.commit();
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return ResultBean.error("添加到索引库失败");
            }

        return ResultBean.success("添加到索引库成功");
    }

    @Override
    public List<TProduct> queryByKeyWords(String keywords) {
        //手机
        SolrQuery queryCondition = new SolrQuery();
        if(!StringUtils.isAllEmpty(keywords)){
            queryCondition.setQuery("product_keywords:"+keywords);
        }else{
            queryCondition.setQuery("product_keywords:iphoneX");
        }
        List<TProduct> productList = null;
        //2.设置高亮信息
        queryCondition.setHighlight(true);
        queryCondition.addHighlightField("product_name");
        queryCondition.addHighlightField("product_sale_point");
        queryCondition.setHighlightSimplePre("<font color='red'>");
        queryCondition.setHighlightSimplePost("</font>");

        try {
            //查询
            QueryResponse queryResponse = solrClient.query(queryCondition);
            //得以查询结果
            SolrDocumentList documentList = queryResponse.getResults();

            productList = new ArrayList<>(documentList.size());

            //得到高亮信息
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            for (SolrDocument document : documentList) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
               /* product.setName(document.getFieldValue("product_name").toString());*/
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
               /* product.setSalePoint(document.getFieldValue("product_sale_point").toString());*/
                product.setImage(document.getFieldValue("product_image").toString());

                //得到当前某条数据的高亮信息
                Map<String, List<String>> map = highlighting.get(document.getFieldValue("id").toString());
                //得到product_name字段的高亮信息
                List<String> list = map.get("product_name");
                if(list!=null && list.size() >0 ){
                    //给这个名称重新赋值(赋的值是高亮的信息)
                    product.setName(list.get(0));
                }else{
                    product.setName(document.getFieldValue("product_name").toString());
                }

                //得到product_sale_point字段的高亮信息
                List<String> salePointList = map.get("product_sale_point");
                if(salePointList!=null && salePointList.size() >0 ){
                    //给这个名称重新赋值(赋的值是高亮的信息)
                    product.setSalePoint(salePointList.get(0));
                }else{
                    product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                }
                //把商品对象存入到集合中
                productList.add(product);

            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public PageResultBean<TProduct> queryByKeyWords(String keywords, Integer pageIndex, Integer pageSize) {

        //手机
        SolrQuery queryCondition = new SolrQuery();
        if(!StringUtils.isAllEmpty(keywords)){
            queryCondition.setQuery("product_keywords:"+keywords);
        }else{
            queryCondition.setQuery("product_keywords:iphoneX");
        }
        List<TProduct> productList = null;
        long totalCount = 0L;
                //2.设置高亮信息
        queryCondition.setHighlight(true);
        queryCondition.addHighlightField("product_name");
        queryCondition.addHighlightField("product_sale_point");
        queryCondition.setHighlightSimplePre("<font color='red'>");
        queryCondition.setHighlightSimplePost("</font>");

        //3.设置分页的参数
        //设置起始下标
        queryCondition.setStart((pageIndex-1)*pageSize);
        //设置每页显示的条数
        queryCondition.setRows(pageSize);
        try {
            //查询
            QueryResponse queryResponse = solrClient.query(queryCondition);
            //得以查询结果
            SolrDocumentList documentList = queryResponse.getResults();
            //得到查询的总条数
            totalCount = documentList.getNumFound();

            productList = new ArrayList<>(documentList.size());

            //得到高亮信息
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            for (SolrDocument document : documentList) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(document.getFieldValue("id").toString()));
               /* product.setName(document.getFieldValue("product_name").toString());*/
                product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
               /* product.setSalePoint(document.getFieldValue("product_sale_point").toString());*/
                product.setImage(document.getFieldValue("product_image").toString());

                //得到当前某条数据的高亮信息
                Map<String, List<String>> map = highlighting.get(document.getFieldValue("id").toString());
                //得到product_name字段的高亮信息
                List<String> list = map.get("product_name");
                if(list!=null && list.size() >0 ){
                    //给这个名称重新赋值(赋的值是高亮的信息)
                    product.setName(list.get(0));
                }else{
                    product.setName(document.getFieldValue("product_name").toString());
                }

                //得到product_sale_point字段的高亮信息
                List<String> salePointList = map.get("product_sale_point");
                if(salePointList!=null && salePointList.size() >0 ){
                    //给这个名称重新赋值(赋的值是高亮的信息)
                    product.setSalePoint(salePointList.get(0));
                }else{
                    product.setSalePoint(document.getFieldValue("product_sale_point").toString());
                }
                //把商品对象存入到集合中
                productList.add(product);

            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        PageResultBean resultBean = new PageResultBean();
        resultBean.setPageNum(pageIndex);
        resultBean.setPageSize(pageSize);
        resultBean.setTotal(totalCount);
        resultBean.setPages((int) (totalCount%pageSize==0?(totalCount/pageSize):(totalCount/pageSize)+1));
        resultBean.setList(productList);
        resultBean.setNavigatePages(3);
        return resultBean;
    }
}
