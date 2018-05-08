package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.RMAItemReason;

public class RMAItemReasonMapper implements RowMapper<RMAItemReason>{

	@Override
	public RMAItemReason mapRow(ResultSet rs, int numRow) throws SQLException {
		// TODO Auto-generated method stub
		int id = rs.getInt("ID");
		String name = rs.getString("NAME");
		
		return new RMAItemReason(id, name);
	}

}
