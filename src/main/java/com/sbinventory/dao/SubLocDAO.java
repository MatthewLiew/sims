package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.MainLocMapper;
import com.sbinventory.mapper.SubLocMapper;
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.SubLoc;

@Repository
@Transactional
public class SubLocDAO extends JdbcDaoSupport{

	private static final String READ_SQL="SELECT * FROM SUB_LOC";
	private static final String CREATE_SQL="INSERT INTO SUB_LOC (SUB_LOC_NAME, MAIN_LOC_ID) VALUES (?,?)";
	private static final String UPDATE_SQL="UPDATE SUB_LOC";
	private static final String DELETE_SQL="DELETE FROM SUB_LOC";
	
	@Autowired
	public SubLocDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String createSubLoc(String sublocname, int mainlocid) {
		
		Object[] params=new Object[]{sublocname, mainlocid};
		String sql=CREATE_SQL;
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e ) {
			return e.getMessage();
			
		}catch(DataAccessException  e) {
//			throw new DataAccessException("Something error", e);
			return e.getMessage();
		}
	}
	
	public List<SubLoc> getAllSubLoc(){
		
		String sql=READ_SQL ;
		SubLocMapper mapper=new SubLocMapper();
		
		try {
            List<SubLoc> sublocs =  this.getJdbcTemplate().query(sql, mapper);
            return sublocs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<SubLoc> getAllSubLoc(int mainlocid){
		
		String sql=READ_SQL+" where MAIN_LOC_ID = ?"; ;
		Object[] params=new Object[] {mainlocid};
		SubLocMapper mapper=new SubLocMapper();
		
		try {
            List<SubLoc> sublocs =  this.getJdbcTemplate().query(sql, params, mapper);
            return sublocs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public SubLoc getSubLoc(int sublocid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {sublocid};
		SubLocMapper mapper=new SubLocMapper();
		
		try {
			SubLoc subloc = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return subloc;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public SubLoc getSubLocName(String sublocname){
		String sql=READ_SQL+" where SUB_LOC_NAME = ?";
		Object[] params= new Object[] {sublocname};
		SubLocMapper mapper=new SubLocMapper();
		
		try {
			SubLoc subloc = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return subloc;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public SubLoc getSubLocName(String sublocname, int sublocid){
		String sql=READ_SQL+" where SUB_LOC_NAME = ? AND ID != ?";
		Object[] params= new Object[] {sublocname, sublocid};
		SubLocMapper mapper=new SubLocMapper();
		
		try {
			SubLoc subloc = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return subloc;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public String updateSubLoc(int sublocid, String sublocname ){
		String sql=UPDATE_SQL+" set SUB_LOC_NAME = ? where ID= ?";
		Object[] params=new Object[]{ sublocname, sublocid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return e.getMessage();
		}catch(DataAccessException  e) {
			return e.getMessage();
		}
	}
	
	public void deleteSubLoc(int sublocid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {sublocid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
