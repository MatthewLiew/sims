package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.MainLocationMapper;
import com.sbinventory.mapper.SubLocationMapper;
import com.sbinventory.model.MainLocation;
import com.sbinventory.model.SubLocation;

@Repository
@Transactional
public class SubLocationDAO extends JdbcDaoSupport{

	private static final String VIEW_SQL = "select sl.*, ml.name as mainLocationName from subLocation sl left join mainLocation ml on sl.mainLocationId = ml.id";
	private static final String READ_SQL = "select * from subLocation";
	private static final String CREATE_SQL="insert into subLocation (name, description, mainLocationId) values (?,?,?)";
	private static final String UPDATE_SQL="update subLocation";
	private static final String DELETE_SQL="delete from subLocation";
	
	@Autowired
	public SubLocationDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(String name, String description, int mainLocationId) {
		
		Object[] params=new Object[]{name, description, mainLocationId};
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
	
	public String update(int id, String name, String description ){
		
		String sql=UPDATE_SQL+" set name = ?, description = ?, dateLastModified = default where id= ?";
		Object[] params=new Object[]{ name, description, id};
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
	
	public String delete(int id){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {id};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SubLocation> findAll(){
		
		String sql=VIEW_SQL + " where sl.name != ''" ;
		SubLocationMapper mapper=new SubLocationMapper();
		
		try {
            List<SubLocation> sublocs =  this.getJdbcTemplate().query(sql, mapper);
            return sublocs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public SubLocation findOne(int id){

		String sql=VIEW_SQL+" where sl.id = ?";
		Object[] params=new Object[] {id};
		SubLocationMapper mapper=new SubLocationMapper();
		
		try {
			SubLocation subloc = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return subloc;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public List<SubLocation> findAllByMainlocid(int mainLocationId){
		
		String sql=READ_SQL+" where id = ?"; ;
		Object[] params=new Object[] {mainLocationId};
		SubLocationMapper mapper=new SubLocationMapper();
		
		try {
            List<SubLocation> sublocs =  this.getJdbcTemplate().query(sql, params, mapper);
            return sublocs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
		
	public SubLocation getSubLocName(String name, int id){
		String sql=VIEW_SQL+" where sl.name = ? ";
		Object[] params= new Object[] {name};
		if(id!=0) {
			sql+="AND sl.id != ?";
			params= new Object[] {name, id};
		}
		SubLocationMapper mapper=new SubLocationMapper();
		
		try {
			SubLocation subloc = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return subloc;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public int getIdentCurrent() {
		String sql = "SELECT IDENT_CURRENT ('subLocation')";
		
		int num = this.getJdbcTemplate().queryForObject(sql, int.class);
		return num;
	
	}
	
}
