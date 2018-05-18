package com.sbinventory.model;

public class MainLocation {

	private int id;
	private boolean activeFlag;
	private String name;
	
	public MainLocation() {}

	
	public MainLocation(int id, boolean activeFlag, String name) {
		super();
		this.id = id;
		this.activeFlag = activeFlag;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isMainLocation(int id) {
		if (this.id==id) {
			return true;
        } else {
        	return false;
    	}
	}
	
}
