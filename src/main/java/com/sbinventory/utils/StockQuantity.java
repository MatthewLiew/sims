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
//		this.updatedisposal();
//		this.updaterma();
		
		List<Product> products = productDAO.findAll();
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
			List<StockHistory> stocks = stockHistoryDAO.findAll();
			for(StockHistory stock: stocks) {
				
				if((stor.getOrgid() == stock.getOrgid())&&(stor.getDeptid() == stock.getDeptid())&&(stor.getSubdeptid() == stock.getSubdeptid())
						&&(stor.getMainlocid() == stock.getMainlocid()) && (stor.getSublocid() == stock.getSublocid())
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
			List<TransferHistory> transferHistories = transferHistoryDAO.findAll();
			for(TransferHistory th: transferHistories){
				
				if((stor.getOrgid() == th.getSrcorgid())&&(stor.getDeptid() == th.getSrcdeptid())&&(stor.getSubdeptid() == th.getSrcsubdeptid())
						&& (stor.getMainlocid() == th.getSrcmainlocid()) && (stor.getSublocid() == th.getSrcsublocid())
						&& (stor.getProductid() == th.getProductid()) && (th.getIsTransfered().equalsIgnoreCase("approved"))) {
					totalquantity-=th.getQuantity();
				} 
				if((stor.getOrgid() == th.getDesorgid())&&(stor.getDeptid() == th.getDesdeptid())&&(stor.getSubdeptid() == th.getDessubdeptid())
						&& (stor.getMainlocid() == th.getDesmainlocid()) && (stor.getSublocid() == th.getDessublocid())
						&& (stor.getProductid() == th.getProductid()) && (th.getIsReceived().equalsIgnoreCase("received"))){
					totalquantity+=th.getQuantity();
				}
			}
			storageDAO.updateQuantity(stor.getStorageid(), totalquantity+stor.getQuantity());
		}
	}
	
//	public void updatedisposal() {
//		
//		List<Storage> storages = storageDAO.getAllStorage();
//		for(Storage stor : storages){
//			
//			int totalquantity=0;
//			List<DisposalHistory> disposalHistories = disposalHistoryDAO.findAll();
//			for(DisposalHistory dis: disposalHistories){
//				
//				if((stor.getMainlocid() == dis.getMainlocid()) && (stor.getSublocid() == dis.getSublocid())
//						&& (stor.getProductid() == dis.getProductid())) {
//					totalquantity-=dis.getQuantity();
//				} 
//			}
//			storageDAO.updateQuantity(stor.getStorageid(), totalquantity+stor.getQuantity());
//		}
//	}
	
//	public void updaterma() {
//		
//		List<Storage> storages = storageDAO.getAllStorage();
//		for(Storage stor : storages){
//			
//			int totalquantity=0;
//			List<RMA> rmas = rmaDAO.findAll();
//			for(RMA rma: rmas){
//				
//				if((stor.getMainlocid() == rma.getMainlocid()) && (stor.getSublocid() == rma.getSublocid())
//						&& (stor.getProductid() == rma.getProductid())) {
//					totalquantity+=rma.getQuantity();
//				} 
//			}
//			storageDAO.updateQuantity(stor.getStorageid(), totalquantity+stor.getQuantity());
//		}
//	}
}
