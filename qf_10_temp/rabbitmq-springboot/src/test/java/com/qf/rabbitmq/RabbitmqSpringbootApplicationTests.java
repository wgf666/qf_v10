package com.qf.rabbitmq;

import com.qf.rabbitmq.publish.SendPublish;
import com.qf.rabbitmq.simple.Send;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqSpringbootApplicationTests {

	@Autowired
	private SendPublish send;

	@Test
	public void contextLoads() {
		send.send("springboot整合rabbitMQ!!");
	}

}
