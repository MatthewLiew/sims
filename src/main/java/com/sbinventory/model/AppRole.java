package com.sbinventory.model;

public class AppRole {

	private int roleid;
	private String rolename;
	
	public AppRole() {}

	public AppRole(int roleid, String rolename) {
		this.roleid = roleid;
		this.rolename = rolename;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	public boolean isSelected(int roleid){
        if (this.roleid==roleid) {
            return true;
        }
        return false;
    }
}
