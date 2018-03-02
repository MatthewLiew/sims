package com.sbinventory.model;

public class Reason {

	private int reasonid;
	private String reason;
	private int stocktypeid;
	
	public Reason( ) {}

	public Reason(int reasonid, String reason, int stocktypeid) {
		this.reasonid = reasonid;
		this.reason = reason;
		this.stocktypeid = stocktypeid;
	}

	public int getReasonid() {
		return reasonid;
	}

	public void setReasonid(int reasonid) {
		this.reasonid = reasonid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public int getStocktypeid() {
		return stocktypeid;
	}

	public void setStocktypeid(int stocktypeid) {
		this.stocktypeid = stocktypeid;
	}

	public boolean isReason(int reasonid) {
		if(this.reasonid==reasonid) {
			return true;
		} else {
			return false;
		}
	}
	
	
}