package com.dsir.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBUtils {
	static Connection conn;
	private static String url = "jdbc:mysql://localhost:3306/mod";
	private static String user = "root";
	private static String psw = "0330";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection() {
		conn = null;
		try {
			conn = DriverManager.getConnection(url, user, psw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}