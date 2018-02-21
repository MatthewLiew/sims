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
import com.sbinventory.model.MainLoc;

@Repository
@Transactional
public class MainLocDAO extends JdbcDaoSupport{
	
	private static final String READ_SQL="SELECT * FROM MAIN_LOC";
	private static final String CREATE_SQL="INSERT INTO MAIN_LOC (MAIN_LOC_NAME) VALUES (?)";
	private static final String UPDATE_SQL="UPDATE MAIN_LOC";
	private static final String DELETE_SQL="DELETE FROM MAIN_LOC";
	
	@Autowired
	public MainLocDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String createMainLoc(String mainlocname) {
		
		Object[] params=new Object[]{mainlocname};
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
	
	public List<MainLoc> getAllMainLoc(){
		
		String sql=READ_SQL + " ORDER BY MAIN_LOC_NAME";
		MainLocMapper mapper=new MainLocMapper();
		
		try {
            List<MainLoc> mainlocs =  this.getJdbcTemplate().query(sql, mapper);
            return mainlocs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public MainLoc getMainLoc(int mainlocid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {mainlocid};
		MainLocMapper mapper=new MainLocMapper();
		
		try {
			MainLoc mainloc = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return mainloc;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public MainLoc getMainLocName(String mainlocname){
		String sql=READ_SQL+" where MAIN_LOC_NAME = ?";
		Object[] params= new Object[] {mainlocname};
		MainLocMapper mapper=new MainLocMapper();
		
		try {
			MainLoc mainloc = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return mainloc;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public MainLoc getMainLocName(String mainlocname, int mainlocid){
		String sql=READ_SQL+" where MAIN_LOC_NAME = ? AND ID != ?";
		Object[] params= new Object[] {mainlocname, mainlocid};
		MainLocMapper mapper=new MainLocMapper();
		
		try {
			MainLoc mainloc = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return mainloc;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public String updateMainLoc(int mainlocid, String mainlocname ){
		String sql=UPDATE_SQL+" set MAIN_LOC_NAME = ? where ID= ?";
		Object[] params=new Object[]{ mainlocname, mainlocid};
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
	
	public void deleteMainLoc(int mainlocid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {mainlocid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}

}
