package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.UserAccountMapper;
import com.sbinventory.model.UserAccount;
import com.sbinventory.utils.EncrytedPasswordUtils;

@Repository
@Transactional
public class UserAccountDAO extends JdbcDaoSupport {

	private static final String CREATE_SQL="INSERT INTO APP_USER ( USER_NAME, ENCRYTED_PASSWORD, "
			+ "ENABLED, ORG_ID, DEPT_ID, SUB_DEPT_ID) VALUES (?,?,?,?,?,?)";
	private static final String READ_SQL="SELECT * FROM APP_USER";
	private static final String UPDATE_SQL="UPDATE APP_USER";
	private static final String DELETE_SQL="DELETE FROM APP_USER";
	
	@Autowired
	public EncrytedPasswordUtils pwencoder;
	
	@Autowired
	public UserAccountDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}

	public List<UserAccount> findAll(){
		
		String sql=READ_SQL;
		UserAccountMapper mapper=new UserAccountMapper();
		
		try {
            List<UserAccount> useraccs =  this.getJdbcTemplate().query(sql, mapper);
            return useraccs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public UserAccount findOne(int userid){
		String sql=READ_SQL+" where ID = ?";
		Object[] params= new Object[] {userid};
		UserAccountMapper mapper=new UserAccountMapper();
		
		try {
			UserAccount useracc=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return useracc;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public UserAccount findOneByUsername(String username, int userid){
		String sql=READ_SQL+" where USER_NAME = ? ";
		Object[] params= new Object[] {username};
		if(userid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {username, userid};
		}
		UserAccountMapper mapper=new UserAccountMapper();
		
		try {
			UserAccount useracc=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return useracc;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public String create(String username, String enPW, Integer orgid, Integer deptid, Integer subdeptid) {
		int enabled=1;
		enPW= pwencoder.encrytePassword(enPW);
		
		Object[] params=new Object[] {username, enPW, enabled, orgid, deptid, subdeptid};
		String sql=CREATE_SQL;
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (DuplicateKeyException e) {
			throw new DuplicateKeyException("UserName Exist", e);
			
		}catch(EmptyResultDataAccessException e ) {
			return e.getMessage();
			
		}catch(DataAccessException  e) {
//			throw new DataAccessException("Something error", e);
			return e.getMessage();
		}
	}
	
	public String update(int userid, String username ){
		String sql=UPDATE_SQL+" set USER_NAME = ? where ID= ?";
		Object[] params=new Object[]{username, userid};
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
	
	public void delete(int userid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {userid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void changePsw(int userid, String password, String repassword){
		if(password.equals(repassword)) {
			password= pwencoder.encrytePassword(password);
			
			String sql=UPDATE_SQL+" set ENCRYTED_PASSWORD= ? where ID=?";
			Object[] params=new Object[]{password,userid};
			try {
				int rows=this.getJdbcTemplate().update(sql, params);
				System.out.println(rows + " row(s) updated.");
			} catch (EmptyResultDataAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
