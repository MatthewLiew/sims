package com.sims.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sims.model.Product;

public class ProductMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int numRow) throws SQLException {
		// TODO Auto-generated method stub
		int productid=rs.getInt("ID");
		int productcode=rs.getInt("PRODUCT_CODE");
		String productname=rs.getString("PRODUCT_NAME");
		int hardwareid=rs.getInt("HARDWARE_ID");
		int brandid=rs.getInt("BRAND_ID");
		int partnoid=rs.getInt("PART_NO_ID");
		int lbvalue=rs.getInt("LB_VALUE");
		int quantity=rs.getInt("QUANTITY");
		
		return new Product(productid, productcode, productname, hardwareid, brandid, partnoid, lbvalue, quantity);
	}
}