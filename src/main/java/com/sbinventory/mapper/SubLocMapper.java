package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.SubLoc;

public class SubLocMapper implements RowMapper<SubLoc>{

	@Override
	public SubLoc mapRow(ResultSet rs, int numRow) throws SQLException {
		// TODO Auto-generated method stub
		int sublocid = rs.getInt("ID");
		String sublocname = rs.getString("SUB_LOC_NAME");
		int mainlocid = rs.getInt("MAIN_LOC_ID");
		
		return new SubLoc(sublocid, sublocname, mainlocid);
	}

}
