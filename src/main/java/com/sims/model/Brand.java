package com.sims.model;

public class Brand {

	private int brandid;
	private Integer brandcode;
	private String brandname;
	
	public Brand() {}
	
	public Brand(int brandid, int brandcode, String brandname) {
		this.brandid = brandid;
		this.brandcode = brandcode;
		this.brandname = brandname;
	}
	
	public int getBrandid() {
		return brandid;
	}

	public void setBrandid(int brandid) {
		this.brandid = brandid;
	}

	public Integer getBrandcode() {
		return brandcode;
	}
	
	public void setBrandcode(Integer brandcode) {
		this.brandcode = brandcode;
	}
	
	public String getBrandname() {
		return brandname;
	}
	
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	
	public boolean isBrand(int brandid){
        if (this.brandid==brandid) {
            return true;
        }
        return false;
    }
	
}
