package com.sims.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sims.model.AppRole;

public class AppRoleMapper implements RowMapper<AppRole>{

	@Override
	public AppRole mapRow(ResultSet rs, int numRow) throws SQLException {

		int roleid=rs.getInt("ID");
		String rolename=rs.getString("ROLE_NAME");
		
		return new AppRole(roleid, rolename);
	}
}
