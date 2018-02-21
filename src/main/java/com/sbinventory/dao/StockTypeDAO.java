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
import com.sbinventory.mapper.StockTypeMapper;
import com.sbinventory.model.Organization;
import com.sbinventory.model.StockType;

@Repository
@Transactional
public class StockTypeDAO extends JdbcDaoSupport {

	private static final String CREATE_SQL="INSERT INTO STOCK_TYPE (STOCK_TYPE) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM STOCK_TYPE";
	private static final String UPDATE_SQL="UPDATE STOCK_TYPE";
	private static final String DELETE_SQL="DELETE FROM STOCK_TYPE";
	
	@Autowired
	public StockTypeDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String createStockType(String stocktype) {
		
		Object[] params=new Object[]{stocktype};
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
	
	public List<StockType> getAllStockType(){
		
		String sql=READ_SQL;
		StockTypeMapper mapper=new StockTypeMapper();
		
		try {
            List<StockType> stocktypes =  this.getJdbcTemplate().query(sql, mapper);
            return stocktypes;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public StockType getStockType(int stocktypeid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {stocktypeid};
		StockTypeMapper mapper=new StockTypeMapper();
		
		try {
			StockType stocktype = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return stocktype;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String updateStockType(int stocktypeid, String stocktype ){
		String sql=UPDATE_SQL+" set STOCK_TYPE = ?, where ID= ?";
		Object[] params=new Object[]{stocktype, stocktypeid};
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
	
	public void deleteStockType(int stocktypeid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {stocktypeid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
