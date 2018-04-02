package com.sbinventory.model;

public class Hardware {

	private int hardwareid;
	private Integer hardwarecode;
	private String hardwaretype;
	
	public Hardware() {}
	
	public Hardware(int hardwareid, int hardwarecode, String hardwaretype) {
		this.hardwareid = hardwareid;
		this.hardwarecode = hardwarecode;
		this.hardwaretype = hardwaretype;
	}
	
	public int getHardwareid() {
		return hardwareid;
	}

	public void setHardwareid(int hardwareid) {
		this.hardwareid = hardwareid;
	}

	public Integer getHardwarecode() {
		return hardwarecode;
	}
	
	public void setHardwarecode(Integer hardwarecode) {
		this.hardwarecode = hardwarecode;
	}
	
	public String getHardwaretype() {
		return hardwaretype;
	}
	
	public void setHardwaretype(String hardwaretype) {
		this.hardwaretype = hardwaretype;
	}	
	
	public boolean isHardware(int hardwareid){
        if (this.hardwareid==hardwareid) {
            return true;
        }
        return false;
    }
}
