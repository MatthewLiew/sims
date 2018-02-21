package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.ProductMapper;
import com.sbinventory.mapper.UserAccountMapper;
import com.sbinventory.model.Product;
import com.sbinventory.model.UserAccount;
import com.sbinventory.utils.EncrytedPasswordUtils;

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
	
	public String createProduct(int productcode, String productname, int hardwareid, 
			int brandid, int partnoud, int lbvalue) {
		
		Object[] params=new Object[]{productcode, productname, hardwareid, brandid, partnoud, lbvalue};
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
	public List<Product> getAllProduct(){
		
		String sql=READ_SQL;
		ProductMapper mapper=new ProductMapper();
		
		try {
            List<Product> products =  this.getJdbcTemplate().query(sql, mapper);
            return products;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Product getProduct(int productid){
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
	
	public Product getProductCode(int productcode){
		String sql=READ_SQL+" where PRODUCT_CODE = ?";
		Object[] params= new Object[] {productcode};
		ProductMapper mapper=new ProductMapper();
		
		try {
			Product product=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Product getProductCode(int productcode, int productid){
		String sql=READ_SQL+" where PRODUCT_CODE = ? AND ID != ?";
		Object[] params= new Object[] {productcode, productid};
		ProductMapper mapper=new ProductMapper();
		
		try {
			Product product=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Product getProductName(String productname){
		String sql=READ_SQL+" where PRODUCT_NAME = ?";
		Object[] params= new Object[] {productname};
		ProductMapper mapper=new ProductMapper();
		
		try {
			Product product=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Product getProductName(String productname, int productid){
		String sql=READ_SQL+" where PRODUCT_NAME = ? AND ID != ?";
		Object[] params= new Object[] {productname, productid};
		ProductMapper mapper=new ProductMapper();
		
		try {
			Product product=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Product getLBValue(int lbvalue){
		String sql=READ_SQL+" where LB_VALUE = ?";
		Object[] params= new Object[] {lbvalue};
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
		Object[] params= new Object[] {lbvalue, productid};
		ProductMapper mapper=new ProductMapper();
		
		try {
			Product product=this.getJdbcTemplate().queryForObject(sql,params,mapper);
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public String updateProduct(int productid, int productcode, String productname ){
		String sql=UPDATE_SQL+" set PRODUCT_CODE = ?, PRODUCT_NAME = ? where ID= ?";
		Object[] params=new Object[]{productcode, productname, productid};
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
	
	public void deleteProduct(int productid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {productid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
