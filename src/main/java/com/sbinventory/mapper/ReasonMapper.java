package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.Reason;

public class ReasonMapper implements RowMapper<Reason>{

	@Override
	public Reason mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int reasonid = rs.getInt("ID");
		String reason = rs.getString("REASON");
		int stocktypeid = rs.getInt("STOCK_TYPE_ID");
		
		return new Reason(reasonid, reason, stocktypeid);
	}

}
