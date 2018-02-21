package com.sbinventory.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.UserRoleMapper;
import com.sbinventory.model.UserRole;

@Repository
@Transactional
public class UserRoleDAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (?,?)";
	private static final String UPDATE_SQL="UPDATE USER_ROLE";
	private static final String DELETE_SQL="DELETE FROM USER_ROLE";
	private static final String READ_SQL="SELECT * FROM USER_ROLE";
	
	@Autowired
	public UserRoleDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public void createUserRole(int userid, int roleid) {

		Object[] params=new Object[]{userid, roleid};
		String sql=CREATE_SQL;
	
		int rows=this.getJdbcTemplate().update(sql, params);
		System.out.println(rows + " row(s) updated.");
		
	}
	
	public void updateUserRole(int new_userid, int userid){
		String sql=UPDATE_SQL+" set USER_ID = ? where USER_ID= ?";
		Object[] params= new Object[] {new_userid, userid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUserRole(int userid){
		String sql=DELETE_SQL+" where USER_ID= ?";
		Object[] params= new Object[] {userid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public UserRole getUserRole(int userid){
		String sql=READ_SQL+" where USER_ID= ?";
		Object[] params= new Object[] {userid};
		UserRoleMapper mapper=new UserRoleMapper();
		try {
			UserRole userrole=this.getJdbcTemplate().queryForObject(sql, params,mapper);
			return userrole;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
}
