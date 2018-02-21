package com.sbinventory.model;

public class UserAccount {

	private int userid;
	private int usercode;
	private String username;
	private String password;
	private int orgid;
	private int deptid;
	private int subdeptid;
	
	public UserAccount() {
		
	}
	public UserAccount (int userid) {
		this.userid = userid;
	}
	
	public UserAccount (int userid, int usercode, String username, String password) {
		this.userid = userid;
		this.usercode = usercode;
		this.username = username;
		this.password = password;
	}
	
	public UserAccount(int userid, int usercode, String username, String password, int orgid, int deptid, int subdeptid) {
		this.userid = userid;
		this.usercode=usercode;
		this.username = username;
		this.password = password;
		this.orgid = orgid;
		this.deptid = deptid;
		this.subdeptid = subdeptid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public int getUsercode() {
		return usercode;
	}
	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public int getOrgid() {
		return orgid;
	}
	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public int getSubdeptid() {
		return subdeptid;
	}
	public void setSubdeptid(int subdeptid) {
		this.subdeptid = subdeptid;
	}
	
}
