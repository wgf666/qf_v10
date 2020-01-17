package com.qf.item.service;

import com.qf.item.api.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Qf10ItemServiceApplicationTests {

	@Autowired
	private ItemService itemService;

	@Test
	public void contextLoads() {
		List<Long> idsList = new ArrayList<>();
		for (long i = 1; i <= 11; i++) {
			idsList.add(i);
		}
		itemService.createBatchHtmlById(idsList);
	}

}
