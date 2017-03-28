package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.rpc.api.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Service
public class MailServiceImpl implements MailService {
	
	Logger logger = LoggerFactory.getLogger(MailService.class);
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Override
	public void send(String toEmail,String fromEmail,String subject,String text) {
		logger.info("邮件发送从{}到{}",fromEmail,toEmail);
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(toEmail);
//            helper.setReplyTo("253240003@qq.com");
            helper.setFrom(fromEmail);
            helper.setSubject(subject);
            helper.setText(text);
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {}
        javaMailSender.send(mail);
        //return helper;
    }

}
