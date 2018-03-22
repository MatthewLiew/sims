package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.ITF;
import com.sbinventory.model.RMA;

public class ITFMapper implements RowMapper<ITF>{

	@Override
	public ITF mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int itfid = rs.getInt("ID");
		String loguser = rs.getString("LOG_USER");
		String logdatetime = rs.getString("LOG_DATETIME");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
		int mainlocid = rs.getInt("MAIN_LOC");
		int sublocid = rs.getInt("SUB_LOC");		
		String approval = rs.getString("APPROVAL");
		
		return new ITF(itfid, loguser, logdatetime, productid, quantity, mainlocid, sublocid, approval);
	}

}
