package com.sbinventory.model;

public class MainLoc {

	private int mainlocid;
	private String mainlocname;
	
	public MainLoc() {}
	public MainLoc(int mainlocid, String mainlocname) {
		super();
		this.mainlocid = mainlocid;
		this.mainlocname = mainlocname;
	}

	public int getMainlocid() {
		return mainlocid;
	}

	public void setMainlocid(int mainlocid) {
		this.mainlocid = mainlocid;
	}

	public String getMainlocname() {
		return mainlocname;
	}

	public void setMainlocname(String mainlocname) {
		this.mainlocname = mainlocname;
	}
	
	public boolean isMainLocName(int mainlocid) {
		if (this.mainlocid==mainlocid) {
			return true;
        } else {
        	return false;
    	}
	}
	
}
