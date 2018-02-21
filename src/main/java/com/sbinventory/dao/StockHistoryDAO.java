package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.StockHistoryMapper;
import com.sbinventory.model.StockHistory;

@Repository
@Transactional
public class StockHistoryDAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO STOCK_HISTORY (PRODUCT_ID, QUANTITY, HISTORY_DATE, HISTORY_TIME, STOCK_TYPE_ID, REASON_ID, REASON_DESC) VALUES (?,?,?,?,?,?,?)";
	private static final String READ_SQL="SELECT * FROM STOCK_HISTORY";
	private static final String UPDATE_SQL="UPDATE STOCK_HISTORY";
	private static final String DELETE_SQL="DELETE FROM STOCK_HISTORY";
	
	@Autowired
	public StockHistoryDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String createStockHistory(int productid, int quantity, String historydate, String historytime, int stocktypeid, int reasonid, String reasondesc ) {
		
		Object[] params=new Object[]{productid, quantity, historydate, historytime, stocktypeid, reasonid, reasondesc};
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
	
	public List<StockHistory> getAllStockHistory(){
		
		String sql=READ_SQL;
		StockHistoryMapper mapper=new StockHistoryMapper();
		
		try {
            List<StockHistory> stockhistorys =  this.getJdbcTemplate().query(sql, mapper);
            return stockhistorys;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public StockHistory getStockHistory(int stockhistoryid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {stockhistoryid};
		StockHistoryMapper mapper=new StockHistoryMapper();
		
		try {
            StockHistory stockhistory = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return stockhistory;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String updateStockHistory(int stockhistoryid, int productid, int quantity, String historydate, String historytime, int stocktypeid, int reasonid, String reasondesc ){
		String sql=UPDATE_SQL+" set PRODUCT_ID = ?, QUANTITY = ?, HISTORY_DATE = ?, HISTORY_TIME = ? , STOCK_TYPE_ID = ? , REASON_ID = ? , REASON_DESC = ?  where ID= ?";
		Object[] params=new Object[]{productid, quantity, historydate, historytime, stocktypeid, reasonid, reasondesc, stockhistoryid};
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
	
	public void deleteStockHistory(int stockhistoryid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {stockhistoryid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
