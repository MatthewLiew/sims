package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.TransferHistory;

public class TransferHistoryMapper implements RowMapper<TransferHistory> {

	@Override
	public TransferHistory mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int transferhistoryid = rs.getInt("ID");
		String loguser = rs.getString("LOG_USER");
		String logdatetime = rs.getString("LOG_DATETIME");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
//		int orimainlocid = rs.getInt("ORI_MAIN_LOC");
//		int orisublocid = rs.getInt("ORI_SUB_LOC");		
//		int desmainlocid = rs.getInt("DES_MAIN_LOC");
//		int dessublocid = rs.getInt("DES_SUB_LOC");	
		String approval = rs.getString("APPROVAL");
		
//		return null;
		return new TransferHistory(transferhistoryid, loguser, logdatetime, productid, quantity, /*orimainlocid, orisublocid, desmainlocid, dessublocid,*/ approval);
	}

}
