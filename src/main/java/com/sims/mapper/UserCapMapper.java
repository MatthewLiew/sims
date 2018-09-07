package com.sims.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sims.model.UserCap;

public class UserCapMapper implements RowMapper<UserCap>{

	@Override
	public UserCap mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int usercapid = rs.getInt("ID");
		int approleid = rs.getInt("APP_ROLE_ID");
		int accessright = rs.getInt("ACCESS_RIGHT");
		int sioapprove = rs.getInt("SIO_APPROVE");
		int sioadd = rs.getInt("SIO_ADD");
		int sioedit = rs.getInt("SIO_EDIT");
		int siodelete = rs.getInt("SIO_DELETE"); 
		int smadd = rs.getInt("SM_ADD");
		int smedit = rs.getInt("SM_EDIT");
		int smdelete = rs.getInt("SM_DELETE"); 
		int sttransfer = rs.getInt("ST_TRANSFER");
		int streceive = rs.getInt("ST_RECEIVE");
		int stapprove = rs.getInt("ST_APPROVE");
		int stedit = rs.getInt("ST_EDIT");
		int stdelete = rs.getInt("ST_DELETE");
		int sddispose = rs.getInt("SD_DISPOSE");
		int sdapprove = rs.getInt("SD_APPROVE");
		int sdedit = rs.getInt("SD_EDIT");
		int sddelete = rs.getInt("SD_DELETE");

		return new UserCap(usercapid, approleid, accessright, sioapprove, sioadd, sioedit, siodelete, smadd, smedit, smdelete,
				sttransfer, streceive, stapprove, stedit, stdelete, sddispose, sdapprove, sdedit, sddelete);
	}

}
