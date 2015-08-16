package com.dsir.mail;

import javax.mail.MessagingException;

public class SendMailDemo {
	public static void main(String[] args) {
		MailSenderInfo mailInfo = new MailSenderInfo();

		mailInfo.setToAddress("qixlin@dsir.net");

		mailInfo.setSubject("接收你的修改密码凭据");

		StringBuffer buffer = new StringBuffer();
		buffer.append("我们发送给你一个临时密码,你可以使用它登陆并修改自己的密码\n");
		buffer.append("你的密码为\n");
		buffer.append("本邮件由系统自动发出,请勿回复!");
		mailInfo.setContent(buffer.toString());

		SimpleMailSender sms = new SimpleMailSender();
		try {
			sms.sendTextMail(mailInfo);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		System.out.println("邮件发送完毕");
	}
}