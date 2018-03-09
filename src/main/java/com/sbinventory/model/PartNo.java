package com.sbinventory.model;

public class PartNo {

	private int partnoid;
	private String serialno;
	private String modelno;
	private String upccode;
	private int productid;
	private String customername;
	private String invoiceno;
	
	public PartNo() {}
	
	public PartNo(int partnoid, String serialno, String modelno, String upccode, int productid, String customername, String invoiceno) {
		
		this.partnoid = partnoid;
		this.serialno = serialno;
		this.modelno = modelno;
		this.upccode = upccode;
		this.productid = productid;
		this.customername = customername;
		this.invoiceno = invoiceno;
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
	
	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
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

	public boolean isPartNo(int partnoid){
        if (this.partnoid==partnoid) {
            return true;
        }
        return false;
    }
	
}
