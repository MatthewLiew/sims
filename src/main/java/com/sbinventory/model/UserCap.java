package com.sbinventory.model;

public class UserCap {

	private int usercapid;
	private int approleid;
	private int accessright;
	private int approve;
	private int add;
	private int edit;
	private int delete;
	
	public UserCap() {}

	public UserCap(int usercapid, int approleid, int accessright, int approve, int add, int edit,
			int delete) {
		this.usercapid = usercapid;
		this.approleid = approleid;
		this.accessright = accessright;
		this.approve = approve;
		this.add = add;
		this.edit = edit;
		this.delete = delete;
	}

	public int getUsercapid() {
		return usercapid;
	}

	public void setUsercapid(int usercapid) {
		this.usercapid = usercapid;
	}

	public int getApproleid() {
		return approleid;
	}

	public void setApproleid(int approleid) {
		this.approleid = approleid;
	}

	public int getAccessright() {
		return accessright;
	}

	public void setAccessright(int accessright) {
		this.accessright = accessright;
	}

	public int getApprove() {
		return approve;
	}

	public void setApprove(int approve) {
		this.approve = approve;
	}

	public int getAdd() {
		return add;
	}

	public void setAdd(int add) {
		this.add = add;
	}

	public int getEdit() {
		return edit;
	}

	public void setEdit(int edit) {
		this.edit = edit;
	}

	public int getDelete() {
		return delete;
	}

	public void setDelete(int delete) {
		this.delete = delete;
	}
	
}
