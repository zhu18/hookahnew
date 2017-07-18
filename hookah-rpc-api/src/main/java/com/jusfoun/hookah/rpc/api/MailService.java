package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.exception.EmailException;

/**
 *邮件service
 * @author admin
 *
 */
public interface MailService {

	String send(String toEmail, String subject, String text) throws EmailException;
}
