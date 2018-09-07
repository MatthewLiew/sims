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

import com.sims.mapper.StockHistoryMapper;
import com.sims.model.StockHistory;

@Repository
@Transactional
public class StockHistoryDAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO STOCK_HISTORY (PRODUCT_ID, ORG_ID, DEPT_ID, SUB_DEPT_ID, MAIN_LOC_ID, SUB_LOC_ID, QUANTITY, HISTORY_DATE, HISTORY_TIME, STOCK_TYPE_ID, REASON_ID, REMARK, LOG_DATETIME, LOG_USER, APPROVAL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String READ_SQL="SELECT * FROM STOCK_HISTORY";
	private static final String UPDATE_SQL="UPDATE STOCK_HISTORY";
	private static final String DELETE_SQL="DELETE FROM STOCK_HISTORY";
	
	@Autowired
	public StockHistoryDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(int productid, int orgid, int deptid, int subdeptid, int mainlocid, int sublocid, int quantity, 
			String historydate, String historytime, int stocktypeid, int reasonid, String remark, String logdatetime, String loguser, 
			String approval) {
		
		Object[] params=new Object[]{productid, orgid, deptid, subdeptid, mainlocid, sublocid, quantity, historydate, historytime, stocktypeid, reasonid, remark, logdatetime, loguser, approval};
		String sql=CREATE_SQL;
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
	
	public String update(int stockhistoryid, int productid, int quantity, String historydate, String historytime, int stocktypeid, 
			int reasonid, String remark, int mainlocid, int sublocid, int orgid, int deptid, int subdeptid ){
		String sql=UPDATE_SQL+" set PRODUCT_ID = ?, QUANTITY = ?, HISTORY_DATE = ?, HISTORY_TIME = ?, STOCK_TYPE_ID = ?, REASON_ID = ?, REMARK = ?, MAIN_LOC_ID = ?, SUB_LOC_ID = ?, "
				+ "ORG_ID = ?, DEPT_ID = ?, SUB_DEPT_ID = ? where ID= ?";
		Object[] params=new Object[]{productid, quantity, historydate, historytime, stocktypeid, reasonid, remark, mainlocid, sublocid, 
				orgid, deptid, subdeptid, stockhistoryid};
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
	
	public String delete(int stockhistoryid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {stockhistoryid};
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
	
	public String approval(int stockhistoryid, String approve ){
		String sql=UPDATE_SQL+" set APPROVAL = ? where ID= ?";
		Object[] params=new Object[]{approve, stockhistoryid};
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
	
	public List<StockHistory> findAll(){
		
		String sql=READ_SQL;
		StockHistoryMapper mapper=new StockHistoryMapper();
		
		try {
            List<StockHistory> stockhistorys =  this.getJdbcTemplate().query(sql, mapper);
            return stockhistorys;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<StockHistory> findAllByFilter(String startdate, String enddate, int stocktypeid, int reasonid){
		Object[] params = null;
		String sql=READ_SQL+ " where HISTORY_DATE >= ? AND HISTORY_DATE <= ?"; 
		params=new Object[] {startdate, enddate};
		
		if((stocktypeid!=0)) {
			sql+= " AND STOCK_TYPE_ID = ?";
			params=new Object[] {startdate, enddate, stocktypeid};
			
			if ((reasonid!=0)){
				sql+= " AND REASON_ID = ? ";
				params=new Object[] {startdate, enddate, stocktypeid, reasonid };
			}
		}
//		
		StockHistoryMapper mapper=new StockHistoryMapper();
		
		try {
            List<StockHistory> stockhistory = this.getJdbcTemplate().query(sql, params, mapper);
            return stockhistory;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public StockHistory findOne(int stockhistoryid){
		
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
	
	public List<StockHistory> getStockQuantity(int productid){
		
		String sql=READ_SQL+" where PRODUCT_ID = ?";
		Object[] params=new Object[] {productid};
		StockHistoryMapper mapper=new StockHistoryMapper();
		
		try {
            List<StockHistory> stockquantity = this.getJdbcTemplate().query(sql, params, mapper);
            return stockquantity;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	
}
