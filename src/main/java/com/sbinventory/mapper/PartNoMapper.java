package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.PartNo;

public class PartNoMapper implements RowMapper<PartNo>{

	@Override
	public PartNo mapRow(ResultSet rs, int numRow) throws SQLException {
		// TODO Auto-generated method stub
		int partnoid = rs.getInt("ID");
		String serialno = rs.getString("SERIAL_NO");
		String modelno = rs.getString("MODEL_NO");
		String upccode = rs.getString("UPC_CODE");
		int productid = rs.getInt("PRODUCT_ID");
		String customername = rs.getString("CUSTOMER_NAME");
		String invoiceno = rs.getString("INVOICE_NO");
		int mainlocid = rs.getInt("MAIN_LOC_ID");
		int sublocid = rs.getInt("SUB_LOC_ID");
		int orgid = rs.getInt("ORG_ID");
		int deptid = rs.getInt("DEPT_ID");
		int subdeptid = rs.getInt("SUB_DEPT_ID");
		String status = rs.getString("STOCK_STATUS");
		
		return new PartNo(partnoid, serialno, modelno, upccode, productid, customername, invoiceno, mainlocid, sublocid, orgid, deptid, subdeptid, status);
	}
}