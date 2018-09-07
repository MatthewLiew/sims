package com.sims.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sims.model.RMA;

public class RMAMapper implements RowMapper<RMA>{

	@Override
	public RMA mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int rmaid = rs.getInt("ID");
		String code = rs.getString("CODE");
		String invoiceno = rs.getString("INVOICE_NO");
		String serialno = rs.getString("SERIAL_NO");
		String name = rs.getString("NAME");
		String email = rs.getString("EMAIL");
		String phoneno = rs.getString("PHONE_NO");
		String desc = rs.getString("DESCRIPTION");
		Integer rmareason = (Integer) rs.getObject("REASON");
		Integer rmatype = (Integer) rs.getObject("TYPE");
		String approval = rs.getString("APPROVAL");
		String rquser = rs.getString("REQUEST_USER");
		String rqdatetime = rs.getString("REQUEST_DATETIME");
		String loguser = rs.getString("LOG_USER");
		String logdatetime = rs.getString("LOG_DATETIME");
		
		return new RMA(rmaid, code, invoiceno, serialno, name, email, phoneno, desc, rmareason, rmatype, approval, rquser, rqdatetime, loguser, logdatetime);
	}

}
