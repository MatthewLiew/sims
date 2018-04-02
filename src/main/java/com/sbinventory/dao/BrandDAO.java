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

import com.sbinventory.mapper.BrandMapper;
import com.sbinventory.mapper.HardwareMapper;
import com.sbinventory.model.Brand;
import com.sbinventory.model.Hardware;

@Repository
@Transactional
public class BrandDAO extends JdbcDaoSupport{
	
	private static final String READ_SQL="SELECT * FROM BRAND";
	private static final String CREATE_SQL="INSERT INTO BRAND (BRAND_CODE, BRAND_NAME) VALUES (?,?)";
	private static final String UPDATE_SQL="UPDATE BRAND";
	private static final String DELETE_SQL="DELETE FROM BRAND";
	
	@Autowired
	public BrandDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(int brandcode, String brandname) {
		
		Object[] params=new Object[]{brandcode, brandname};
		String sql=CREATE_SQL;
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (DuplicateKeyException e) {
			return "Brand "+brandname+" Exist. Brand Creation Failed.";
			
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public String update(int brandid, int brandcode, String brandname ){
		
		String sql=UPDATE_SQL+" set BRAND_CODE = ?, BRAND_NAME = ? where ID= ?";
		Object[] params=new Object[]{brandcode, brandname, brandid};
		
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
	
	public String delete(int brandid){
		
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {brandid};
		
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
	
	public List<Brand> findAll(){
		
		String sql=READ_SQL;
		BrandMapper mapper=new BrandMapper();
		
		try {
            List<Brand> brands =  this.getJdbcTemplate().query(sql, mapper);
            return brands;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Brand findOne(int brandid){
		
		String sql=READ_SQL+ " where ID = ?";
		Object[] params =new Object[] {brandid};
		BrandMapper mapper=new BrandMapper();
		
		try {
			Brand brand =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return brand;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Brand getBrandCode(int brandcode, int brandid){
		
		String sql=READ_SQL+ " where BRAND_CODE = ? ";
		Object[] params =new Object[] {brandcode};
		if(brandid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {brandcode, brandid};
		}
		BrandMapper mapper=new BrandMapper();
		
		try {
			Brand brand =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return brand;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Brand getBrandName(String brandname, int brandid){
		
		String sql=READ_SQL+ " where BRAND_NAME = ? ";
		Object[] params =new Object[] {brandname};
		if(brandid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {brandname, brandid};
		}
		BrandMapper mapper=new BrandMapper();
		
		try {
			Brand brand =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return brand;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
}
