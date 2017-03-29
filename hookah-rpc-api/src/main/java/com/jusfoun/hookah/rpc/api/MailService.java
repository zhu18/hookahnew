package com.jusfoun.hookah.rpc.api;
/**
 *邮件service
 * @author admin
 *
 */
public interface MailService {

	void send(String toEmail, String subject, String text);
}
