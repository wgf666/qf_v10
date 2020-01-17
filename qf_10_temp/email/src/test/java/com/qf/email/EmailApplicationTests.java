package com.qf.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${email.address}")
	private String EMAIL_ADDRESS;

	@Autowired
	private TemplateEngine templateEngine;

	//发送一个普通的邮件
	@Test
	public void sendSimpleEamil() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(EMAIL_ADDRESS);
		message.setTo("734108673@qq.com");
		message.setSubject("与163企业建立深度合作!!");
		message.setText("合作愉快!<a href='https://www.baidu.com'>激活</a>");
		mailSender.send(message);
	}


	//发送一个支持HTML标签的邮件
	@Test
	public void sendHtmlEmail() throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
		helper.setFrom(EMAIL_ADDRESS);
		helper.setTo("734108673@qq.com");
		helper.setSubject("与163企业建立深度合作!!");
		helper.setText("合作愉快!<a href='https://www.baidu.com'>激活</a>",true);
		mailSender.send(mimeMessage);
		System.out.println("发送邮件成功!");
	}

	//发送一个带附件的邮件
	@Test
	public void sendAttchmentEmail() throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
		helper.setFrom(EMAIL_ADDRESS);
		helper.setTo("734108673@qq.com");
		helper.setSubject("与163企业建立深度合作!!");
		helper.setText("合作愉快!<a href='https://www.baidu.com'>激活</a>",true);

		//设置附件
		FileSystemResource resource = new FileSystemResource("D:\\worksapce\\sz_1910\\qf_v10\\qf_10_temp\\email\\a.txt");
		helper.addAttachment("a.txt",resource);
		mailSender.send(mimeMessage);
		System.out.println("发送邮件成功!");
	}



	//发送一个模板方式的邮件
	@Test
	public void sendTemplateEmail() throws MessagingException {
		//得到模板中的内容
		Context context = new Context();
		context.setVariable("username","zhangsan");
		String content = templateEngine.process("template.html", context);

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
		helper.setFrom(EMAIL_ADDRESS);
		helper.setTo("734108673@qq.com");
		helper.setSubject("与163企业建立深度合作!!");
		helper.setText(content,true);

		//设置附件
		FileSystemResource resource = new FileSystemResource("D:\\worksapce\\sz_1910\\qf_v10\\qf_10_temp\\email\\a.txt");
		helper.addAttachment("a.txt",resource);
		mailSender.send(mimeMessage);
		System.out.println("发送邮件成功!");
	}
}
