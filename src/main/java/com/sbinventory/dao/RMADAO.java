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
import com.sbinventory.mapper.RMAMapper;
import com.sbinventory.model.DisposalHistory;
import com.sbinventory.model.RMA;

@Repository
@Transactional
public class RMADAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO RMA (LOG_USER, LOG_DATETIME, PRODUCT_ID, QUANTITY, MAIN_LOC, SUB_LOC, APPROVAL, REASON) VALUES (?,?,?,?,?,?,?,?)";
//	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (LOG_USER ) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM RMA";
	private static final String UPDATE_SQL="UPDATE RMA";
	private static final String DELETE_SQL="DELETE FROM RMA";
	
	@Autowired
	public RMADAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(String loguser, String logdatetime, int productid, int quantity, int mainlocid, int sublocid, 
			String approval, String reason) {

		Object[] params=new Object[]{loguser, logdatetime, productid, quantity, mainlocid, sublocid, approval, reason};
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
	
	public String update(int rmaid, int productid, int mainlocid, int sublocid, int quantity, String reason){
		String sql=UPDATE_SQL+" set PRODUCT_ID = ?, MAIN_LOC = ?, SUB_LOC = ?, QUANTITY = ?, REASON = ? where ID= ?";
		Object[] params=new Object[]{productid, mainlocid, sublocid, quantity, reason, rmaid};
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
	
	public String delete(int rmaid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {rmaid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<RMA> findAll(){
		
		String sql=READ_SQL;
		RMAMapper mapper=new RMAMapper();
		
		try {
            List<RMA> rmas =  this.getJdbcTemplate().query(sql, mapper);
            return rmas;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public RMA findOne(int rmaid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {rmaid};
		RMAMapper mapper=new RMAMapper();
		
		try {
			RMA rma = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return rma;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public String approval(int rmaid, String approve ){
		String sql=UPDATE_SQL+" set APPROVAL = ? where ID= ?";
		Object[] params=new Object[]{approve, rmaid};
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
