package com.sims.dao;

import java.util.List;

import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sims.mapper.AppRoleMapper;
import com.sims.mapper.DepartmentMapper;
import com.sims.mapper.TransferHistoryMapper;
import com.sims.model.AppRole;
import com.sims.model.Department;
 
@Repository
@Transactional
public class AppRoleDAO extends JdbcDaoSupport {
 
	private static final String READ_SQL="SELECT * FROM APP_ROLE";
    @Autowired
    public AppRoleDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
 
    public List<String> getRoleNames(int userId) {
    	
        String sql = "SELECT R.ROLE_NAME " //
                + " FROM APP_USER UR, APP_ROLE R " //
                + " WHERE UR.ROLE_ID = R.ID and UR.ID = ? ";
        Object[] params = new Object[] { userId };
        List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);
 
        return roles;
    }
    
//    public List<AppRole> getRoleName(int userId) {
//    	
//        String sql = "SELECT R.ROLE_NAME " //
//                + " FROM APP_USER UR, APP_ROLE R " //
//                + " WHERE UR.ROLE_ID = R.ID and UR.ID = ? ";
//        Object[] params = new Object[] { userId };
//        AppRoleMapper mapper=new AppRoleMapper();
////        List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);
//        List<AppRole> approles =  this.getJdbcTemplate().query(sql, params, mapper);
//        return approles;
//    }
    
    
    public AppRole findRoleNameByRoleid(int roleid) {
    	
        String sql = "SELECT * FROM APP_ROLE WHERE ID = ? ";
        Object[] params = new Object[] { roleid };
        AppRoleMapper mapper=new AppRoleMapper();
        AppRole approle = this.getJdbcTemplate().queryForObject(sql, params, mapper);
 
        return approle;
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