package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.rpc.api.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.mail.username}")
    String systemMail;
	
	@Override
	public String send(String toEmail,String subject,String text) {
        String retVal = HookahConstants.SMS_FAIL;
		logger.info("邮件发送从{}到{}",systemMail,toEmail);
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(toEmail);
//            helper.setReplyTo("253240003@qq.com");
            helper.setFrom(systemMail);
            helper.setSubject(subject);
            helper.setText(text);
            javaMailSender.send(mail);
            retVal = HookahConstants.SMS_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {}
        javaMailSender.send(mail);
        //return helper;
        return retVal;
    }

}
