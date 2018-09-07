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

import com.sims.mapper.DepartmentMapper;
import com.sims.mapper.OrganizationMapper;
import com.sims.model.Department;
import com.sims.model.Organization;

@Repository
@Transactional
public class DepartmentDAO extends JdbcDaoSupport{

private static final String VIEW_SQL = "select d.*, o.name as organizationName from department d left join organization o on d.organizationId = o.id";
private static final String READ_SQL="select * from department";
private static final String CREATE_SQL="insert into department ( name, organizationId) values (?,?)";
private static final String UPDATE_SQL="update department";
private static final String DELETE_SQL="delete from department";
	
	@Autowired
	public DepartmentDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(int organizationId, String name) {
		
		Object[] params=new Object[]{ name, organizationId};
		String sql=CREATE_SQL;
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (DuplicateKeyException e) {
			return "Department " + name + " Exist. Department Creation Failed.";
			
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
			
		}  catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public List<Department> findAll(){
		
		String sql=VIEW_SQL;
		DepartmentMapper mapper=new DepartmentMapper();
		
		try {
            List<Department> depts =  this.getJdbcTemplate().query(sql, mapper);
            return depts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public Department findOne(int id){
		
		String sql=VIEW_SQL+ " where d.id = ?";
		Object[] params =new Object[] {id};
		DepartmentMapper mapper=new DepartmentMapper();
		
		try {
            Department dept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            System.out.println(dept.getName());
            return dept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public List<Department> findAllByOrgid(int organizationId){
		
		String sql=VIEW_SQL+ " where organizationId = ?";
		Object[] params =new Object[] {organizationId};
		DepartmentMapper mapper=new DepartmentMapper();
		
		try {
            List<Department> depts =  this.getJdbcTemplate().query(sql, params, mapper);
            return depts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<Department> findAllByOrgid(int organizationId, int id){
		
		String sql=VIEW_SQL+ " where organizaitonId = ? and id != ?";
		Object[] params =new Object[] {organizationId, id};
		DepartmentMapper mapper=new DepartmentMapper();
		
		try {
            List<Department> depts =  this.getJdbcTemplate().query(sql, params, mapper);
            return depts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public Department getDeptName(String name, int id){
		
		String sql=VIEW_SQL+ " where d.name = ? ";
		Object[] params =new Object[] {name};
		if(id != 0) {
			sql+="AND d.id != ?";
			params= new Object[] {name, id};
		}
		DepartmentMapper mapper=new DepartmentMapper();
		
		try {
            Department dept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            System.out.println(dept.getName());
            return dept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public int getIdentCurrent() {
		String sql = "SELECT IDENT_CURRENT ('department')";
		
		int num = this.getJdbcTemplate().queryForObject(sql, int.class);
		return num;
	
	}
	
}
