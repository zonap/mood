package com.dsir.mail;

import javax.mail.MessagingException;

public class SendMailDemo {
	public static void main(String[] args) {
		MailSenderInfo mailInfo = new MailSenderInfo();

		mailInfo.setToAddress("qixlin@dsir.net");

		mailInfo.setSubject("��������޸�����ƾ��");

		StringBuffer buffer = new StringBuffer();
		buffer.append("���Ƿ��͸���һ����ʱ����,�����ʹ������½���޸��Լ�������\n");
		buffer.append("�������Ϊ\n");
		buffer.append("���ʼ���ϵͳ�Զ�����,����ظ�!");
		mailInfo.setContent(buffer.toString());

		SimpleMailSender sms = new SimpleMailSender();
		try {
			sms.sendTextMail(mailInfo);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		System.out.println("�ʼ��������");
	}
}