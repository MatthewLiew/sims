package com.sbinventory.model;

public class TransferHistory {

	private int transferhistoryid;
	private String loguser;
	private String logdatetime;
	private Integer productid;
	private int quantity;
//	private Integer orimainlocid;
//	private Integer orisublocid;
//	private Integer desmainlocid;
//	private Integer dessublocid;
	private String approval;
	
	public TransferHistory () {}

	public TransferHistory(int transferhistoryid, String loguser, String logdatetime, int productid, int quantity,
			/*int orimainlocid, int orisublocid, int desmainlocid, int dessublocid,*/ String approval) {
		
		this.transferhistoryid = transferhistoryid;
		this.loguser = loguser;
		this.logdatetime = logdatetime;
		this.productid = productid;
		this.quantity = quantity;
//		this.orimainlocid = orimainlocid;
//		this.orisublocid = orisublocid;
//		this.desmainlocid = desmainlocid;
//		this.dessublocid = dessublocid;
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

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

}
