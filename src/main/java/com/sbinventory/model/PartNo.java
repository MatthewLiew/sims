package com.sbinventory.model;

public class PartNo {

	private int partnoid;
	private String serialno;
	private String modelno;
	private String upccode;
	
	public PartNo() {}
	
	public PartNo(int partnoid, String serialno, String modelno, String upccode) {
		
		this.partnoid = partnoid;
		this.serialno = serialno;
		this.modelno = modelno;
		this.upccode = upccode;
	}
	
	public int getPartnoid() {
		return partnoid;
	}
	
	public void setPartnoid(int partnoid) {
		this.partnoid = partnoid;
	}
	
	public String getSerialno() {
		return serialno;
	}
	
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	
	public String getModelno() {
		return modelno;
	}
	
	public void setModelno(String modelno) {
		this.modelno = modelno;
	}
	
	public String getUpccode() {
		return upccode;
	}
	
	public void setUpccode(String upccode) {
		this.upccode = upccode;
	}
	
	public boolean isPartNo(int partnoid){
        if (this.partnoid==partnoid) {
            return true;
        }
        return false;
    }
	
}
