package com.sbinventory.model;

public class Organization {

	private int orgid;
	private int orgcode;
	private String orgname;
	
	public Organization() {
		 
    }

	public Organization(int orgid, int orgcode, String orgname) {
		this.orgid = orgid;
		this.orgcode = orgcode;
		this.orgname = orgname;
	}

	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}

	public int getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(int orgcode) {
		this.orgcode = orgcode;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	public boolean isSelected(int orgid){
        if (this.orgid==orgid) {
            return true;
        }
        return false;
    }
	public boolean isOrgname(int orgid) {
		if (this.orgid==orgid) {
            return true;
        } else {return false;}
	}
}