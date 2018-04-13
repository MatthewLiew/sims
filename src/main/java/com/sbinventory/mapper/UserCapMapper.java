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
		int stockapprove = rs.getInt("STOCK_APPROVE");
		int stockadd = rs.getInt("STOCK_ADD");
		int stockedit = rs.getInt("STOCK_EDIT");
		int stockdelete = rs.getInt("STOCK_DELETE"); 
		int serialadd = rs.getInt("SERIAL_ADD");
		int serialedit = rs.getInt("SERIAL_EDIT");
		int serialdelete = rs.getInt("SERIAL_DELETE"); 

		return new UserCap(usercapid, approleid, accessright, stockapprove, stockadd, stockedit, stockdelete, serialadd, serialedit, serialdelete);
	}

}
