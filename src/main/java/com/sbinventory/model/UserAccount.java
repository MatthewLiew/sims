package com.sbinventory.model;

public class UserAccount {

	private int userid;
	private String username;
	private String password;
	private Integer orgid;
	private Integer deptid;
	private Integer subdeptid;
	
	public UserAccount() {
		
	}
	public UserAccount (int userid) {
		this.userid = userid;
	}
	
	public UserAccount (int userid, String username, String password) {
		this.userid = userid;
		this.username = username;
		this.password = password;
	}
	
	public UserAccount(int userid, String username, String password, Integer orgid, Integer deptid, Integer subdeptid) {
		this.userid = userid;
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
	
	public Integer getOrgid() {
		return orgid;
	}
	
	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
	
	public Integer getDeptid() {
		return deptid;
	}
	
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	
	public Integer getSubdeptid() {
		return subdeptid;
	}
	
	public void setSubdeptid(Integer subdeptid) {
		this.subdeptid = subdeptid;
	}
	
}
