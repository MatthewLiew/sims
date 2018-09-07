package com.sims.model;

public class SubLocation {

	private int id;
	private boolean activeFlag;
	private String name;
	private String description;
	private Integer mainLocationId;
	private String mainLocationName;
	
	public SubLocation() {}

	public SubLocation(int id, boolean activeFlag, String name, String description, Integer mainLocationId,
			String mainLocationName) {
		super();
		this.id = id;
		this.activeFlag = activeFlag;
		this.name = name;
		this.description = description;
		this.mainLocationId = mainLocationId;
		this.mainLocationName = mainLocationName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getMainLocationId() {
		return mainLocationId;
	}

	public void setMainLocationId(Integer mainLocationId) {
		this.mainLocationId = mainLocationId;
	}

	public String getMainLocationName() {
		return mainLocationName;
	}

	public void setMainLocationName(String mainLocationName) {
		this.mainLocationName = mainLocationName;
	}

	public boolean isSubLocation(int id) {
		if (this.id==id) {
            return true;
        } else {
        	return false;
    	}
	}
}
