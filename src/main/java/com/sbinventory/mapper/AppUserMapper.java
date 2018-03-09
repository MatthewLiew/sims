package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.AppUser;

public class AppUserMapper implements RowMapper<AppUser>{

	public static final String BASE_SQL
		="Select u.ID, u.User_Code, u.User_Name, u.Encryted_Password From App_User u";

	@Override
	public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {

		int userId=rs.getInt("Id");
		int userCode=rs.getInt("User_Code");
		String userName = rs.getString("User_Name");
        String encrytedPassword = rs.getString("Encryted_Password");
        return new AppUser(userId, userCode, userName, encrytedPassword);
	}
}
