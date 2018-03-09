package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.Hardware;

public class HardwareMapper implements RowMapper<Hardware> {

	@Override
	public Hardware mapRow(ResultSet rs, int nuwRow) throws SQLException {

		int hardwareid = rs.getInt("ID");
		int hardwarecode = rs.getInt("HARDWARE_CODE");
		String hardwaretype = rs.getString("HARDWARE_TYPE");
		
		return new Hardware(hardwareid, hardwarecode, hardwaretype);
	}
}