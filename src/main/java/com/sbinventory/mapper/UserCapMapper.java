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
		boolean accessright = rs.getBoolean("ACCESS_RIGHT");
		boolean approve = rs.getBoolean("APPROVE");
		boolean add = rs.getBoolean("ADD_");
		boolean edit = rs.getBoolean("EDIT_");
		boolean delete = rs.getBoolean("DELETE_"); 
		
		System.out.println(usercapid);
		System.out.println(approve);
		
		return new UserCap(usercapid, approleid, accessright, approve, add, edit, delete);
	}

}
