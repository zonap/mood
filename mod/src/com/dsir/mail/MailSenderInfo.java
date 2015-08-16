package com.dsir.mail;

import java.util.Properties;

import javax.mail.Session;

public class MailSenderInfo {
	private String mailServerHost;
	private String mailServerPort = "25";
	private String fromAddress;
	private String toAddress;
	private String userName;
	private String password;
	private boolean validate = false;
	private String subject;
	private String content;
	private String[] attachFileNames;

	public MailSenderInfo() {
		setMailServerHost("smtp.mxhichina.com");
		setMailServerPort("25");
		setValidate(true);
		this.userName = "�������";
		this.password = "Zz265874129";
		this.fromAddress = "administrator@dsir.net";
	}

	// public MailSenderInfo() {
	// setMailServerHost("smtp.qq.com");
	// setMailServerPort("25");
	// setValidate(true);
	// this.userName = "�������";
	// this.password = "[mySql]";
	// this.fromAddress = "228944620@qq.com";
	// }

	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);

		p.put("mail.smtp.auth", "true");
		MyAuthenticator myauth = new MyAuthenticator(this.fromAddress, this.password);
		@SuppressWarnings("unused")
		Session session = Session.getDefaultInstance(p, myauth);
		return p;
	}

	public String getMailServerHost() {
		return this.mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return this.mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return this.validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String[] getAttachFileNames() {
		return this.attachFileNames;
	}

	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return this.fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}
}