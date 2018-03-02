package com.sbinventory.model;

public class StockHistory {
	
	private int stockhistoryid;
	private int productid;
	private int quantity;
	private String historydate;
	private String historytime;
	private int stocktypeid;
	private int reasonid;
	private String reasondesc;
	private String logdatetime;
	private String loguser;
	private String approval;
	
	public StockHistory () {}

	public StockHistory(int stockhistoryid, int productid, int quantity, String historydate, String historytime, int stocktypeid,
			int reasonid, String reasondesc, String logdatetime, String loguser, String approval) {
		this.stockhistoryid = stockhistoryid;
		this.productid = productid;
		this.quantity = quantity;
		this.historydate = historydate;
		this.historytime = historytime;
		this.stocktypeid = stocktypeid;
		this.reasonid = reasonid;
		this.reasondesc = reasondesc;
		this.logdatetime = logdatetime;
		this.loguser = loguser;
		this.approval = approval;
	}

	public int getStockhistoryid() {
		return stockhistoryid;
	}

	public void setStockhistoryid(int stockhistoryid) {
		this.stockhistoryid = stockhistoryid;
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

	public String getHistorydate() {
		return historydate;
	}

	public void setHistorydate(String historydate) {
		this.historydate = historydate;
	}

	public String getHistorytime() {
		return historytime;
	}

	public void setHistorytime(String historytime) {
		this.historytime = historytime;
	}

	public int getStocktypeid() {
		return stocktypeid;
	}

	public void setStocktypeid(int stocktypeid) {
		this.stocktypeid = stocktypeid;
	}

	public int getReasonid() {
		return reasonid;
	}

	public void setReasonid(int reasonid) {
		this.reasonid = reasonid;
	}

	public String getReasondesc() {
		return reasondesc;
	}

	public void setReasondesc(String reasondesc) {
		this.reasondesc = reasondesc;
	}
	
	public String getLogdatetime() {
		return logdatetime;
	}

	public void setLogdatetime(String logdatetime) {
		this.logdatetime = logdatetime;
	}

	public String getLoguser() {
		return loguser;
	}

	public void setLoguser(String loguser) {
		this.loguser = loguser;
	}
	
	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public boolean isProduct(int productid) {
		if(this.productid==productid) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}