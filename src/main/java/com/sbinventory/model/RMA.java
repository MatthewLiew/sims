package com.sbinventory.model;

public class RMA {
	
	private int rmaid;
	private String loguser;
	private String logdatetime;
	private Integer productid;
	private Integer quantity;
	private Integer mainlocid;
	private Integer sublocid;
	private String approval;
	private String reason;
	
	public RMA() {}

	public RMA(int rmaid, String loguser, String logdatetime, int productid, int quantity, int mainlocid, int sublocid,
			String approval, String reason) {
		super();
		this.rmaid = rmaid;
		this.loguser = loguser;
		this.logdatetime = logdatetime;
		this.productid = productid;
		this.quantity = quantity;
		this.mainlocid = mainlocid;
		this.sublocid = sublocid;
		this.approval = approval;
		this.reason = reason;
	}

	public int getRmaid() {
		return rmaid;
	}

	public void setRmaid(int rmaid) {
		this.rmaid = rmaid;
	}

	public String getLoguser() {
		return loguser;
	}

	public void setLoguser(String loguser) {
		this.loguser = loguser;
	}

	public String getLogdatetime() {
		return logdatetime;
	}

	public void setLogdatetime(String logdatetime) {
		this.logdatetime = logdatetime;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getMainlocid() {
		return mainlocid;
	}

	public void setMainlocid(Integer mainlocid) {
		this.mainlocid = mainlocid;
	}

	public Integer getSublocid() {
		return sublocid;
	}

	public void setSublocid(Integer sublocid) {
		this.sublocid = sublocid;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
