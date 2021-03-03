package com.dhj.demo.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件控制层
 */
@RestController
public class SendMail {
    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 简单邮件发送测试
     * @return
     */
    @RequestMapping("/send/simpleMail")
    public String sendMail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("邮件主题");
        simpleMailMessage.setText("邮件文本");
        simpleMailMessage.setTo("1286753432@qq.com");//发送对象
        simpleMailMessage.setFrom("1286753432@qq.com");//由谁发送
        mailSender.send(simpleMailMessage);
        return "send success";
    }
    /**
     * 复杂邮件发送
     * @return
     */
    @RequestMapping("/send/mimeMailMessage")
    public String sendMimeMailMessage() throws MessagingException {
        //声明复炸邮件对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //生成对应的邮件组装器对象,第二个参数为是否发送多文件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //添加附件，附件名称及路径
        helper.addAttachment("11.jpg",new File("E:\\java_project\\个人文件\\11.jpg"));
        helper.setSubject("邮件主题");
        //第一个参数为正文，第二个参数为是否为html文件
        helper.setText("邮件正文",false);
        helper.setTo("1286753432@qq.com");//发送对象
        helper.setFrom("1286753432@qq.com");//由谁发送
        mailSender.send(helper.getMimeMessage());
        return "send success";
    }
}
