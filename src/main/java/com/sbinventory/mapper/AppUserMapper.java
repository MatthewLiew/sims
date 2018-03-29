package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.AppUser;

public class AppUserMapper implements RowMapper<AppUser>{

	public static final String BASE_SQL
		="SELECT ID, USER_NAME, ENCRYTED_PASSWORD FROM APP_USER";

	@Override
	public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {

		int userId=rs.getInt("ID");
		String userName = rs.getString("USER_NAME");
        String encrytedPassword = rs.getString("ENCRYTED_PASSWORD");
        
        return new AppUser(userId, userName, encrytedPassword);
	}
}
