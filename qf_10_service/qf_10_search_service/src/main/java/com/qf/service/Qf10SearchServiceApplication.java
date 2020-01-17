package com.qf.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.qf.v10.mapper")
public class Qf10SearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Qf10SearchServiceApplication.class, args);
	}

}
