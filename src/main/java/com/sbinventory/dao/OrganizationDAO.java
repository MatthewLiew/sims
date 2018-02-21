package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

	private static final String CREATE_SQL="INSERT INTO ORG (ORG_CODE, ORG_NAME) VALUES (?,?)";
	private static final String READ_SQL="SELECT * FROM ORG";
	private static final String UPDATE_SQL="UPDATE ORG";
	private static final String DELETE_SQL="DELETE FROM ORG";
	
	@Autowired
	public OrganizationDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String createOrganization(int orgcode, String orgname) {
		
		Object[] params=new Object[]{orgcode, orgname};
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
	
	public List<Organization> getAllOrganization(){
		
		String sql=READ_SQL;
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
            List<Organization> orgs =  this.getJdbcTemplate().query(sql, mapper);
            return orgs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Organization getOrganization(int orgid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {orgid};
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
            Organization org = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return org;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Organization getOrganizationCode(int orgcode){
		
		String sql=READ_SQL+" where ORG_CODE = ?";
		Object[] params=new Object[] {orgcode};
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
            Organization org = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return org;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Organization getOrganizationCode(int orgcode, int orgid){
		
		String sql=READ_SQL+" where ORG_CODE = ? AND ID!= ?";
		Object[] params=new Object[] {orgcode, orgid};
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
            Organization org = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return org;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	public Organization getOrganizationName(String orgname){
		String sql=READ_SQL+" where ORG_NAME = ?";
		Object[] params= new Object[] {orgname};
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
			Organization org = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return org;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Organization getOrganizationName(String orgname, int orgid){
		String sql=READ_SQL+" where ORG_NAME = ? AND ID != ?";
		Object[] params= new Object[] {orgname, orgid};
		OrganizationMapper mapper=new OrganizationMapper();
		
		try {
			Organization org = this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return org;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public String updateOrganization(int userid, int usercode, String username ){
		String sql=UPDATE_SQL+" set ORG_CODE = ?, ORG_NAME = ? where ID= ?";
		Object[] params=new Object[]{usercode, username, userid};
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
	
	public void deleteOrganization(int userid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {userid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
