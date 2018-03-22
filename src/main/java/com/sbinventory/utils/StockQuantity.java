package com.sbinventory.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbinventory.dao.DisposalHistoryDAO;
import com.sbinventory.dao.ProductDAO;
import com.sbinventory.dao.RMADAO;
import com.sbinventory.dao.StockHistoryDAO;
import com.sbinventory.dao.StorageDAO;
import com.sbinventory.dao.TransferHistoryDAO;
import com.sbinventory.model.DisposalHistory;
import com.sbinventory.model.Product;
import com.sbinventory.model.RMA;
import com.sbinventory.model.StockHistory;
import com.sbinventory.model.Storage;
import com.sbinventory.model.TransferHistory;

@Service
public class StockQuantity {
	
	@Autowired
	private StockHistoryDAO stockHistoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private StorageDAO storageDAO;
	
	@Autowired
	private TransferHistoryDAO transferHistoryDAO;
	
	@Autowired
	private DisposalHistoryDAO disposalHistoryDAO;
	
	@Autowired
	private RMADAO rmaDAO;
	
	public void update() {
		
		this.updatestockio();
		this.updatetransfer();
		this.updatedisposal();
		this.updaterma();
		
		List<Product> products = productDAO.getAllProduct();
		for(Product prod: products){
			
			int totalquantity=0;
			List<Storage> storages = storageDAO.getAllStorage();
			for(Storage stor: storages) {
				if(prod.getProductid() == stor.getProductid()) {
					totalquantity+=stor.getQuantity();
				}
			}
			productDAO.updateQuantity(prod.getProductid(), totalquantity);
		}
	}
	
	public void updatestockio() {
		
		List<Storage> storages = storageDAO.getAllStorage();
		for(Storage stor : storages){
			
			int total = 0;
			List<StockHistory> stocks = stockHistoryDAO.getAllStockHistory();
			for(StockHistory stock: stocks) {
				
				if((stor.getMainlocid() == stock.getMainlocid()) && (stor.getSublocid() == stock.getSublocid())
						&& (stor.getProductid()==stock.getProductid()) && (stock.getApproval().equalsIgnoreCase("approved"))) {
					
					if(stock.getStocktypeid()==1) {
						total += stock.getQuantity();
					} else {
						total -= stock.getQuantity();
					}
				}
			}
			storageDAO.updateQuantity(stor.getStorageid(), total);
		}
	}

	public void updatetransfer() {
		
		List<Storage> storages = storageDAO.getAllStorage();
		for(Storage stor : storages){
			
			int totalquantity=0;
			List<TransferHistory> transferHistories = transferHistoryDAO.getAllTransferHistory();
			for(TransferHistory trans: transferHistories){
				
				if((stor.getMainlocid() == trans.getOrimainlocid()) && (stor.getSublocid() == trans.getOrisublocid())
						&& (stor.getProductid() == trans.getProductid())) {
					totalquantity-=trans.getQuantity();
				} else if((stor.getMainlocid() == trans.getDesmainlocid()) && (stor.getSublocid() == trans.getDessublocid())
						&& (stor.getProductid() == trans.getProductid())){
					totalquantity+=trans.getQuantity();
				}
			}
			storageDAO.updateQuantity(stor.getStorageid(), totalquantity+stor.getQuantity());
		}
	}
	
	public void updatedisposal() {
		
		List<Storage> storages = storageDAO.getAllStorage();
		for(Storage stor : storages){
			
			int totalquantity=0;
			List<DisposalHistory> disposalHistories = disposalHistoryDAO.getAllDisposalHistory();
			for(DisposalHistory dis: disposalHistories){
				
				if((stor.getMainlocid() == dis.getMainlocid()) && (stor.getSublocid() == dis.getSublocid())
						&& (stor.getProductid() == dis.getProductid())) {
					totalquantity-=dis.getQuantity();
				} 
			}
			storageDAO.updateQuantity(stor.getStorageid(), totalquantity+stor.getQuantity());
		}
	}
	
	public void updaterma() {
		
		List<Storage> storages = storageDAO.getAllStorage();
		for(Storage stor : storages){
			
			int totalquantity=0;
			List<RMA> rmas = rmaDAO.getAllRMA();
			for(RMA rma: rmas){
				
				if((stor.getMainlocid() == rma.getMainlocid()) && (stor.getSublocid() == rma.getSublocid())
						&& (stor.getProductid() == rma.getProductid())) {
					totalquantity+=rma.getQuantity();
				} 
			}
			storageDAO.updateQuantity(stor.getStorageid(), totalquantity+stor.getQuantity());
		}
	}
}
