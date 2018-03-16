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
import com.sbinventory.mapper.TransferHistoryMapper;
import com.sbinventory.model.StockHistory;
import com.sbinventory.model.TransferHistory;

@Repository
@Transactional
public class TransferHistoryDAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (LOG_USER, LOG_DATETIME, PRODUCT_ID, QUANTITY, ORI_MAIN_LOC, ORI_SUB_LOC, DES_MAIN_LOC, DES_SUB_LOC, APPROVAL) VALUES (?,?,?,?,?,?,?,?,?)";
//	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (LOG_USER ) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM TRANSFER_HISTORY";
	private static final String UPDATE_SQL="UPDATE TRANSFER_HISTORY";
	private static final String DELETE_SQL="DELETE FROM TRANSFER_HISTORY";
	
	@Autowired
	public TransferHistoryDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String createTransferHistory(String loguser, 
										String logdatetime, 
									 int productid, 
									 int quantity, 
									 int orimainlocid, 
									 int orisublocid,
									 int desmainlocid,
									 int dessublocid,
									 String approval) {

		Object[] params=new Object[]{loguser, logdatetime, productid, quantity, orimainlocid, orisublocid, desmainlocid, dessublocid, approval};
//		Object[] params=new Object[]{"null",logdatetime, productid, quantity, orimainlocid, orisublocid, desmainlocid, dessublocid};
		String sql=CREATE_SQL;
		
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e ) {
			return e.getMessage();

		}catch(DataAccessException  e) {
			//throw new DataAccessException("Something error", e);
			return e.getMessage();
		}
	}
	
	public List<TransferHistory> getAllTransferHistory(){
		
		String sql=READ_SQL;
		TransferHistoryMapper mapper=new TransferHistoryMapper();
		
		try {
            List<TransferHistory> transferhistories =  this.getJdbcTemplate().query(sql, mapper);
            return transferhistories;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public TransferHistory getTransferHistory(int transferhistoryid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {transferhistoryid};
		TransferHistoryMapper mapper=new TransferHistoryMapper();
		
		try {
            TransferHistory transferhistory = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return transferhistory;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String updateTransferHistory(int transferhistoryid, int productid, int orimainlocid, int orisublocid, int desmainlocid, int dessublocid, int quantity ){
		String sql=UPDATE_SQL+" set PRODUCT_ID = ?, ORI_MAIN_LOC = ?, ORI_SUB_LOC = ?, DES_MAIN_LOC = ?, DES_SUB_LOC = ?, QUANTITY = ? where ID= ?";
		Object[] params=new Object[]{productid, orimainlocid, orisublocid, desmainlocid, dessublocid, quantity, transferhistoryid};
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
	
	public void deleteStockHistory(int transferhistoryid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {transferhistoryid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public String approval(int transferhistoryid, String approve ){
		String sql=UPDATE_SQL+" set APPROVAL = ? where ID= ?";
		Object[] params=new Object[]{approve, transferhistoryid};
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
}
