package com.qf.service;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Qf10SearchServiceApplicationTests {

	@Autowired
	private SolrClient client;

	@Test
	public void contextLoads() throws IOException, SolrServerException {
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id",1003);
		document.setField("product_name","周杰伦演唱会门票一张!!");
		document.setField("product_price",777);
		document.setField("product_sale_point","好听!");
		document.setField("product_image","暂无");

		client.add("collection2",document);
//		client.add(document);
//		client.commit();
		client.commit("collection2");
		System.out.println("添加到索引库成功!!");
	}

	@Test
	public void testQuery() throws IOException, SolrServerException {
		SolrQuery condition = new SolrQuery();
		//设置查询条件
		condition.setQuery("product_name:NBA");
		//查询
		QueryResponse queryResponse = client.query(condition);
		//得到查询结果
		SolrDocumentList documentList = queryResponse.getResults();
		for (SolrDocument document : documentList) {
			System.out.println(document.get("product_name"));
			System.out.println(document.get("product_price"));
			System.out.println(document.get("product_sale_point"));
			System.out.println(document.get("product_image"));
		}
	}

	@Test
	public void testDelete() throws IOException, SolrServerException {
		client.deleteById("1001");
		client.commit();
	}

}
