package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.Storage;

public class StorageMapper implements RowMapper<Storage>{

	@Override
	public Storage mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int storageid = rs.getInt("ID");
		int mainlocid = rs.getInt("MAIN_LOC_ID");
		int sublocid = rs.getInt("SUB_LOC_ID");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
		
		return new Storage (storageid, mainlocid, sublocid, productid, quantity);
	}

}
