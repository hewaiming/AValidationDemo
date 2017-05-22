package com.cndemoz.avalidations.demo;

import java.io.Serializable;

public class User implements Serializable {
	private String UserName;
	private String PassWord;

	public User(String userName, String passWord) {
		super();
		UserName = userName;
		PassWord = passWord;
	}

	public User() {
		super();
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	@Override
	public String toString() {
		return "User [UserName=" + UserName + ", PassWord=" + PassWord + "]";
	}

}
