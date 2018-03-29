package com.sbinventory.model;

public class UserRole {
	
	private int userroleid;
	private int userid;
	private int roleid;
	
	public UserRole() {}

	public UserRole(int userroleid, int userid, int roleid) {
		this.userroleid = userroleid;
		this.userid = userid;
		this.roleid = roleid;
	}

	public int getUserroleid() {
		return userroleid;
	}

	public void setUserroleid(int userroleid) {
		this.userroleid = userroleid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	
}
