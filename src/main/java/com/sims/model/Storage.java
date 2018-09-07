package com.sims.model;

public class Storage {

	private int storageid;
	private int orgid;
	private int deptid;
	private int subdeptid;
	private int mainlocid;
	private int sublocid;
	private int productid;
	private int quantity;

	public Storage () {}
	
	public Storage(int storageid, int orgid, int deptid, int subdeptid, int mainlocid, int sublocid, int productid, int quantity) {
		
		this.storageid = storageid;
		this.orgid = orgid;
		this.deptid = deptid;
		this.subdeptid = subdeptid;
		this.mainlocid = mainlocid;
		this.sublocid = sublocid;
		this.productid = productid;
		this.quantity = quantity;
	}

	public int getStorageid() {
		return storageid;
	}

	public void setStorageid(int storageid) {
		this.storageid = storageid;
	}

	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}

	public int getDeptid() {
		return deptid;
	}

	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

	public int getSubdeptid() {
		return subdeptid;
	}

	public void setSubdeptid(int subdeptid) {
		this.subdeptid = subdeptid;
	}
	
	public int getMainlocid() {
		return mainlocid;
	}

	public void setMainlocid(int mainlocid) {
		this.mainlocid = mainlocid;
	}

	public int getSublocid() {
		return sublocid;
	}

	public void setSublocid(int sublocid) {
		this.sublocid = sublocid;
	}

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
