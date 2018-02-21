package com.sbinventory.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sbinventory.model.Dept;

public class DeptMapper implements RowMapper<Dept>{

	@Override
	public Dept mapRow(ResultSet rs, int numRow) throws SQLException {
		
		int deptid=rs.getInt("ID");
		int deptcode=rs.getInt("DEPT_CODE");
		String deptname=rs.getString("DEPT_NAME");
		int orgid=rs.getInt("ORG_ID");
		
		return new Dept(deptid, deptcode, deptname, orgid);
	}

}
