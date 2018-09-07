package com.sims.model;

public class DisposalHistory {

	private int disposalhistoryid;
	private String code;
	private Integer productid;
	private Integer quantity;
	private String serialno;
	private Integer orgid;
	private Integer deptid;
	private Integer subdeptid;
	private Integer mainlocid;
	private Integer sublocid;
	private String approval;
	private Integer loguser;
	private String logdatetime;
	
	public DisposalHistory() {}

	public DisposalHistory(int disposalhistoryid, String code, Integer productid, Integer quantity, String serialno,
			Integer orgid, Integer deptid, Integer subdeptid, Integer mainlocid, Integer sublocid, String approval,
			Integer loguser, String logdatetime) {
		this.disposalhistoryid = disposalhistoryid;
		this.code = code;
		this.productid = productid;
		this.quantity = quantity;
		this.serialno = serialno;
		this.orgid = orgid;
		this.deptid = deptid;
		this.subdeptid = subdeptid;
		this.mainlocid = mainlocid;
		this.sublocid = sublocid;
		this.approval = approval;
		this.loguser = loguser;
		this.logdatetime = logdatetime;
	}

	public int getDisposalhistoryid() {
		return disposalhistoryid;
	}

	public void setDisposalhistoryid(int disposalhistoryid) {
		this.disposalhistoryid = disposalhistoryid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}

	public Integer getSubdeptid() {
		return subdeptid;
	}

	public void setSubdeptid(Integer subdeptid) {
		this.subdeptid = subdeptid;
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

	public Integer getLoguser() {
		return loguser;
	}

	public void setLoguser(Integer loguser) {
		this.loguser = loguser;
	}

	public String getLogdatetime() {
		return logdatetime;
	}

	public void setLogdatetime(String logdatetime) {
		this.logdatetime = logdatetime;
	}
	
}
