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

import com.sims.mapper.ProductMapper;
import com.sims.mapper.UserAccountMapper;
import com.sims.model.Product;
import com.sims.model.UserAccount;
import com.sims.utils.EncrytedPasswordUtils;

@Repository
@Transactional
public class ProductDAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO PRODUCT (PRODUCT_CODE, PRODUCT_NAME, HARDWARE_ID, "
			+ "BRAND_ID, PART_NO_ID, LB_VALUE) VALUES (?,?,?,?,?,?)";
	private static final String READ_SQL="SELECT * FROM PRODUCT";
	private static final String UPDATE_SQL="UPDATE PRODUCT";
	private static final String DELETE_SQL="DELETE FROM PRODUCT";
	
	@Autowired
	public ProductDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(int productcode, String productname, int hardwareid, 
			int brandid, int partnoud, int lbvalue) {
		
		Object[] params=new Object[]{productcode, productname, hardwareid, brandid, partnoud, lbvalue};
		String sql=CREATE_SQL;
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		} catch (DuplicateKeyException e) {
			return "Product "+productname+" Exist. Product Creation Failed.";
			
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public String update(int productid, int productcode, String productname, int brandid, int hardwareid, int lbvalue ){
		String sql=UPDATE_SQL+" set PRODUCT_CODE = ?, PRODUCT_NAME = ?, BRAND_ID = ?, HARDWARE_ID = ?, LB_VALUE = ? where ID= ?";
		Object[] params=new Object[]{productcode, productname, brandid, hardwareid, lbvalue, productid};
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
	
	public String updateQuantity(int productid, int quantity ){
		String sql=UPDATE_SQL+" set QUANTITY = ? where ID= ?";
		Object[] params=new Object[] { quantity, productid};
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
	
	public String delete(int productid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {productid};
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
	
	public List<Product> findAll(){
		
		String sql=READ_SQL;
		ProductMapper mapper=new ProductMapper();
		
		try {
            List<Product> products =  this.getJdbcTemplate().query(sql, mapper);
            return products;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Product findOne(int productid){
		String sql=READ_SQL+" where ID = ?";
		Object[] params= new Object[] {productid};
		ProductMapper mapper=new ProductMapper();
		
		try {
			Product product=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Product getProductCode(int productcode, int productid){
		String sql=READ_SQL+" where PRODUCT_CODE = ? ";
		Object[] params= new Object[] {productcode};
		if(productid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {productcode, productid};
		}
		ProductMapper mapper=new ProductMapper();
		
		try {
			Product product=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Product getProductName(String productname, int productid){
		String sql=READ_SQL+" where PRODUCT_NAME = ? ";
		Object[] params= new Object[] {productname};
		if(productid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {productname, productid};
		}
		ProductMapper mapper=new ProductMapper();
		
		try {
			Product product=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Product getLBValue(int lbvalue, int productid){
		String sql=READ_SQL+" where LB_VALUE = ? AND ID !=? ";
		Object[] params= new Object[] {lbvalue};
		if(productid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {lbvalue, productid};
		}
		ProductMapper mapper=new ProductMapper();
		
		try {
			Product product=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
		
}
