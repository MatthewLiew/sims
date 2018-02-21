package com.sbinventory.model;

public class SubLoc {

	private int sublocid;
	private String sublocname;
	private int mainlocid;
	
	public SubLoc() {}
	
	public SubLoc(int sublocid, String sublocname, int mainlocid) {
		super();
		this.sublocid = sublocid;
		this.sublocname = sublocname;
		this.mainlocid = mainlocid;
	}
	public int getSublocid() {
		return sublocid;
	}
	public void setSublocid(int sublocid) {
		this.sublocid = sublocid;
	}
	public String getSublocname() {
		return sublocname;
	}
	public void setSublocname(String sublocname) {
		this.sublocname = sublocname;
	}
	public int getMainlocid() {
		return mainlocid;
	}
	public void setMainlocid(int mainlocid) {
		this.mainlocid = mainlocid;
	}
	
	
}
