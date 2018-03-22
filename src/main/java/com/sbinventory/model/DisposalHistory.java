package com.sbinventory.model;

public class DisposalHistory {

	private int disposalhistoryid;
	private String loguser;
	private String logdatetime;
	private int productid;
	private int quantity;
	private int mainlocid;
	private int sublocid;
	private String approval;
	
	public DisposalHistory() {}

	public DisposalHistory(int disposalhistoryid, String loguser, String logdatetime, int productid, int quantity,
			int mainlocid, int sublocid, String approval) {
		super();
		this.disposalhistoryid = disposalhistoryid;
		this.loguser = loguser;
		this.logdatetime = logdatetime;
		this.productid = productid;
		this.quantity = quantity;
		this.mainlocid = mainlocid;
		this.sublocid = sublocid;
		this.approval = approval;
	}

	public int getDisposalhistoryid() {
		return disposalhistoryid;
	}

	public void setDisposalhistoryid(int disposalhistoryid) {
		this.disposalhistoryid = disposalhistoryid;
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

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	
}
