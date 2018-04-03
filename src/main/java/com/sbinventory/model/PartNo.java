package com.sbinventory.model;

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
	private String status;
	
	public PartNo() {}
	
	public PartNo(int partnoid, String serialno, String modelno, String upccode, int productid, String customername, String invoiceno, int mainlocid, int sublocid, String status) {
		
		this.partnoid = partnoid;
		this.serialno = serialno;
		this.modelno = modelno;
		this.upccode = upccode;
		this.productid = productid;
		this.customername = customername;
		this.invoiceno = invoiceno;
		this.mainlocid = mainlocid;
		this.sublocid = sublocid;
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
