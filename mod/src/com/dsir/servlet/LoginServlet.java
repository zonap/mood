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

public class LoginServlet extends HttpServlet {
	private Connection conn;
	private Statement stmt;
	private LoginResult lr;
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		try {
			this.conn = DBUtils.getConnection();
			this.stmt = this.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.lr = loginMethod(req);

		String loginJson = JsonTools.createJsonString("login", this.lr);
		PrintWriter pw = resp.getWriter();
		pw.println(loginJson);
		pw.close();
	}

	private LoginResult loginMethod(HttpServletRequest req) {
		LoginResult ls = new LoginResult();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		ls.setUsername(username);
		String dbPassword = null;
		String sql = "select userid,usersid,password from userinfo where username='" + username + "' ;";
		try {
			ResultSet rs = this.stmt.executeQuery(sql);
			if (!rs.next()) {
				ls.setResult("ÓÃ»§²»´æÔÚ");
			} else {
				System.out.println(password);
				ResultSet rs1 = this.stmt.executeQuery(sql);
				while (rs1.next()) {
					dbPassword = rs1.getString("password");
					System.out.println(dbPassword);
				}
				ResultSet rs2 = this.stmt.executeQuery(sql);
				System.out.println(dbPassword);
				if (dbPassword.equals(password)) {
					ls.setResult("µÇÂ¼³É¹¦");
					while (rs2.next()) {
						ls.setUserid(rs2.getInt("userid"));
						ls.setUsersid(rs2.getString("usersid"));
					}
				} else {
					ls.setResult("ÃÜÂë´íÎó");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ls.setResult("µÇÂ¼Ê§°Ü");
		}

		return ls;
	}

	public void destroy() {
		try {
			this.conn.close();
			this.stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}