package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.TransferType;

public class TransferTypeMapper implements RowMapper<TransferType>{

	@Override
	public TransferType mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int transfertypeid = rs.getInt("ID");
		String mode = rs.getString("MODE");
		
		return new TransferType(transfertypeid, mode);
	}

}
