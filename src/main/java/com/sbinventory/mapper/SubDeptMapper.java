package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.SubDept;

public class SubDeptMapper implements RowMapper<SubDept>{

	@Override
	public SubDept mapRow(ResultSet rs, int numRow) throws SQLException {
		// TODO Auto-generated method stub
		int subdeptid=rs.getInt("ID");
		int subdeptcode=rs.getInt("SUB_DEPT_CODE");
		String subdeptname=rs.getString("SUB_DEPT_NAME");
		int deptid=rs.getInt("DEPT_ID");
		
		return new SubDept(subdeptid, subdeptcode, subdeptname, deptid);
	}

}
