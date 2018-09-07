package com.sims.dao;

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

import com.sims.mapper.MainLocationMapper;
import com.sims.model.MainLocation;

@Repository
@Transactional
public class MainLocationDAO extends JdbcDaoSupport{
	
	private static final String READ_SQL="select * from mainLocation";
	private static final String CREATE_SQL="insert into mainLocation (name) values (?)";
	private static final String UPDATE_SQL="update mainLocation";
	private static final String DELETE_SQL="delete from mainLocation";
	
	@Autowired
	public MainLocationDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(String name) {
		
		Object[] params=new Object[]{name};
		String sql=CREATE_SQL;
		
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (DuplicateKeyException e) {
			return "Main Location "+name+" Exist. Main Location Creation Failed.";
			
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public String update(int id, String name ){
		
		String sql=UPDATE_SQL+" set name = ?, dateLastModified = default where id= ?";
		Object[] params=new Object[]{ name, id};
		
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
	
	public String delete(int id){
		
		String sql=DELETE_SQL+" where id= ?";
		Object[] params= new Object[] {id};
		
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
	
	public List<MainLocation> findAll(){
		
		String sql=READ_SQL;
		MainLocationMapper mapper=new MainLocationMapper();
		
		try {
            List<MainLocation> mainlocations =  this.getJdbcTemplate().query(sql, mapper);
            return mainlocations;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public MainLocation findOne(int id){
		
		String sql=READ_SQL+" where id = ?";
		Object[] params=new Object[] {id};
		MainLocationMapper mapper=new MainLocationMapper();
		
		try {
			MainLocation mainloc = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return mainloc;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public MainLocation getMainLocName(String name, int id){
		
		String sql=READ_SQL+" where name = ? ";
		Object[] params= new Object[] {name};
		if(id!=0) {
			sql+="AND id != ?";
			params= new Object[] {name, id};
		}
		MainLocationMapper mapper=new MainLocationMapper();
		
		try {
			MainLocation mainloc = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return mainloc;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public int getIdentCurrent() {
		String sql = "SELECT IDENT_CURRENT ('mainLocation')";
		
		int num = this.getJdbcTemplate().queryForObject(sql, int.class);
		return num;
	
	}
	
}
