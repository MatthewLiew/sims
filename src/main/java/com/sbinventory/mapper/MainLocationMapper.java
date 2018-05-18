package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.MainLocation;

public class MainLocationMapper implements RowMapper<MainLocation>{

	@Override
	public MainLocation mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int id = rs.getInt("id");
		boolean activeFlag = rs.getBoolean("activeFlag"); 
		String name = rs.getString("name");
		
		return new MainLocation(id, activeFlag, name);
	}
}