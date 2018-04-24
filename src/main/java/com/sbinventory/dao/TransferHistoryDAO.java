package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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

	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (CODE, PRODUCT_ID, QUANTITY, SERIAL_NO, SOURCE, DESTINATION, TRANSFER_TYPE) VALUES (?,?,?,?,?,?,?)";
//	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (LOG_USER ) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM TRANSFER_HISTORY";
	private static final String UPDATE_SQL="UPDATE TRANSFER_HISTORY";
	private static final String DELETE_SQL="DELETE FROM TRANSFER_HISTORY";
	
	@Autowired
	public TransferHistoryDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
//	public String createOutBound(int orgid, int deptid, int subdeptid, int mainlocid, int sublocid, String approval, String loguser, 
//			String logdatetime, int transferhistoryid) {
//
//		Object[] params=new Object[]{orgid, deptid, subdeptid, mainlocid, sublocid, approval, loguser, logdatetime, transferhistoryid};
//		String sql="INSERT INTO OUTBOUND (ORG_ID, DEPT_ID, SUB_DEPT_ID, MAIN_LOC_ID, SUB_LOC_ID, APPROVAL, LOG_USER, LOG_DATETIME, TRANSFER_HISTORY_ID) "
//				+ "VALUES (?,?,?,?,?,?,?,?,?)";
//		
//		try {
//			int rows=this.getJdbcTemplate().update(sql, params);
//			System.out.println(rows + " row(s) updated.");
//			return null;
//		} catch (EmptyResultDataAccessException e) {
//			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
//			
//		} catch (DataAccessException e) {
//			return e.getMessage();
//		}
//	}
//	
//	public String createInBound(int orgid, int deptid, int subdeptid, int mainlocid, int sublocid, String accepted, String loguser, 
//			String logdatetime, int transferhistoryid) {
//
//		Object[] params=new Object[]{orgid, deptid, subdeptid, mainlocid, sublocid, accepted, loguser, logdatetime, transferhistoryid};
//		String sql="INSERT INTO OUTBOUND (ORG_ID, DEPT_ID, SUB_DEPT_ID, MAIN_LOC_ID, SUB_LOC_ID, ACCEPTED, LOG_USER, LOG_DATETIME, TRANSFER_HISTORY_ID) "
//				+ "VALUES (?,?,?,?,?,?,?,?,?)";
//		
//		try {
//			int rows=this.getJdbcTemplate().update(sql, params);
//			System.out.println(rows + " row(s) updated.");
//			return null;
//		} catch (EmptyResultDataAccessException e) {
//			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
//			
//		} catch (DataAccessException e) {
//			return e.getMessage();
//		}
//	}
	
	public String create(String code, int productid, int quantity, String serialno, String source, String destination, int transfertype,
			Integer srcorg, Integer srcdept, Integer srcsubdept, Integer srcmainloc, Integer srcsubloc, String istransfered, String trfuser, 
			String tfrdatetime, Integer desorg, Integer desdept, Integer dessubdept, Integer desmainloc, Integer dessubloc, 
			String isreceived, String recuser, String recdatetime) {

		Object[] params=new Object[]{code, productid, quantity, serialno, source, destination, transfertype, srcorg, srcdept, srcsubdept, srcmainloc, srcsubloc, 
				istransfered, trfuser, tfrdatetime, desorg, desdept, dessubdept, desmainloc, dessubloc, isreceived, recuser, recdatetime};
//		Object[] params=new Object[]{"null",logdatetime, productid, quantity, orimainlocid, orisublocid, desmainlocid, dessublocid};
		String sql="INSERT INTO TRANSFER_HISTORY (CODE, PRODUCT_ID, QUANTITY, SERIAL_NO, SOURCE, DESTINATION, TRANSFER_TYPE, "
				+ "SRC_ORG_ID, SRC_DEPT_ID, SRC_SUB_DEPT_ID, SRC_MAIN_LOC_ID, SRC_SUB_LOC_ID, TRANSFERED, TFR_USER, TFR_DATETIME, "
				+ "DES_ORG_ID, DES_DEPT_ID, DES_SUB_DEPT_ID, DES_MAIN_LOC_ID, DES_SUB_LOC_ID, RECEIVED, REC_USER, REC_DATETIME) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
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
	
	public String update(int transferhistoryid, String code, int productid, int quantity, String serialno ){
		String sql=UPDATE_SQL+" set CODE = ?, PRODUCT_ID = ?, QUANTITY = ?, SERIAL_NO = ? where ID= ?";
		Object[] params=new Object[]{code, productid, quantity, serialno, transferhistoryid};
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
	
	public String updateNewDestination(int transferhistoryid, String code, int productid, int quantity, String serialno, int transfertype, String source, 
			String destination, String tfruser, String tfrdatetime, Integer desorgid, Integer desdeptid, Integer dessubdeptid, Integer desmainlocid, 
			Integer dessublocid, String isreceived, String recuser, String recdatetime){
		String sql=UPDATE_SQL+" set CODE = ?, PRODUCT_ID = ?, QUANTITY = ?, SERIAL_NO = ?, TRANSFER_TYPE = ?, SOURCE = ?, DESTINATION = ?, TFR_USER = ?, "
				+ "TFR_DATETIME = ?, DES_ORG_ID = ?, DES_DEPT_ID = ?, DES_SUB_DEPT_ID = ?, DES_MAIN_LOC_ID = ?, DES_SUB_LOC_ID = ?, RECEIVED = ?, "
				+ "REC_USER = ?, REC_DATETIME = ? where ID= ?";
		Object[] params=new Object[]{code, productid, quantity, serialno, transfertype, source, destination, tfruser, tfrdatetime, desorgid, 
			 desdeptid, dessubdeptid, desmainlocid, dessublocid, isreceived, recuser, recdatetime, transferhistoryid};
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
	
	public String updateReceiveStock(int transferhistoryid, Integer desorgid, Integer desdeptid, Integer dessubdeptid, Integer desmainlocid, 
			Integer dessublocid, String isreceived, String recuser, String recdatetime){
		String sql=UPDATE_SQL+" set DES_ORG_ID = ?, DES_DEPT_ID = ?, DES_SUB_DEPT_ID = ?, DES_MAIN_LOC_ID = ?, DES_SUB_LOC_ID = ?, RECEIVED = ?, "
				+ "REC_USER = ?, REC_DATETIME = ? where ID= ?";
		Object[] params=new Object[]{desorgid, desdeptid, dessubdeptid, desmainlocid, dessublocid, isreceived, recuser, recdatetime, transferhistoryid};
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
	
	public String delete(int transferhistoryid){
		String sql=DELETE_SQL+" where ID = ?";
		Object[] params= new Object[] {transferhistoryid};
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
	
	public List<TransferHistory> findAll(){
		
		String sql=READ_SQL;
		TransferHistoryMapper mapper=new TransferHistoryMapper();
		
		try {
            List<TransferHistory> transferhistories =  this.getJdbcTemplate().query(sql, mapper);
            return transferhistories;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<TransferHistory> findAllInBound(){
		String isApproved="approved", isReceived="pending";
		String sql=READ_SQL+" WHERE TRANSFERED = ? ";
		Object[] params=new Object[] {isApproved};
		TransferHistoryMapper mapper=new TransferHistoryMapper();
		
		try {
            List<TransferHistory> transferhistories =  this.getJdbcTemplate().query(sql, params, mapper);
            return transferhistories;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public TransferHistory findOne(int transferhistoryid){
		
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
	
	public TransferHistory findOneByCode(String code){
		
		String sql="SELECT * "
				+ "FROM TRANSFER_HISTORY WHERE CODE = ?";
//		String sql="SELECT * FROM TRANSFER_HISTORY WHERE CODE = ?";
		Object[] params=new Object[] {code};
		TransferHistoryMapper mapper=new TransferHistoryMapper();
		
		try {
            TransferHistory transferhistory = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return transferhistory;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String approval(int transferhistoryid, String approve ){
		String sql=UPDATE_SQL+" set TRANSFERED = ? where ID= ?";
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
