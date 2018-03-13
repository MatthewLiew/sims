package com.sbinventory.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sbinventory.dao.ProductDAO;
import com.sbinventory.dao.StockHistoryDAO;
import com.sbinventory.model.Product;
import com.sbinventory.model.StockHistory;

public class StockQuantity {
	
	public static void update(ProductDAO productDAO, StockHistoryDAO stockHistoryDAO) {
		
		List<Product> products = productDAO.getAllProduct();
		for(Product p: products){
			
			int totalquantity=0;
			List<StockHistory> stockquantity = stockHistoryDAO.getStockQuantity(p.getProductid());
			for(StockHistory s: stockquantity) {
				if(s.getApproval().equalsIgnoreCase("approved")) {
					if(s.getStocktypeid()==1) {
						totalquantity+=s.getQuantity();
					} else {
						totalquantity-=s.getQuantity();
					}
				}
			}
			System.out.println(p.getProductname()+" Total: "+totalquantity);
			productDAO.updateQuantity(p.getProductid(), totalquantity);
		}
	}
}
