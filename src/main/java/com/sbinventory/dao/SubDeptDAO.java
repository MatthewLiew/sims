package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.SubDeptMapper;
import com.sbinventory.model.SubDept;

@Repository
@Transactional
public class SubDeptDAO extends JdbcDaoSupport{

	private static final String READ_SQL="SELECT * FROM SUB_DEPT";
	private static final String CREATE_SQL="INSERT INTO SUB_DEPT (SUB_DEPT_CODE, SUB_DEPT_NAME, DEPT_ID) VALUES (?,?,?)";
	private static final String UPDATE_SQL="UPDATE SUB_DEPT";
	private static final String DELETE_SQL="DELETE FROM SUB_DEPT";
	
	@Autowired
	public SubDeptDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public List<SubDept> getAllSubDept(){
		
		String sql=READ_SQL;
		SubDeptMapper mapper=new SubDeptMapper();
		
		try {
            List<SubDept> subdepts =  this.getJdbcTemplate().query(sql, mapper);
            return subdepts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<SubDept> getAllSubDept(int deptid){
		
		String sql=READ_SQL+ " where DEPT_ID = ?";
		Object[] params =new Object[] {deptid};
		SubDeptMapper mapper=new SubDeptMapper();
		
		try {
            List<SubDept> subdepts =  this.getJdbcTemplate().query(sql, params, mapper);
            return subdepts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public SubDept getSubDept(int subdeptid){
		
		String sql=READ_SQL+ " where ID = ?";
		Object[] params =new Object[] {subdeptid};
		SubDeptMapper mapper=new SubDeptMapper();
		
		try {
            SubDept subdept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return subdept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public SubDept getSubDeptCode(int subdeptcode){
		
		String sql=READ_SQL+ " where SUB_DEPT_CODE = ?";
		Object[] params =new Object[] {subdeptcode};
		SubDeptMapper mapper=new SubDeptMapper();
		
		try {
            SubDept subdept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return subdept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public SubDept getSubDeptCode(int subdeptcode, int subdeptid){
		
		String sql=READ_SQL+ " where SUB_DEPT_CODE = ? AND ID != ?";
		Object[] params =new Object[] {subdeptcode, subdeptid};
		SubDeptMapper mapper=new SubDeptMapper();
		
		try {
            SubDept subdept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return subdept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public SubDept getSubDeptName(String subdeptname){
		
		String sql=READ_SQL+ " where SUB_DEPT_NAME = ?";
		Object[] params =new Object[] {subdeptname};
		SubDeptMapper mapper=new SubDeptMapper();
		
		try {
            SubDept subdept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return subdept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public SubDept getSubDeptName(String subdeptname, int subdeptid){
		
		String sql=READ_SQL+ " where SUB_DEPT_NAME = ? AND ID != ?";
		Object[] params =new Object[] {subdeptname, subdeptid};
		SubDeptMapper mapper=new SubDeptMapper();
		
		try {
            SubDept subdept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return subdept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String createSubDepartment(int deptid, int subdeptcode, String subdeptname) {
		
		Object[] params=new Object[]{subdeptcode, subdeptname, deptid};
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
	
	public String updateSubDepartment(int subdeptid, int subdeptcode, String subdeptname ){
		String sql=UPDATE_SQL+" set SUB_DEPT_CODE = ?, SUB_DEPT_NAME = ? where ID= ?";
		Object[] params=new Object[]{subdeptcode, subdeptname, subdeptid};
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
	
	public void deleteDepartment(int subdeptid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {subdeptid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
