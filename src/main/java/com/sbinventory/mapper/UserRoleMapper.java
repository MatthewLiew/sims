package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.UserRole;

public class UserRoleMapper implements RowMapper<UserRole>{

	@Override
	public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int userid=rs.getInt("USER_ID");
		int roleid=rs.getInt("ROLE_ID");
		
		return new UserRole(userid, roleid);
	}
}