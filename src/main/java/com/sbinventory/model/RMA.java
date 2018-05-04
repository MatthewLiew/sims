package com.sbinventory.model;

public class RMA {
	
	private int rmaid;
	private String code;
	private String invoiceno;
	private String serialno;
	private String name;
	private String email;
	private String phoneno;
	private String desc;
	private Integer rmareason;
	private Integer rmatype;
	private String approval;
	private String rquser;
	private String rqdatetime;
	private String loguser;
	private String logdatetime;
	
	public RMA() {}

	public RMA(int rmaid, String code, String invoiceno, String serialno, String name, String email, String phoneno,
			String desc, Integer rmareason, Integer rmatype, String approval, String rquser, String rqdatetime,
			String loguser, String logdatetime) {
		super();
		this.rmaid = rmaid;
		this.code = code;
		this.invoiceno = invoiceno;
		this.serialno = serialno;
		this.name = name;
		this.email = email;
		this.phoneno = phoneno;
		this.desc = desc;
		this.rmareason = rmareason;
		this.rmatype = rmatype;
		this.approval = approval;
		this.rquser = rquser;
		this.rqdatetime = rqdatetime;
		this.loguser = loguser;
		this.logdatetime = logdatetime;
	}

	public int getRmaid() {
		return rmaid;
	}

	public void setRmaid(int rmaid) {
		this.rmaid = rmaid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getRmareason() {
		return rmareason;
	}

	public void setRmareason(Integer rmareason) {
		this.rmareason = rmareason;
	}

	public Integer getRmatype() {
		return rmatype;
	}

	public void setRmatype(Integer rmatype) {
		this.rmatype = rmatype;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getRquser() {
		return rquser;
	}

	public void setRquser(String rquser) {
		this.rquser = rquser;
	}

	public String getRqdatetime() {
		return rqdatetime;
	}

	public void setRqdatetime(String rqdatetime) {
		this.rqdatetime = rqdatetime;
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
}
