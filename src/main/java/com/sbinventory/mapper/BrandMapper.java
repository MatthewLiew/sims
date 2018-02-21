package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.Brand;

public class BrandMapper implements RowMapper<Brand> {

	@Override
	public Brand mapRow(ResultSet rs, int numRow) throws SQLException {
		// TODO Auto-generated method stub
		int brandid = rs.getInt("ID");
		int brandcode = rs.getInt("BRAND_CODE");
		String brandname = rs.getString("BRAND_NAME");
		
		return new Brand(brandid, brandcode, brandname);
	}

}
