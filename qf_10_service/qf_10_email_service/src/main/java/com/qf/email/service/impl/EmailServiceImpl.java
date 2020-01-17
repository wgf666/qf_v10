package com.qf.email.service.impl;

import com.qf.email.api.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;

/**
 * @Author chenzetao
 * @Date 2020/1/14
 */
@Component
@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.address}")
    private String EMAIL_ADDRESS;

    @Override
    public String sendSimpleEmail(String to, String subject, String content) {
        //单线程的方式
        //通过线程池的方式来修改这段代码
        //要改造成多线程的方式
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EMAIL_ADDRESS);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
        return "success";
    }
}
