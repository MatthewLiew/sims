package com.sbinventory.model;

public class TransferType {

	private int transfertypeid;
	private String mode;
	
	public TransferType() {}
	
	public TransferType(int transfertypeid, String mode) {
		this.transfertypeid = transfertypeid;
		this.mode = mode;
	}

	public int getTransfertypeid() {
		return transfertypeid;
	}

	public void setTransfertypeid(int transfertypeid) {
		this.transfertypeid = transfertypeid;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
