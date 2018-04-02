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

import com.sbinventory.mapper.DeptMapper;
import com.sbinventory.mapper.HardwareMapper;
import com.sbinventory.model.Dept;
import com.sbinventory.model.Hardware;

@Repository
@Transactional
public class HardwareDAO extends JdbcDaoSupport{
	
	private static final String READ_SQL="SELECT * FROM HARDWARE";
	private static final String CREATE_SQL="INSERT INTO HARDWARE (HARDWARE_CODE, HARDWARE_TYPE) VALUES (?,?)";
	private static final String UPDATE_SQL="UPDATE HARDWARE";
	private static final String DELETE_SQL="DELETE FROM HARDWARE";
	
	@Autowired
	public HardwareDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(int hardwarecode, String hardwaretype) {
		
		Object[] params=new Object[]{ hardwarecode, hardwaretype };
		String sql=CREATE_SQL;
		
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (DuplicateKeyException e) {
			return "Hardware "+hardwaretype+" Exist. Hardware Creation Failed.";
			
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public String update(int hardwareid, int hardwarecode, String hardwaretype ){
		
		String sql=UPDATE_SQL+" set HARDWARE_CODE = ?, HARDWARE_TYPE = ? where ID= ?";
		Object[] params=new Object[]{ hardwarecode, hardwaretype, hardwareid };
		
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
	
	public String delete(int hardwareid){
		
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {hardwareid};
		
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
	
	public List<Hardware> findAll(){
		
		String sql=READ_SQL;
		HardwareMapper mapper=new HardwareMapper();
		
		try {
            List<Hardware> hardwares =  this.getJdbcTemplate().query(sql, mapper);
            return hardwares;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Hardware findOne(int hardwareid){
		
		String sql=READ_SQL+ " where ID = ?";
		Object[] params =new Object[] {hardwareid};
		HardwareMapper mapper=new HardwareMapper();
		
		try {
			Hardware hardware =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return hardware;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Hardware getHardwareCode(int hardwarecode, int hardwareid){
		
		String sql=READ_SQL+ " where HARDWARE_CODE = ? ";
		Object[] params =new Object[] {hardwarecode};
		if(hardwareid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {hardwarecode, hardwareid};
		}
		HardwareMapper mapper=new HardwareMapper();
		
		try {
			Hardware hardware =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return hardware;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Hardware getHardwareType(String hardwaretype, int hardwareid){
		
		String sql=READ_SQL+ " where HARDWARE_TYPE = ? ";
		Object[] params =new Object[] {hardwaretype};
		if(hardwareid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {hardwaretype, hardwareid};
		}
		HardwareMapper mapper=new HardwareMapper();
		
		try {
			Hardware hardware =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return hardware;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
}
