package com.qf.product.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.qf.v10.mapper")
public class QfV10ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QfV10ProductServiceApplication.class, args);
	}

}
