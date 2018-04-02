package com.sbinventory.model;

public class SubLoc {

	private int sublocid;
	private String sublocname;
	private Integer mainlocid;
	
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
	
	public Integer getMainlocid() {
		return mainlocid;
	}
	
	public void setMainlocid(Integer mainlocid) {
		this.mainlocid = mainlocid;
	}
	
	public boolean isSubLocName(int sublocid) {
		if (this.sublocid==sublocid) {
            return true;
        } else {return false;}
	}
}
