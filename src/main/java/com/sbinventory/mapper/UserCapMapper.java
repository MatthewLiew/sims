package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.UserCap;

public class UserCapMapper implements RowMapper<UserCap>{

	@Override
	public UserCap mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int usercapid = rs.getInt("ID");
		int approleid = rs.getInt("APP_ROLE_ID");
		int accessright = rs.getInt("ACCESS_RIGHT");
		int approve = rs.getInt("APPROVE");
		int add = rs.getInt("ADD_");
		int edit = rs.getInt("EDIT_");
		int delete = rs.getInt("DELETE_"); 
		
		return new UserCap(usercapid, approleid, accessright, approve, add, edit, delete);
	}

}
