package com.sbinventory.model;

public class SubDept {

	private int subdeptid;
	private int subdeptcode;
	private String subdeptname;
	private int deptid;
	
	public SubDept() {}
	
	public SubDept(int subdeptid, int subdeptcode, String subdeptname, int deptid) {
		this.subdeptid = subdeptid;
		this.subdeptcode = subdeptcode;
		this.subdeptname = subdeptname;
		this.deptid = deptid;
	}

	public int getSubdeptid() {
		return subdeptid;
	}

	public void setSubdeptid(int subdeptid) {
		this.subdeptid = subdeptid;
	}

	public int getSubdeptcode() {
		return subdeptcode;
	}

	public void setSubdeptcode(int subdeptcode) {
		this.subdeptcode = subdeptcode;
	}

	public String getSubdeptname() {
		return subdeptname;
	}

	public void setSubdeptname(String subdeptname) {
		this.subdeptname = subdeptname;
	}

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	
	public boolean isSelected(int subdeptid){
        if (this.subdeptid==subdeptid) {
            return true;
        }
        return false;
    }
	
}
