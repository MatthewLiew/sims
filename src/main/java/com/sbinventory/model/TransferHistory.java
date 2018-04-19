package com.sbinventory.model;

public class TransferHistory {

	private int transferhistoryid;
//	private String loguser;
//	private String logdatetime;
	private Integer productid;
	private int quantity;
	private String serialno;
	private Integer transfertype;
	private String source;
	private String destination;
	private String approval;
	
	public TransferHistory () {}

	public TransferHistory(int transferhistoryid, /*String loguser, String logdatetime, */int productid, int quantity,
			String serialno, int transfertype, String source, String destination, String approval) {
		
		this.transferhistoryid = transferhistoryid;
//		this.loguser = loguser;
//		this.logdatetime = logdatetime;
		this.productid = productid;
		this.quantity = quantity;
		this.serialno = serialno;
		this.transfertype = transfertype;
		this.source = source;
		this.destination = destination;
		this.approval = approval;
	}

	public int getTransferhistoryid() {
		return transferhistoryid;
	}

	public void setTransferhistoryid(int transferhistoryid) {
		this.transferhistoryid = transferhistoryid;
	}

//	public String getLoguser() {
//		return loguser;
//	}
//
//	public void setLoguser(String loguser) {
//		this.loguser = loguser;
//	}
//
//	public String getLogdatetime() {
//		return logdatetime;
//	}
//
//	public void setLogdatetime(String logdatetime) {
//		this.logdatetime = logdatetime;
//	}

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

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	
	public Integer getTransfertype() {
		return transfertype;
	}

	public void setTransfertype(Integer transfertype) {
		this.transfertype = transfertype;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
