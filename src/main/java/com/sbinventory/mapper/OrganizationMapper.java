package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.Organization;

public class OrganizationMapper implements RowMapper<Organization> {

	@Override
	public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int id = rs.getInt("id");
		boolean activeFlag = rs.getBoolean("activeFlag");
		String name = rs.getString("name");
		
		return new Organization(id, activeFlag, name);
	}
}