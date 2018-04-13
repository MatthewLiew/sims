package com.sbinventory.model;

public class UserCap {

	private int usercapid;
	private int approleid;
	private int accessright;
	private int stockapprove;
	private int stockadd;
	private int stockedit;
	private int stockdelete;
	private int serialadd;
	private int serialedit;
	private int serialdelete;
	
	public UserCap() {}
	
	public UserCap(int usercapid, int approleid, int accessright, int stockapprove, int stockadd, int stockedit,
			int stockdelete, int serialadd, int serialedit, int serialdelete) {
		this.usercapid = usercapid;
		this.approleid = approleid;
		this.accessright = accessright;
		this.stockapprove = stockapprove;
		this.stockadd = stockadd;
		this.stockedit = stockedit;
		this.stockdelete = stockdelete;
		this.serialadd = serialadd;
		this.serialedit = serialedit;
		this.serialdelete = serialdelete;
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
	
	public int getStockapprove() {
		return stockapprove;
	}

	public void setStockapprove(int stockapprove) {
		this.stockapprove = stockapprove;
	}

	public int getStockadd() {
		return stockadd;
	}

	public void setStockadd(int stockadd) {
		this.stockadd = stockadd;
	}

	public int getStockedit() {
		return stockedit;
	}

	public void setStockedit(int stockedit) {
		this.stockedit = stockedit;
	}

	public int getStockdelete() {
		return stockdelete;
	}

	public void setStockdelete(int stockdelete) {
		this.stockdelete = stockdelete;
	}

	public int getSerialadd() {
		return serialadd;
	}

	public void setSerialadd(int serialadd) {
		this.serialadd = serialadd;
	}

	public int getSerialedit() {
		return serialedit;
	}

	public void setSerialedit(int serialedit) {
		this.serialedit = serialedit;
	}

	public int getSerialdelete() {
		return serialdelete;
	}

	public void setSerialdelete(int serialdelete) {
		this.serialdelete = serialdelete;
	}
	
}
