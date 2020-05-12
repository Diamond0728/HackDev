package com.group2.project.common;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


public class SendEmail {

    private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    private SimpleMailMessage message;

    public SendEmail() {

        // 参考QQ邮箱帮助中心
        mailSender.setHost("smtp.qq.com"); // QQ邮箱smtp发送服务器地址
        //mailSender.setPort(465); // QQ这个端口不可用
        mailSender.setPort(587);// 端口号
        mailSender.setUsername("qq@qq.com"); // 使用你自己的账号
        mailSender.setPassword("password"); // 授权码-发短信获取
    }
    public void send(String addr, String title, String text) {
        // 邮件信息
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("qq0@qq.com"); // 发件人邮箱
        msg.setTo(addr); // 收件人邮箱
        msg.setSubject(title); // 标题
        msg.setText(text); // 文本信息
        try {
            mailSender.send(msg);
            System.out.println("邮件发送成功!"); // 没有报错就是好消息 :-)
        } catch (MailException ex) {
            System.err.println("发送失败:" + ex.getMessage());
        }
    }
}

