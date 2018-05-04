package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.DisposalHistoryMapper;
import com.sbinventory.model.DisposalHistory;

@Repository
@Transactional
public class DisposalHistoryDAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO DISPOSAL_HISTORY (CODE, PRODUCT_ID, QUANTITY, SERIAL_NO, ORG_ID, DEPT_ID, SUB_DEPT_ID, MAIN_LOC_ID, SUB_LOC_ID, APPROVAL, LOG_USER, LOG_DATETIME) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
//	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (LOG_USER ) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM DISPOSAL_HISTORY";
	private static final String UPDATE_SQL="UPDATE DISPOSAL_HISTORY";
	private static final String DELETE_SQL="DELETE FROM DISPOSAL_HISTORY";
	
	@Autowired
	public DisposalHistoryDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(String code, Integer productid, int quantity, String serialno, Integer orgid, Integer deptid, Integer subdeptid, Integer mainlocid, 
			Integer sublocid, String approval, Integer loguser, String logdatetime) {

		Object[] params=new Object[]{code, productid, quantity, serialno, orgid, deptid, subdeptid, mainlocid, sublocid,  approval, loguser, logdatetime};
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
	
	public String update(int disposalhistoryid, int productid, int mainlocid, int sublocid, int quantity ){
		String sql=UPDATE_SQL+" set PRODUCT_ID = ?, MAIN_LOC = ?, SUB_LOC = ?, QUANTITY = ? where ID= ?";
		Object[] params=new Object[]{productid, mainlocid, sublocid, quantity, disposalhistoryid};
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
	
	public String delete(int disposalhistoryid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {disposalhistoryid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<DisposalHistory> findAll(){
		
		String sql=READ_SQL;
		DisposalHistoryMapper mapper=new DisposalHistoryMapper();
		
		try {
            List<DisposalHistory> disposalhistories =  this.getJdbcTemplate().query(sql, mapper);
            return disposalhistories;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public DisposalHistory findOne(int disposalhistoryid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {disposalhistoryid};
		DisposalHistoryMapper mapper=new DisposalHistoryMapper();
		
		try {
			DisposalHistory disposalhistory = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return disposalhistory;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String approval(int disposalhistoryid, String approve ){
		String sql=UPDATE_SQL+" set APPROVAL = ? where ID= ?";
		Object[] params=new Object[]{approve, disposalhistoryid};
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
	
	public int getCurrentIdent() {
		
		String sql="SELECT IDENT_CURRENT('DISPOSAL_HISTORY')";
		
		try {
			int index = this.getJdbcTemplate().queryForObject(sql, int.class);
			return index;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
