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
import com.sbinventory.mapper.DeptMapper;
import com.sbinventory.model.Organization;
import com.sbinventory.model.Dept;

@Repository
@Transactional
public class DeptDAO extends JdbcDaoSupport{

private static final String READ_SQL="SELECT * FROM DEPT";
private static final String CREATE_SQL="INSERT INTO DEPT (DEPT_CODE, DEPT_NAME, ORG_ID) VALUES (?,?,?)";
private static final String UPDATE_SQL="UPDATE DEPT";
private static final String DELETE_SQL="DELETE FROM DEPT";
	
	@Autowired
	public DeptDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public List<Dept> getAllDept(){
		
		String sql=READ_SQL;
		DeptMapper mapper=new DeptMapper();
		
		try {
            List<Dept> depts =  this.getJdbcTemplate().query(sql, mapper);
            return depts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public List<Dept> getAllDept(int orgid){
		
		String sql=READ_SQL+ " where ORG_ID = ?";
		Object[] params =new Object[] {orgid};
		DeptMapper mapper=new DeptMapper();
		
		try {
            List<Dept> depts =  this.getJdbcTemplate().query(sql, params, mapper);
            return depts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Dept getDept(int deptid){
		
		String sql=READ_SQL+ " where ID = ?";
		Object[] params =new Object[] {deptid};
		DeptMapper mapper=new DeptMapper();
		
		try {
            Dept dept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            System.out.println(dept.getDeptname());
            return dept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Dept getDeptCode(int deptcode){
		
		String sql=READ_SQL+ " where DEPT_CODE = ?";
		Object[] params =new Object[] {deptcode};
		DeptMapper mapper=new DeptMapper();
		
		try {
            Dept dept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            System.out.println(dept.getDeptname());
            return dept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Dept getDeptCode(int deptcode, int deptid){
		
		String sql=READ_SQL+ " where DEPT_CODE = ? AND ID != ?";
		Object[] params =new Object[] {deptcode, deptid};
		DeptMapper mapper=new DeptMapper();
		
		try {
            Dept dept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            System.out.println(dept.getDeptname());
            return dept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Dept getDeptName(String deptname){
		
		String sql=READ_SQL+ " where DEPT_NAME = ?";
		Object[] params =new Object[] {deptname};
		DeptMapper mapper=new DeptMapper();
		
		try {
            Dept dept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            System.out.println(dept.getDeptname());
            return dept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Dept getDeptName(String deptname, int deptid){
		
		String sql=READ_SQL+ " where DEPT_NAME = ? AND ID != ?";
		Object[] params =new Object[] {deptname, deptid};
		DeptMapper mapper=new DeptMapper();
		
		try {
            Dept dept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            System.out.println(dept.getDeptname());
            return dept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String createDepartment(int orgid, int deptcode, String deptname) {
		
		Object[] params=new Object[]{deptcode, deptname, orgid};
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
	
	public String updateDepartment(int deptid, int deptcode, String deptname ){
		
		String sql=UPDATE_SQL+" set DEPT_CODE = ?, DEPT_NAME = ? where ID= ?";
		Object[] params=new Object[]{deptcode, deptname, deptid};
		
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (DataAccessException  e) {
			return e.getMessage();
		}
	}
	
	public void deleteDepartment(int deptid){
		
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {deptid};
		
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
