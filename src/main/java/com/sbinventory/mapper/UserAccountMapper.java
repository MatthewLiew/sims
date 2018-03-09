package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.UserAccount;

public class UserAccountMapper implements RowMapper<UserAccount> {

	@Override
	public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {

		int userid=rs.getInt("ID");
		int usercode=rs.getInt("USER_CODE");
		String username = rs.getString("USER_NAME");
        String price = rs.getString("ENCRYTED_PASSWORD");
        int orgid=rs.getInt("ORG_ID");
        int deptid=rs.getInt("DEPT_ID");
        int subdeptid=rs.getInt("SUB_DEPT_ID");
        
        return new UserAccount(userid, usercode, username, price, orgid, deptid, subdeptid);
	}
}