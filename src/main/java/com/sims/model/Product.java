package com.sims.model;

public class Product {
	
	private int productid;
	private Integer productcode;
	private String productname;
	private Integer hardwareid;
	private Integer brandid;
	private int partnoid;
	private Integer lbvalue;
	private int quantity;
	
	public Product() {}
	
	public Product(int productid, int productcode, String productname, int hardwareid, int brandid, int partnoid, int lbvalue, int quantity) {

		this.productid=productid;
		this.productcode = productcode;
		this.productname = productname;
		this.hardwareid = hardwareid;
		this.brandid = brandid;
		this.partnoid = partnoid;
		this.lbvalue = lbvalue;
		this.quantity = quantity;
	}
	
	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public Integer getProductcode() {
		return productcode;
	}

	public void setProductcode(Integer productcode) {
		this.productcode = productcode;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Integer getHardwareid() {
		return hardwareid;
	}

	public void setHardwareid(Integer hardwareid) {
		this.hardwareid = hardwareid;
	}

	public Integer getBrandid() {
		return brandid;
	}

	public void setBrandid(Integer brandid) {
		this.brandid = brandid;
	}

	public int getPartnoid() {
		return partnoid;
	}

	public void setPartnoid(int partnoid) {
		this.partnoid = partnoid;
	}

	public Integer getLbvalue() {
		return lbvalue;
	}

	public void setLbvalue(int lbvalue) {
		this.lbvalue = lbvalue;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isProduct(int productid) {
		if(this.productid==productid) {
			return true;
		} else {
			return false;
		}
	}
}
