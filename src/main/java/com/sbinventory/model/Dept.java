package com.sbinventory.model;

public class Dept {

	private int deptid;
	private Integer deptcode;
	private String deptname;
	private Integer orgid;
	
	public Dept() {}

	public Dept(int deptid, Integer deptcode, String deptname, Integer orgid) {
		this.deptid = deptid;
		this.deptcode = deptcode;
		this.deptname = deptname;
		this.orgid = orgid;
	}
	
	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public Integer getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(Integer deptcode) {
		this.deptcode = deptcode;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
	
	public boolean isSelected(int deptid){
        if (this.deptid==deptid) {
            return true;
        }
        return false;
    }
	
	public boolean isDeptname(int deptid) {
		if (this.deptid==deptid) {
            return true;
        } else {return false;}
	}
}
