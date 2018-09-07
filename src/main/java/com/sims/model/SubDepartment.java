package com.sims.model;

public class SubDepartment {

	private int id;
	private boolean activeFlag;
	private String name;
	private Integer departmentId;
	private String departmentName;
	private Integer organizationId;
	private String organizationName;
	
	public SubDepartment() {}
	
	public SubDepartment(int id, boolean activeFlag, String name, Integer departmentId, String departmentName,
			Integer organizationId, String organizationName) {
		super();
		this.id = id;
		this.activeFlag = activeFlag;
		this.name = name;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
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

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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

	public boolean isSubDepartment(int id){
        if (this.id == id) {
            return true;
        } else {
        	return false;
        }
    }
}
