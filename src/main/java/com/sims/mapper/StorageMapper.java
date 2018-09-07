package com.sims.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sims.model.Storage;

public class StorageMapper implements RowMapper<Storage>{

	@Override
	public Storage mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int storageid = rs.getInt("ID");
		int orgid = rs.getInt("ORG_ID");
		int deptid = rs.getInt("DEPT_ID");
		int subdeptid = rs.getInt("SUB_DEPT_ID");
		int mainlocid = rs.getInt("MAIN_LOC_ID");
		int sublocid = rs.getInt("SUB_LOC_ID");
		int productid = rs.getInt("PRODUCT_ID");
		int quantity = rs.getInt("QUANTITY");
		
//		int orgid = 0, deptid = 0, subdeptid = 0;
		
		return new Storage (storageid, orgid, deptid, subdeptid, mainlocid, sublocid, productid, quantity);
	}

}
