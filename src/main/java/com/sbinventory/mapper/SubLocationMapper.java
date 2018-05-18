package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.SubLocation;

public class SubLocationMapper implements RowMapper<SubLocation>{

	@Override
	public SubLocation mapRow(ResultSet rs, int numRow) throws SQLException {

		int id = rs.getInt("id");
		boolean activeFlag = rs.getBoolean("activeFlag");
		String name = rs.getString("name");
		String description = rs.getString("description");
		Integer mainLocationId = rs.getInt("mainLocationId");
		String mainLocationName = rs.getString("mainLocationName");
		
		return new SubLocation(id, activeFlag, name, description, mainLocationId, mainLocationName);
	}
}