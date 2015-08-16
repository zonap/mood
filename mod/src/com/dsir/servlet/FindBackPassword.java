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
			pwd.setTips("参数错误");
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
				pwd.setTips("没找到该用户,请核验邮箱后重新操作");
				pwd.setTipsId(1);
				return pwd;
			}
			this.stmt.close();
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			pwd.setTips("未知错误");
			pwd.setTipsId(2);
			return pwd;
		}
		if (sendMail(email)) {
			pwd.setTips("邮件发送成功,请登陆邮箱查看");
			pwd.setTipsId(0);
		} else {
			pwd.setTips("邮件服务不可用,请稍后再试");
			pwd.setTipsId(4);
		}
		return pwd;
	}

	boolean sendMail(String mail) {
		try {
			String temPwd = StringTools.getRandomString(9);
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setToAddress(mail);
			mailInfo.setSubject("接收你的修改密码凭据");
			StringBuffer buffer = new StringBuffer();
			buffer.append("我们发送给你一个临时密码,你可以使用它登陆并修改自己的密码\n");
			buffer.append("你的密码为:" + temPwd);
			buffer.append("\n本邮件由系统自动发出,请勿回复!");
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