package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.StockType;

public class StockTypeMapper implements RowMapper<StockType>{

	@Override
	public StockType mapRow(ResultSet rs, int numRow) throws SQLException {

		int stocktypeid = rs.getInt("ID");
		String stocktype = rs.getString("STOCK_TYPE");
		
		return new StockType(stocktypeid, stocktype);
	}

}
