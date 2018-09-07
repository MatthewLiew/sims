package com.sims.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sims.mapper.DisposalHistoryMapper;
import com.sims.mapper.RMAMapper;
import com.sims.model.DisposalHistory;
import com.sims.model.RMA;

@Repository
@Transactional
public class RMADAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO RMA_HISTORY(CODE, INVOICE_NO, SERIAL_NO, NAME, EMAIL, PHONE_NO, DESCRIPTION, REASON, TYPE, APPROVAL, REQUEST_USER, REQUEST_DATETIME, LOG_USER, LOG_DATETIME) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String READ_SQL="SELECT * FROM RMA_HISTORY";
	private static final String UPDATE_SQL="UPDATE RMA_HISTORY";
	private static final String DELETE_SQL="DELETE FROM RMA_HISTORY";
	
	@Autowired
	public RMADAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(String code, String invoiceno, String serialno, String name, String email, String phoneno, String desc, Integer rmareason, Integer rmatype, 
			String approval, String rquser, String rqdatetime, String loguser, String logdatetime) {

		Object[] params=new Object[]{code, invoiceno, serialno, name, email, phoneno, desc, rmareason, rmatype, approval, rquser, rqdatetime, loguser, logdatetime};

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
	
	public String update(int rmaid, String code, String invoiceno, String serialno, String name, String email, String phoneno, String desc, Integer rmareason, 
			Integer rmatype){
		String sql=UPDATE_SQL+" set CODE = ?, INVOICE_NO = ?, SERIAL_NO = ?, NAME = ?, EMAIL = ?, PHONE_NO = ?, DESCRIPTION = ?, REASON = ?, TYPE = ? where ID= ?";
		Object[] params=new Object[]{ code, invoiceno, serialno, name, email, phoneno, desc, rmareason, rmatype, rmaid};
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
	
	public int getCurrentIdent() {
		
		String sql="SELECT IDENT_CURRENT('RMA_HISTORY')";
		
		try {
			int index = this.getJdbcTemplate().queryForObject(sql, int.class);
			return index;
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
