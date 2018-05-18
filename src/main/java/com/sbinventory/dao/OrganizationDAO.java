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

import com.sbinventory.mapper.OrganizationMapper;
import com.sbinventory.mapper.UserAccountMapper;
import com.sbinventory.model.Organization;
import com.sbinventory.model.UserAccount;

@Repository
@Transactional
public class OrganizationDAO extends JdbcDaoSupport {

	private static final String CREATE_SQL="insert into organization (name) values (?)";
	private static final String READ_SQL="select * from organization";
	private static final String UPDATE_SQL="update organization";
	private static final String DELETE_SQL="delete from organization";
	
	@Autowired
	public OrganizationDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create (String name) {
		
		Object[] params=new Object[]{name};
		String sql=CREATE_SQL;
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
			
		} catch (DuplicateKeyException e) {
			return "Organization "+ name + " Exist. Organization Creation Failed.";
			
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public String update(int id, String name ){
		String sql=UPDATE_SQL+" set name = ?, dateLastModified = default where id = ?";
		Object[] params=new Object[]{name, id};
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
		String sql=DELETE_SQL+" where ID= ?";
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
	
	public List<Organization> findAll(){
		
		String sql=READ_SQL;
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
            List<Organization> orgs =  this.getJdbcTemplate().query(sql, mapper);
            return orgs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<Organization> findAll(int id){
		
		String sql=READ_SQL+" where id ! = ?";
		Object[] params=new Object[] {id};
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
            List<Organization> orgs =  this.getJdbcTemplate().query(sql, params, mapper);
            return orgs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Organization findOne(int id){
		
		String sql=READ_SQL+" where id = ?";
		Object[] params=new Object[] {id};
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
            Organization org = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return org;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Organization getOrganizationName(String name, int id){
		String sql=READ_SQL+" where name = ? ";
		Object[] params= new Object[] {name};
		if(id!=0) {
			sql+="AND id != ?";
			params= new Object[] {name, id};
		}
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
			Organization org = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return org;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public int getIdentCurrent() {
		String sql = "SELECT IDENT_CURRENT ('organization')";
		
		int num = this.getJdbcTemplate().queryForObject(sql, int.class);
		return num;
	
	}
	
}
