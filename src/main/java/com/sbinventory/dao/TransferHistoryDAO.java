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

	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (CODE, PRODUCT_ID, QUANTITY, SERIAL_NO, TRANSFER_TYPE, SOURCE, DESTINATION) VALUES (?,?,?,?,?,?,?)";
//	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (LOG_USER ) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM TRANSFER_HISTORY";
	private static final String UPDATE_SQL="UPDATE TRANSFER_HISTORY";
	private static final String DELETE_SQL="DELETE FROM TRANSFER_HISTORY";
	
	@Autowired
	public TransferHistoryDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String createOutBound(int orgid, int deptid, int subdeptid, int mainlocid, int sublocid, String approval, String loguser, 
			String logdatetime, int transferhistoryid) {

		Object[] params=new Object[]{orgid, deptid, subdeptid, mainlocid, sublocid, approval, loguser, logdatetime, transferhistoryid};
		String sql="INSERT INTO OUTBOUND (ORG_ID, DEPT_ID, SUB_DEPT_ID, MAIN_LOC_ID, SUB_LOC_ID, APPROVAL, LOG_USER, LOG_DATETIME, TRANSFER_HISTORY_ID) "
				+ "VALUES (?,?,?,?,?,?,?,?,?)";
		
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
	
	public String create(String code, int productid, int quantity, String serialno, int transfertype, String source, String destination) {

		Object[] params=new Object[]{code, productid, quantity, serialno, transfertype, source, destination };
//		Object[] params=new Object[]{"null",logdatetime, productid, quantity, orimainlocid, orisublocid, desmainlocid, dessublocid};
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
	
	public String update(int transferhistoryid, int productid, /*int orimainlocid, int orisublocid, int desmainlocid, int dessublocid,*/ int quantity ){
		String sql=UPDATE_SQL+" set PRODUCT_ID = ?, QUANTITY = ? where ID= ?";
		Object[] params=new Object[]{productid,/* orimainlocid, orisublocid, desmainlocid, dessublocid, */quantity, transferhistoryid};
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
		String sql=DELETE_SQL+" where ID= ?";
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
		
		String sql="SELECT * "
				+ "FROM TRANSFER_HISTORY TH, OUTBOUND OB "
				+ "WHERE TH.ID = OB.TRANSFER_HISTORY_ID";
		TransferHistoryMapper mapper=new TransferHistoryMapper();
		
		try {
            List<TransferHistory> transferhistories =  this.getJdbcTemplate().query(sql, mapper);
            return transferhistories;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public TransferHistory findOne(int transferhistoryid){
		
		String sql="SELECT * "
				+ "FROM TRANSFER_HISTORY TH, OUTBOUND OB "
				+ "WHERE TH.ID = OB.TRANSFER_HISTORY_ID AND TH.ID = ?";
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
		
		String sql="SELECT * FROM TRANSFER_HISTORY WHERE CODE = ?";
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
		String sql="UPDATE OUTBOUND set APPROVAL = ? where TRANSFER_HISTORY_ID= ?";
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
