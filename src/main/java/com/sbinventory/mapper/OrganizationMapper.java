package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.Organization;

public class OrganizationMapper implements RowMapper<Organization> {

	@Override
	public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		int orgid=rs.getInt("ID");
		int orgcode=rs.getInt("ORG_CODE");
		String orgname=rs.getString("ORG_NAME");
		
		return new Organization(orgid, orgcode, orgname);
	}

}
