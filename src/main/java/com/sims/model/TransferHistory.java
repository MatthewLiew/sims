package com.sims.model;

public class TransferHistory {

	private int transferhistoryid;
	private String code;
	private Integer productid;
	private int quantity;
	private String serialno;
	private String source;
	private String destination;
	private Integer transfertype;
	private Integer srcorgid;
	private Integer srcdeptid;
	private Integer srcsubdeptid;
	private Integer srcmainlocid;
	private Integer srcsublocid;
	private String isTransfered;
	private String tfruser;
	private String tfrdatetime;
	private Integer desorgid;
	private Integer desdeptid;
	private Integer dessubdeptid;
	private Integer desmainlocid;
	private Integer dessublocid;
	private String isReceived;
	private String recuser;
	private String recdatetime;
	
	public TransferHistory () {}

	public TransferHistory(int transferhistoryid, String code, Integer productid, int quantity, String serialno,
			String source, String destination, Integer transfertype, Integer srcorgid, Integer srcdeptid,
			Integer srcsubdeptid, Integer srcmainlocid, Integer srcsublocid, String isTransfered, String tfruser,
			String tfrdatetime, Integer desorgid, Integer desdeptid, Integer dessubdeptid, Integer desmainlocid,
			Integer dessublocid, String isReceived, String recuser, String recdatetime) {

		this.transferhistoryid = transferhistoryid;
		this.code = code;
		this.productid = productid;
		this.quantity = quantity;
		this.serialno = serialno;
		this.source = source;
		this.destination = destination;
		this.transfertype = transfertype;
		this.srcorgid = srcorgid;
		this.srcdeptid = srcdeptid;
		this.srcsubdeptid = srcsubdeptid;
		this.srcmainlocid = srcmainlocid;
		this.srcsublocid = srcsublocid;
		this.isTransfered = isTransfered;
		this.tfruser = tfruser;
		this.tfrdatetime = tfrdatetime;
		this.desorgid = desorgid;
		this.desdeptid = desdeptid;
		this.dessubdeptid = dessubdeptid;
		this.desmainlocid = desmainlocid;
		this.dessublocid = dessublocid;
		this.isReceived = isReceived;
		this.recuser = recuser;
		this.recdatetime = recdatetime;
	}

	public int getTransferhistoryid() {
		return transferhistoryid;
	}

	public void setTransferhistoryid(int transferhistoryid) {
		this.transferhistoryid = transferhistoryid;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
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

	public Integer getTransfertype() {
		return transfertype;
	}

	public void setTransfertype(Integer transfertype) {
		this.transfertype = transfertype;
	}

	public Integer getSrcorgid() {
		return srcorgid;
	}

	public void setSrcorgid(Integer srcorgid) {
		this.srcorgid = srcorgid;
	}

	public Integer getSrcdeptid() {
		return srcdeptid;
	}

	public void setSrcdeptid(Integer srcdeptid) {
		this.srcdeptid = srcdeptid;
	}

	public Integer getSrcsubdeptid() {
		return srcsubdeptid;
	}

	public void setSrcsubdeptid(Integer srcsubdeptid) {
		this.srcsubdeptid = srcsubdeptid;
	}

	public Integer getSrcmainlocid() {
		return srcmainlocid;
	}

	public void setSrcmainlocid(Integer srcmainlocid) {
		this.srcmainlocid = srcmainlocid;
	}

	public Integer getSrcsublocid() {
		return srcsublocid;
	}

	public void setSrcsublocid(Integer srcsublocid) {
		this.srcsublocid = srcsublocid;
	}

	public String getIsTransfered() {
		return isTransfered;
	}

	public void setIsTransfered(String isTransfered) {
		this.isTransfered = isTransfered;
	}

	public String getTfruser() {
		return tfruser;
	}

	public void setTfruser(String tfruser) {
		this.tfruser = tfruser;
	}

	public String getTfrdatetime() {
		return tfrdatetime;
	}

	public void setTfrdatetime(String tfrdatetime) {
		this.tfrdatetime = tfrdatetime;
	}

	public Integer getDesorgid() {
		return desorgid;
	}

	public void setDesorgid(Integer desorgid) {
		this.desorgid = desorgid;
	}

	public Integer getDesdeptid() {
		return desdeptid;
	}

	public void setDesdeptid(Integer desdeptid) {
		this.desdeptid = desdeptid;
	}

	public Integer getDessubdeptid() {
		return dessubdeptid;
	}

	public void setDessubdeptid(Integer dessubdeptid) {
		this.dessubdeptid = dessubdeptid;
	}

	public Integer getDesmainlocid() {
		return desmainlocid;
	}

	public void setDesmainlocid(Integer desmainlocid) {
		this.desmainlocid = desmainlocid;
	}

	public Integer getDessublocid() {
		return dessublocid;
	}

	public void setDessublocid(Integer dessublocid) {
		this.dessublocid = dessublocid;
	}

	public String getIsReceived() {
		return isReceived;
	}

	public void setIsReceived(String isReceived) {
		this.isReceived = isReceived;
	}

	public String getRecuser() {
		return recuser;
	}

	public void setRecuser(String recuser) {
		this.recuser = recuser;
	}

	public String getRecdatetime() {
		return recdatetime;
	}

	public void setRecdatetime(String recdatetime) {
		this.recdatetime = recdatetime;
	}
	
}
