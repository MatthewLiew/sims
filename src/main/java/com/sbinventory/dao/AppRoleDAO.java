package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.AppRoleMapper;
import com.sbinventory.mapper.DeptMapper;
import com.sbinventory.model.AppRole;
import com.sbinventory.model.Dept;
 
@Repository
@Transactional
public class AppRoleDAO extends JdbcDaoSupport {
 
	private static final String READ_SQL="SELECT * FROM APP_ROLE";
    @Autowired
    public AppRoleDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
 
    public List<String> getRoleNames(int userId) {
        String sql = "Select r.Role_Name " //
                + " from User_Role ur, App_Role r " //
                + " where ur.Role_Id = r.Role_Id and ur.User_Id = ? ";
 
        Object[] params = new Object[] { userId };
 
        List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);
 
        return roles;
    }
    
    public List<AppRole> getAllRoleNames() {
    	
    	String sql=READ_SQL;
		AppRoleMapper mapper=new AppRoleMapper();
		
		try {
            List<AppRole> approles =  this.getJdbcTemplate().query(sql, mapper);
            return approles;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
     
}