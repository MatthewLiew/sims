package com.sbinventory.model;

public class SubDept {

	private int subdeptid;
	private Integer subdeptcode;
	private String subdeptname;
	private Integer deptid;
	
	public SubDept() {}
	
	public SubDept(int subdeptid, Integer subdeptcode, String subdeptname, Integer deptid) {
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

	public Integer getSubdeptcode() {
		return subdeptcode;
	}

	public void setSubdeptcode(Integer subdeptcode) {
		this.subdeptcode = subdeptcode;
	}

	public String getSubdeptname() {
		return subdeptname;
	}

	public void setSubdeptname(String subdeptname) {
		this.subdeptname = subdeptname;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	
	public boolean isSelected(int subdeptid){
        if (this.subdeptid==subdeptid) {
            return true;
        }
        return false;
    }
	public boolean isSubdeptname(int subdeptid){
        if (this.subdeptid==subdeptid) {
            return true;
        }
        return false;
    }
}
