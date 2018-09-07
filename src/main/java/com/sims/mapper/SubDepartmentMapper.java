package com.sims.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sims.model.SubDepartment;

public class SubDepartmentMapper implements RowMapper<SubDepartment>{

	@Override
	public SubDepartment mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int id = rs.getInt("id");
		boolean activeFlag = rs.getBoolean("activeFlag");
		String name = rs.getString("name");
		Integer departmentId = rs.getInt("departmentId");
		String departmentName = rs.getString("departmentName");
		Integer organizationId = rs.getInt("organizationId");
		String organizationName = rs.getString("organizationName");
		
		return new SubDepartment(id, activeFlag, name, departmentId, departmentName, organizationId, organizationName);
	}
}