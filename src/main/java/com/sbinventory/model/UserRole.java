package com.sbinventory.model;

public class UserRole {
	
	private int userid;
	private int roleid;
	
	public UserRole() {}

	public UserRole(int userid, int roleid) {
		this.userid = userid;
		this.roleid = roleid;
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
