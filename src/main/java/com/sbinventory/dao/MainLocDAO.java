package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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
	
	public String create(String mainlocname) {
		
		Object[] params=new Object[]{mainlocname};
		String sql=CREATE_SQL;
		
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (DuplicateKeyException e) {
			return "Main Location "+mainlocname+" Exist. Main Location Creation Failed.";
			
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public String update(int mainlocid, String mainlocname ){
		
		String sql=UPDATE_SQL+" set MAIN_LOC_NAME = ? where ID= ?";
		Object[] params=new Object[]{ mainlocname, mainlocid};
		
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public String delete(int mainlocid){
		
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {mainlocid};
		
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public List<MainLoc> findAll(){
		
		String sql=READ_SQL + " ORDER BY MAIN_LOC_NAME";
		MainLocMapper mapper=new MainLocMapper();
		
		try {
            List<MainLoc> mainlocs =  this.getJdbcTemplate().query(sql, mapper);
            return mainlocs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public MainLoc findOne(int mainlocid){
		
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

	public MainLoc getMainLocName(String mainlocname, int mainlocid){
		
		String sql=READ_SQL+" where MAIN_LOC_NAME = ? ";
		Object[] params= new Object[] {mainlocname};
		if(mainlocid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {mainlocname, mainlocid};
		}
		MainLocMapper mapper=new MainLocMapper();
		
		try {
			MainLoc mainloc = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return mainloc;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
}
