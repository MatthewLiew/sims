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
import com.sims.mapper.ITFMapper;
import com.sims.mapper.RMAMapper;
import com.sims.model.DisposalHistory;
import com.sims.model.ITF;
import com.sims.model.RMA;

@Repository
@Transactional
public class ITFDAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO ITF (LOG_USER, LOG_DATETIME, PRODUCT_ID, QUANTITY, MAIN_LOC, SUB_LOC, APPROVAL) VALUES (?,?,?,?,?,?,?)";
//	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (LOG_USER ) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM ITF";
	private static final String UPDATE_SQL="UPDATE ITF";
	private static final String DELETE_SQL="DELETE FROM ITF";
	
	@Autowired
	public ITFDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(String loguser, String logdatetime, int productid, int quantity, int mainlocid, int sublocid, 
			String approval) {

		Object[] params=new Object[]{loguser, logdatetime, productid, quantity, mainlocid, sublocid, approval};
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
	
	public String update(int rmaid, int productid, int mainlocid, int sublocid, int quantity){
		String sql=UPDATE_SQL+" set PRODUCT_ID = ?, MAIN_LOC = ?, SUB_LOC = ?, QUANTITY = ? where ID= ?";
		Object[] params=new Object[]{productid, mainlocid, sublocid, quantity, rmaid};
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
	
	public String delete(int itfid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {itfid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ITF> findAll(){
		
		String sql=READ_SQL;
		ITFMapper mapper=new ITFMapper();
		
		try {
            List<ITF> itfs =  this.getJdbcTemplate().query(sql, mapper);
            return itfs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public ITF findOne(int itfid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {itfid};
		ITFMapper mapper=new ITFMapper();
		
		try {
			ITF itf = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return itf;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String approval(int itfid, String approve ){
		String sql=UPDATE_SQL+" set APPROVAL = ? where ID= ?";
		Object[] params=new Object[]{approve, itfid};
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
