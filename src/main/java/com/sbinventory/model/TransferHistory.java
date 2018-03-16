package com.sbinventory.model;

public class TransferHistory {

	private int transferhistoryid;
	private String loguser;
	private String logdatetime;
	private int productid;
	private int quantity;
	private int orimainlocid;
	private int orisublocid;
	private int desmainlocid;
	private int dessublocid;
	private String approval;
	
	public TransferHistory () {}

	public TransferHistory(int transferhistoryid, String loguser, String logdatetime, int productid, int quantity,
			int orimainlocid, int orisublocid, int desmainlocid, int dessublocid, String approval) {
		
		this.transferhistoryid = transferhistoryid;
		this.loguser = loguser;
		this.logdatetime = logdatetime;
		this.productid = productid;
		this.quantity = quantity;
		this.orimainlocid = orimainlocid;
		this.orisublocid = orisublocid;
		this.desmainlocid = desmainlocid;
		this.dessublocid = dessublocid;
		this.approval = approval;
	}

	public int getTransferhistoryid() {
		return transferhistoryid;
	}

	public void setTransferhistoryid(int transferhistoryid) {
		this.transferhistoryid = transferhistoryid;
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

	public int getOrimainlocid() {
		return orimainlocid;
	}

	public void setOrimainlocid(int orimainlocid) {
		this.orimainlocid = orimainlocid;
	}

	public int getOrisublocid() {
		return orisublocid;
	}

	public void setOrisublocid(int orisublocid) {
		this.orisublocid = orisublocid;
	}

	public int getDesmainlocid() {
		return desmainlocid;
	}

	public void setDesmainlocid(int desmainlocid) {
		this.desmainlocid = desmainlocid;
	}

	public int getDessublocid() {
		return dessublocid;
	}

	public void setDessublocid(int dessublocid) {
		this.dessublocid = dessublocid;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

}
