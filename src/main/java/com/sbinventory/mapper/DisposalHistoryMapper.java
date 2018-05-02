package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.DisposalHistory;

public class DisposalHistoryMapper implements RowMapper<DisposalHistory>{

	@Override
	public DisposalHistory mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int disposalhistoryid = rs.getInt("ID");
		String code = rs.getString("CODE");
		Integer productid = rs.getInt("PRODUCT_ID");
		Integer quantity = rs.getInt("QUANTITY");
		String serialno = rs.getString("SERIAL_NO");
		Integer orgid = rs.getInt("ORG_ID");
		Integer deptid = rs.getInt("DEPT_ID");
		Integer subdeptid = rs.getInt("SUB_DEPT_ID");
		Integer mainlocid = rs.getInt("MAIN_LOC_ID");
		Integer sublocid = rs.getInt("SUB_LOC_ID");		
		String approval = rs.getString("APPROVAL");
		Integer loguser = (Integer) rs.getObject("LOG_USER");
		String logdatetime = rs.getString("LOG_DATETIME");
		
		return new DisposalHistory(disposalhistoryid, code, productid, quantity, serialno, orgid, deptid, subdeptid, mainlocid, sublocid, 
				approval, loguser, logdatetime);
	}

}
