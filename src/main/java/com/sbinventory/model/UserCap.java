package com.sbinventory.model;

public class UserCap {

	private int usercapid;
	private int approleid;
	private boolean accessright;
	private boolean approve;
	private boolean add;
	private boolean edit;
	private boolean delete;
	
	public UserCap() {}

	public UserCap(int usercapid, int approleid, boolean accessright, boolean approve, boolean add, boolean edit,
			boolean delete) {
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

	public boolean isAccessright() {
		return accessright;
	}

	public void setAccessright(boolean accessright) {
		this.accessright = accessright;
	}

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
