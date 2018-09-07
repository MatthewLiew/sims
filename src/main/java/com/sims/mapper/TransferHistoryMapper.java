package com.sims.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sims.model.TransferHistory;

public class TransferHistoryMapper implements RowMapper<TransferHistory> {

	@Override
	public TransferHistory mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int transferhistoryid = rs.getInt("ID");
		String code = rs.getString("CODE");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
		String serialno = rs.getString("SERIAL_NO");
		String source = rs.getString("SOURCE");
		String destination = rs.getString("DESTINATION");
		int transfertype = rs.getInt("TRANSFER_TYPE");
		int srcorgid = rs.getInt("SRC_ORG_ID");
		int srcdeptid = rs.getInt("SRC_DEPT_ID");
		int srcsubdeptid = rs.getInt("SRC_SUB_DEPT_ID");
		int srcmainlocid = rs.getInt("SRC_MAIN_LOC_ID");
		int srcsublocid = rs.getInt("SRC_SUB_LOC_ID");
		String isTransfered = rs.getString("TRANSFERED");
		String tfruser = rs.getString("TFR_USER");
		String tfrdatetime = rs.getString("TFR_DATETIME");
		Integer desorgid = (Integer) rs.getObject("DES_ORG_ID");
		Integer desdeptid = (Integer) rs.getObject("DES_DEPT_ID");
		Integer dessubdeptid = (Integer) rs.getObject("DES_SUB_DEPT_ID");
		Integer desmainlocid = (Integer) rs.getObject("DES_MAIN_LOC_ID");
		Integer dessublocid = (Integer) rs.getObject("DES_SUB_LOC_ID");
		String isReceived = rs.getString("RECEIVED");
		String recuser = rs.getString("REC_USER");
		String recdatetime = rs.getString("REC_DATETIME");
		
//		return null;
		return new TransferHistory(transferhistoryid, code, productid, quantity, serialno,
				 source, destination, transfertype, srcorgid, srcdeptid,
				 srcsubdeptid, srcmainlocid, srcsublocid, isTransfered, tfruser,
				 tfrdatetime, desorgid, desdeptid, dessubdeptid, desmainlocid,
				 dessublocid, isReceived, recuser, recdatetime);
	}

}
