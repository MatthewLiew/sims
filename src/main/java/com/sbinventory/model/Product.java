package com.sbinventory.model;

public class Product {
	
	private int productid;
	private int productcode;
	private String productname;
	private int hardwareid;
	private int brandid;
	private int partnoid;
	private int lbvalue;
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


	public int getProductcode() {
		return productcode;
	}

	public void setProductcode(int productcode) {
		this.productcode = productcode;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public int getHardwareid() {
		return hardwareid;
	}

	public void setHardwareid(int hardwareid) {
		this.hardwareid = hardwareid;
	}

	public int getBrandid() {
		return brandid;
	}

	public void setBrandid(int brandid) {
		this.brandid = brandid;
	}

	public int getPartnoid() {
		return partnoid;
	}

	public void setPartnoid(int partnoid) {
		this.partnoid = partnoid;
	}

	public int getLbvalue() {
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
