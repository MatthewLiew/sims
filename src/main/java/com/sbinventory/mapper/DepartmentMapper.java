package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.Department;

public class DepartmentMapper implements RowMapper<Department>{

	@Override
	public Department mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int id = rs.getInt("id");
		boolean activeFlag = rs.getBoolean("activeFlag");
		String name = rs.getString("name");
		Integer organizationId = rs.getInt("organizationId");
		String organizationName = rs.getString("organizationName");

		return new Department(id, activeFlag, name, organizationId, organizationName);
	}
}
