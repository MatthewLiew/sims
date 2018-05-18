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

import com.sbinventory.mapper.SubDepartmentMapper;
import com.sbinventory.model.SubDepartment;

@Repository
@Transactional
public class SubDepartmentDAO extends JdbcDaoSupport{

	private static final String VIEW_SQL="select sd.*, d.name as departmentName, o.id as organizationId, o.name as organizationName " + 
											"from subDepartment sd " + 
											"left join department d on sd.departmentId = d.id " + 
											"left join organization o on d.organizationId = o.id";
	private static final String READ_SQL="SELECT * FROM SUB_DEPT";
	private static final String CREATE_SQL="insert into subDepartment (name, departmentId) VALUES (?,?)";
	private static final String UPDATE_SQL="update subDepartment";
	private static final String DELETE_SQL="delete from subDepartment";
	
	@Autowired
	public SubDepartmentDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(int departmentId, String name) {
		
		Object[] params=new Object[]{ name, departmentId};
		String sql=CREATE_SQL;
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (DuplicateKeyException e) {
			return "Sub Department " + name + " Exist. Sub Department Creation Failed.";
			
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
		
		String sql=DELETE_SQL+" where id= ?";
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
	
	public List<SubDepartment> findAll(){
		
		String sql=VIEW_SQL;
		SubDepartmentMapper mapper=new SubDepartmentMapper();
		
		try {
            List<SubDepartment> subdepts =  this.getJdbcTemplate().query(sql, mapper);
            return subdepts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public SubDepartment findOne(int id){
		
		String sql=VIEW_SQL+ " where sd.id = ?";
		Object[] params =new Object[] {id};
		SubDepartmentMapper mapper=new SubDepartmentMapper();
		
		try {
            SubDepartment subdept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return subdept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public List<SubDepartment> findAllByDeptid(int departmentId){
		
		String sql=VIEW_SQL+ " where departmentId = ?";
		Object[] params =new Object[] {departmentId};
		SubDepartmentMapper mapper=new SubDepartmentMapper();
		
		try {
            List<SubDepartment> subdepts =  this.getJdbcTemplate().query(sql, params, mapper);
            return subdepts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<SubDepartment> findAllByDeptid(int departmentId, int id){
		
		String sql=VIEW_SQL+ " where departmentId = ? and id != ?";
		Object[] params =new Object[] {departmentId, id};
		SubDepartmentMapper mapper=new SubDepartmentMapper();
		
		try {
            List<SubDepartment> subdepts =  this.getJdbcTemplate().query(sql, params, mapper);
            return subdepts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public SubDepartment getSubDeptName(String name, int id){
		
		String sql=VIEW_SQL+ " where sd.name = ? ";
		Object[] params =new Object[] {name};
		if(id !=0 ) {
			sql+="and sd.id != ?";
			params= new Object[] {name, id};
		}
		SubDepartmentMapper mapper=new SubDepartmentMapper();
		
		try {
            SubDepartment subdept =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return subdept;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public int getIdentCurrent() {
		String sql = "SELECT IDENT_CURRENT ('subDepartment')";
		
		int num = this.getJdbcTemplate().queryForObject(sql, int.class);
		return num;
	
	}
	
}
