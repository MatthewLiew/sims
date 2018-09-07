package com.sims.model;

public class StockType {

	private int stocktypeid;
	private String stocktype;
	
	public StockType() {}

	public StockType(int stocktypeid, String stocktype) {
		this.stocktypeid = stocktypeid;
		this.stocktype = stocktype;
	}

	public int getStocktypeid() {
		return stocktypeid;
	}

	public void setStocktypeid(int stocktypeid) {
		this.stocktypeid = stocktypeid;
	}

	public String getStocktype() {
		return stocktype;
	}

	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}
	
	public boolean isStockType(int stocktypeid) {
		if(this.stocktypeid==stocktypeid) {
			return true;
		} else {
			return false;
		}
	}
	
}
