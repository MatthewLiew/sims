package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.PartNo;

public class PartNoMapper implements RowMapper<PartNo>{

	@Override
	public PartNo mapRow(ResultSet rs, int numRow) throws SQLException {
		// TODO Auto-generated method stub
		int partnoid = rs.getInt("ID");
		String serialno = rs.getString("SERIAL_NO");
		String modelno = rs.getString("MODEL_NO");
		String upccode = rs.getString("UPC_CODE");
		
		return new PartNo(partnoid, serialno, modelno, upccode);
	}

}
