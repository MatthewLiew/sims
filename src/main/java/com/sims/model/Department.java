package com.sims.model;

public class Department {

	private int id;
	private boolean activeFlag;
	private String name;
	private Integer organizationId;
	private String organizationName;
	
	public Department() {}
	
	public Department(int id, boolean activeFlag, String name, Integer organizationId, String organizationName) {
		super();
		this.id = id;
		this.activeFlag = activeFlag;
		this.name = name;
		this.organizationId = organizationId;
		this.organizationName = organizationName;
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

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public boolean isDepartment(int id) {
		if (this.id == id) {
            return true;
        } else {
        	return false;
    	}
	}
}
