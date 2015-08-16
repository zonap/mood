package com.dsir.servlet;

import com.dsir.dbconn.DBUtils;
import com.dsir.domain.Password;
import com.dsir.mail.MailSenderInfo;
import com.dsir.mail.SimpleMailSender;
import com.dsir.tools.JsonTools;
import com.dsir.tools.StringTools;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindBackPassword extends HttpServlet {
	private Connection conn;
	private Statement stmt;
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		String json = JsonTools.createJsonString("findbackpassword", dosomething(req));
		PrintWriter pw = resp.getWriter();
		pw.print(json);
		pw.close();
	}

	private Password dosomething(HttpServletRequest req) {
		Password pwd = new Password(null, 0);

		String email = req.getParameter("email");
		if (email == null) {
			pwd.setTips("��������");
			pwd.setTipsId(3);
			return pwd;
		}
		try {
			this.conn = DBUtils.getConnection();
			this.stmt = this.conn.createStatement();
			String pwdsql = "select * from userinfo where emailaddress='" + email + "';";
			ResultSet rs = this.stmt.executeQuery(pwdsql);
			rs.last();
			if (rs.getRow() == 0) {
				pwd.setTips("û�ҵ����û�,�������������²���");
				pwd.setTipsId(1);
				return pwd;
			}
			this.stmt.close();
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			pwd.setTips("δ֪����");
			pwd.setTipsId(2);
			return pwd;
		}
		if (sendMail(email)) {
			pwd.setTips("�ʼ����ͳɹ�,���½����鿴");
			pwd.setTipsId(0);
		} else {
			pwd.setTips("�ʼ����񲻿���,���Ժ�����");
			pwd.setTipsId(4);
		}
		return pwd;
	}

	boolean sendMail(String mail) {
		try {
			String temPwd = StringTools.getRandomString(9);
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setToAddress(mail);
			mailInfo.setSubject("��������޸�����ƾ��");
			StringBuffer buffer = new StringBuffer();
			buffer.append("���Ƿ��͸���һ����ʱ����,�����ʹ������½���޸��Լ�������\n");
			buffer.append("�������Ϊ:" + temPwd);
			buffer.append("\n���ʼ���ϵͳ�Զ�����,����ظ�!");
			mailInfo.setContent(buffer.toString());
			SimpleMailSender sms = new SimpleMailSender();
			sms.sendTextMail(mailInfo);
			this.conn = DBUtils.getConnection();
			this.stmt = this.conn.createStatement();
			String updstepwd = "update userinfo set password='" + temPwd + "' where emailaddress='" + mail + "';";
			this.stmt.executeUpdate(updstepwd);
			this.stmt.close();
			this.conn.close();
			return true;
		} catch (Exception e) {
		}
		return false;
	}
}