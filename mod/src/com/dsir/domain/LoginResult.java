package com.dsir.domain;

public class LoginResult {
	private int userid;
	private String username;
	private String result;
	private String usersid;

	public LoginResult() {
	}

	public LoginResult(int userid, String username, String result, String usersid) {
		this.userid = userid;
		this.username = username;
		this.result = result;
		this.usersid = usersid;
	}

	public String getUsersid() {
		return this.usersid;
	}

	public void setUsersid(String usersid) {
		this.usersid = usersid;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return "LoginResult [userid=" + this.userid + ", username=" + this.username + "]";
	}
}