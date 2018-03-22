package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.RMA;

public class RMAMapper implements RowMapper<RMA>{

	@Override
	public RMA mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int rmaid = rs.getInt("ID");
		String loguser = rs.getString("LOG_USER");
		String logdatetime = rs.getString("LOG_DATETIME");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
		int mainlocid = rs.getInt("MAIN_LOC");
		int sublocid = rs.getInt("SUB_LOC");		
		String approval = rs.getString("APPROVAL");
		String reason = rs.getString("REASON");
		
		return new RMA(rmaid, loguser, logdatetime, productid, quantity, mainlocid, sublocid, approval, reason);
	}

}
