package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.MainLoc;

public class MainLocMapper implements RowMapper<MainLoc>{

	@Override
	public MainLoc mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int mainlocid = rs.getInt("ID");
		String mainlocname = rs.getString("MAIN_LOC_NAME");
		
		return new MainLoc(mainlocid, mainlocname);
	}
}