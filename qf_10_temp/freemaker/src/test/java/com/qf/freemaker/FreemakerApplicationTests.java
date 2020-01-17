package com.qf.freemaker;

import com.qf.freemaker.entity.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreemakerApplicationTests {

	@Autowired
	private Configuration configuration;

	@Test
	public void contextLoads() throws IOException, TemplateException {
		//获取到模板
		Template template = configuration.getTemplate("freemarker.ftl");

		Student student = new Student();
		student.setId(1001);
		student.setName("lisi");
		student.setBirthday(new Date());

		Student student2 = new Student();
		student2.setId(1002);
		student2.setName("wangwu");
		student2.setBirthday(new Date());

		List<Student> list = new ArrayList<>();
		list.add(student);
		list.add(student2);
		//设置数据
		Map<String,Object> data = new HashMap<>();
		data.put("username","zhangsan");
		data.put("student",student);
		data.put("list",list);
		data.put("age",35);
		Writer writer = new FileWriter("D:\\worksapce\\sz_1910\\qf_v10\\qf_10_temp\\freemaker\\src\\main\\resources\\templates\\freemarker.html");
		template.process(data,writer);
		System.out.println("生成静态页面成功!!");
	}

}
