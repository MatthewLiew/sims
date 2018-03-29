package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.UserAccount;

public class UserAccountMapper implements RowMapper<UserAccount> {

	@Override
	public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {

		int userid=rs.getInt("ID");
		String username = rs.getString("USER_NAME");
        String price = rs.getString("ENCRYTED_PASSWORD");
        Integer orgid=rs.getInt("ORG_ID");
        Integer deptid=rs.getInt("DEPT_ID");
        Integer subdeptid=rs.getInt("SUB_DEPT_ID");
        Integer roleid=rs.getInt("ROLE_ID");
        
        return new UserAccount(userid, username, price, orgid, deptid, subdeptid, roleid);
	}
}