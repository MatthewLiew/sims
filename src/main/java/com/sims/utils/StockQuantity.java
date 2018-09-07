package com.sims.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sims.dao.DisposalHistoryDAO;
import com.sims.dao.ProductDAO;
import com.sims.dao.RMADAO;
import com.sims.dao.StockHistoryDAO;
import com.sims.dao.StorageDAO;
import com.sims.dao.TransferHistoryDAO;
import com.sims.model.DisposalHistory;
import com.sims.model.Product;
import com.sims.model.RMA;
import com.sims.model.StockHistory;
import com.sims.model.Storage;
import com.sims.model.TransferHistory;

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
				
				if(th.getIsTransfered().equalsIgnoreCase("approved")) {
					if((stor.getOrgid() == th.getSrcorgid())&&(stor.getDeptid() == th.getSrcdeptid())&&(stor.getSubdeptid() == th.getSrcsubdeptid())
							&& (stor.getMainlocid() == th.getSrcmainlocid()) && (stor.getSublocid() == th.getSrcsublocid())
							&& (stor.getProductid() == th.getProductid())) {
						
						totalquantity-=th.getQuantity();
						System.out.println("YES "+totalquantity);
					}
				}
				if(th.getIsReceived().equalsIgnoreCase("received")) {
					if((stor.getOrgid() == th.getDesorgid())&&(stor.getDeptid() == th.getDesdeptid())&&(stor.getSubdeptid() == th.getDessubdeptid())
							&& (stor.getMainlocid() == th.getDesmainlocid()) && (stor.getSublocid() == th.getDessublocid())
							&& (stor.getProductid() == th.getProductid())){
						totalquantity+=th.getQuantity();
						System.out.println("NO "+totalquantity);
					}
				}
			}
			storageDAO.updateQuantity(stor.getStorageid(), totalquantity+stor.getQuantity());
		}
	}
	
	public void updatedisposal() {
		
		List<Storage> storages = storageDAO.getAllStorage();
		for(Storage stor : storages){
			
			int totalquantity=0;
			List<DisposalHistory> disposalHistories = disposalHistoryDAO.findAll();
			for(DisposalHistory dh: disposalHistories){
				
				if(dh.getApproval().equalsIgnoreCase("approved")) {
					if((stor.getOrgid() == dh.getOrgid())&&(stor.getDeptid() == dh.getDeptid())&&(stor.getSubdeptid() == dh.getSubdeptid())
							&& (stor.getMainlocid() == dh.getMainlocid()) && (stor.getSublocid() == dh.getSublocid())
							&& (stor.getProductid() == dh.getProductid())) {
						totalquantity-=dh.getQuantity();
					} 
				}
			}
			storageDAO.updateQuantity(stor.getStorageid(), totalquantity+stor.getQuantity());
		}
	}
	
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
