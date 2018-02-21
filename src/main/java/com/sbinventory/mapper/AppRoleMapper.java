package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.AppRole;

public class AppRoleMapper implements RowMapper<AppRole>{

	@Override
	public AppRole mapRow(ResultSet rs, int numRow) throws SQLException {

		int roleid=rs.getInt("ROLE_ID");
		String rolename=rs.getString("ROLE_NAME");
		
		return new AppRole(roleid, rolename);
	}

}
