package com.sims.model;

public class PartNo {

	private int partnoid;
	private String serialno;
	private String modelno;
	private String upccode;
	private Integer productid;
	private String customername;
	private String invoiceno;
	private Integer mainlocid;
	private Integer sublocid;
	private Integer orgid;
	private Integer deptid;
	private Integer subdeptid;
	private String status;
	
	public PartNo() {}
	
	public PartNo(int partnoid, String serialno, String modelno, String upccode, Integer productid, String customername,
			String invoiceno, Integer mainlocid, Integer sublocid, Integer orgid, Integer deptid, Integer subdeptid,
			String status) {

		this.partnoid = partnoid;
		this.serialno = serialno;
		this.modelno = modelno;
		this.upccode = upccode;
		this.productid = productid;
		this.customername = customername;
		this.invoiceno = invoiceno;
		this.mainlocid = mainlocid;
		this.sublocid = sublocid;
		this.orgid = orgid;
		this.deptid = deptid;
		this.subdeptid = subdeptid;
		this.status = status;
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
	
	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean isPartNo(int partnoid){
        if (this.partnoid==partnoid) {
            return true;
        }
        return false;
    }
	
}
