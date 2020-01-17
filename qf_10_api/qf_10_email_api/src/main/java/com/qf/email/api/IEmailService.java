package com.qf.email.api;

/**
 * @Author chenzetao
 * @Date 2020/1/14
 */
public interface IEmailService {

    public String sendSimpleEmail(String to,String subject,String content);
}
