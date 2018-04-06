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
import com.sbinventory.mapper.ReasonMapper;
import com.sbinventory.model.Organization;
import com.sbinventory.model.Reason;

@Repository
@Transactional
public class ReasonDAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO REASON (REASON, STOCK_TYPE_ID) VALUES (?,?)";
	private static final String READ_SQL="SELECT * FROM REASON";
	private static final String UPDATE_SQL="UPDATE REASON";
	private static final String DELETE_SQL="DELETE FROM REASON";
	
	@Autowired
	public ReasonDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String createReason(String reason, int stocktypeid) {
		
		Object[] params=new Object[]{reason, stocktypeid};
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
	
	public List<Reason> getAllReason(){
		
		String sql=READ_SQL;
		ReasonMapper mapper=new ReasonMapper();
		
		try {
            List<Reason> reasons =  this.getJdbcTemplate().query(sql, mapper);
            return reasons;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Reason getReason(int reasonid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {reasonid};
		ReasonMapper mapper=new ReasonMapper();
		
		try {
            Reason reason = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return reason;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Reason getReason(String reason){
		
		String sql=READ_SQL+" where REASON = ?";
		Object[] params=new Object[] {reason};
		ReasonMapper mapper=new ReasonMapper();
		
		try {
            Reason r = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return r;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public Reason getReason(String reason, int stocktypeid){
		
		String sql=READ_SQL+" where REASON = ? AND ID != ?";
		Object[] params=new Object[] {reason, stocktypeid};
		ReasonMapper mapper=new ReasonMapper();
		
		try {
            Reason r = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return r;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

//	public List<Reason> getReasonstockin(int stocktypeid){
//	
//		String sql=READ_SQL+" where STOCK_TYPE_ID = ? ";
//		Object[] params=new Object[] {stocktypeid};
//		ReasonMapper mapper=new ReasonMapper();
//		
//		try {
//            List<Reason> r = this.getJdbcTemplate().query(sql, params, mapper);
//            return r;
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//	}
	
	public List<Reason> findAllByStocktype(int stocktypeid){
		
		String sql=READ_SQL+" where STOCK_TYPE_ID = ? ";
		Object[] params=new Object[] {stocktypeid};
		ReasonMapper mapper=new ReasonMapper();
		
		try {
			List<Reason> r = this.getJdbcTemplate().query(sql, params, mapper);
            return r;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String updateReason(int reasonid, String reason, int stocktypeid){
		String sql=UPDATE_SQL+" set REASON = ?, STOCK_TYPE_ID = ? where ID= ?";
		Object[] params=new Object[]{reason, stocktypeid, reasonid};
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
	
	public String deleteReason(int reasonid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {reasonid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
