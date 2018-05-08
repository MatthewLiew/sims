package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.RMAItemReason;
import com.sbinventory.model.RMAItemType;

public class RMAItemTypeMapper implements RowMapper<RMAItemType>{

	@Override
	public RMAItemType mapRow(ResultSet rs, int numRow) throws SQLException {
		// TODO Auto-generated method stub
		int id = rs.getInt("ID");
		String name = rs.getString("NAME");
		
		return new RMAItemType(id, name);
	}

}
