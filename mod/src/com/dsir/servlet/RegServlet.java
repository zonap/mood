package com.dsir.servlet;

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

import com.dsir.dbconn.DBUtils;
import com.dsir.domain.LoginResult;
import com.dsir.tools.JsonTools;
import com.dsir.tools.StringTools;

public class RegServlet extends HttpServlet {
	Connection conn;
	Statement stmt;
	String tipsString = "注册成功";
	String password;
	String userName;
	String emailAddress;
	String usersid;
	String userid;
	String openid;
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		this.conn = DBUtils.getConnection();
		try {
			this.stmt = this.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.openid = req.getParameter("openid");
		if (this.openid == null)
			this.tipsString = modReg(req);
		else {
			this.tipsString = QQLoginReg(req);
		}
		PrintWriter pw = resp.getWriter();
		pw.println(this.tipsString);
		pw.close();
		try {
			this.conn.close();
			this.stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String QQLoginReg(HttpServletRequest req) {
		this.userName = ("qq" + req.getParameter("username"));
		if (this.userName == null) {
			return "参数不完整";
		}
		System.out.println(this.userName);
		String sqluserid = "select * from userinfo where usersid='" + this.openid + "';";
		String sqlusername = "select * from userinfo where username like '" + this.userName + "\\_%' ;";
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			rs = this.stmt.executeQuery(sqluserid);
			rs.last();
			if (rs.getRow() >= 1) {
				LoginResult ls = new LoginResult();
				rs = this.stmt.executeQuery(sqluserid);
				while (rs.next()) {
					if (rs.getString("qquser").equals("true")) {
						ls.setUserid(rs.getInt("userid"));
						ls.setUsername(rs.getString("username"));
						ls.setResult("登陆成功");
						ls.setUsersid(rs.getString("usersid"));
					} else {
						ls.setResult("登陆失败");
					}
				}
				return JsonTools.createJsonString("qqlogin", ls);
			}

			rs1 = this.stmt.executeQuery(sqlusername);
			rs1.last();
			String temString = "_" + (rs1.getRow() + 1);
			System.out.println(temString);
			this.userName += temString;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return "注册失败";
		}

		String userReg = "insert into userinfo(username,usersid,qquser) values('" + this.userName + "','" + this.openid
				+ "','true')";
		try {
			this.stmt.execute(userReg);
		} catch (SQLException e) {
			e.printStackTrace();
			return "注册失败";
		}
		return "注册成功";
	}

	public String modReg(HttpServletRequest req) {
		this.userName = req.getParameter("username");
		this.password = req.getParameter("password");
		this.emailAddress = req.getParameter("email");
		if ((this.userName == null) || (this.password == null) || (this.emailAddress == null)) {
			return "参数不完整";
		}
		String sqlemail = "select * from userinfo where emailaddress='" + this.emailAddress + "';";
		String sqlusername = "select * from userinfo where username='" + this.userName + "';";
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			rs = this.stmt.executeQuery(sqlemail);
			if (rs.next())
				return "邮箱已存在";

			rs1 = this.stmt.executeQuery(sqlusername);
			if (rs1.next())
				return "用户名已存在";

			this.userid = StringTools.getRandomString(32);
			String sqluserid = "select usersid from userinfo where usersid='" + this.userid + "';";

			ResultSet reSet = this.stmt.executeQuery(sqluserid);
			while (reSet.next()) {
				reSet.close();
				this.usersid = StringTools.getRandomString(32);
				reSet = this.stmt.executeQuery(sqluserid);
			}

			String userReg = "insert into userinfo(username,password,emailaddress,usersid) values('" + this.userName
					+ "','" + this.password + "','" + this.emailAddress + "','" + this.userid + "')";

			this.stmt.execute(userReg);
			
			return "注册成功";
		} catch (SQLException e1) {
			e1.printStackTrace();
			return "注册失败";
		}
	}
}