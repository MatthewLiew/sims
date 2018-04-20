package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.TransferHistory;

public class TransferHistoryMapper implements RowMapper<TransferHistory> {

	@Override
	public TransferHistory mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int transferhistoryid = rs.getInt("ID");
		String code = rs.getString("CODE");
		String loguser = rs.getString("LOG_USER");
		String logdatetime = rs.getString("LOG_DATETIME");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
		String serialno = rs.getString("SERIAL_NO");
		String source = rs.getString("SOURCE");
		String destination = rs.getString("DESTINATION");
		String approval = rs.getString("APPROVAL");
		int transfertype = rs.getInt("TRANSFER_TYPE");
		
//		return null;
		return new TransferHistory(transferhistoryid, code, /*loguser, logdatetime, */productid, quantity, 
				serialno, transfertype,  source, destination, approval);
	}

}
