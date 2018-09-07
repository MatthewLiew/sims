package com.sims.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sims.dao.DisposalHistoryDAO;
import com.sims.dao.RMADAO;
import com.sims.dao.StockHistoryDAO;
import com.sims.dao.TransferHistoryDAO;

@Service
public class OrderCodeGenerator {

	@Autowired
	private StockHistoryDAO stockHistoryDAO;
	
	@Autowired
	private TransferHistoryDAO transferHistoryDAO;
	
	@Autowired
	private DisposalHistoryDAO disposalHistoryDAO;
	
	@Autowired
	private RMADAO rmaDAO;
	
	public String getOrderCode(String module) {
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(Date.from(Instant.now()));
        
		if(module.equalsIgnoreCase("stockdisposal")) {
			int dhIndent = disposalHistoryDAO.getCurrentIdent();
			String code = String.format("SD-%ty%tm%td%06d", cal, cal, cal, dhIndent+1);
			return code;
			
		} else if(module.equalsIgnoreCase("stocktransfer")) {
			int dhIndent = transferHistoryDAO.getCurrentIdent();
			String code = String.format("ST-%ty%tm%td%06d", cal, cal, cal, dhIndent+1);
			return code;
			
		} else if(module.equalsIgnoreCase("rma")) {
			int dhIndent = rmaDAO.getCurrentIdent();
			String code = String.format("SR-%ty%tm%td%06d", cal, cal, cal, dhIndent+1);
			return code;
			
		} else {
			return null;
		}
	}
}
