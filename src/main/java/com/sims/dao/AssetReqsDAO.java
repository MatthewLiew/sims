package com.sims.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sims.mapper.AssetReqsMapper;
import com.sims.mapper.DisposalHistoryMapper;
import com.sims.mapper.ITFMapper;
import com.sims.mapper.RMAMapper;
import com.sims.model.AssetReqs;
import com.sims.model.DisposalHistory;
import com.sims.model.ITF;
import com.sims.model.RMA;

@Repository
@Transactional
public class AssetReqsDAO extends JdbcDaoSupport{

	private static final String CREATE_SQL="INSERT INTO ASSET_REQS (LOG_USER, LOG_DATETIME, PRODUCT_ID, QUANTITY, MAIN_LOC, SUB_LOC, APPROVAL) VALUES (?,?,?,?,?,?,?)";
//	private static final String CREATE_SQL="INSERT INTO TRANSFER_HISTORY (LOG_USER ) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM ASSET_REQS";
	private static final String UPDATE_SQL="UPDATE ASSET_REQS";
	private static final String DELETE_SQL="DELETE FROM ASSET_REQS";
	
	@Autowired
	public AssetReqsDAO(DataSource dataSource) {
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
	
	public String update(int assetreqsid, int productid, int mainlocid, int sublocid, int quantity){
		String sql=UPDATE_SQL+" set PRODUCT_ID = ?, MAIN_LOC = ?, SUB_LOC = ?, QUANTITY = ? where ID= ?";
		Object[] params=new Object[]{productid, mainlocid, sublocid, quantity, assetreqsid};
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
	
	public String delete(int assetreqsid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {assetreqsid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<AssetReqs> findAll(){
		
		String sql=READ_SQL;
		AssetReqsMapper mapper=new AssetReqsMapper();
		
		try {
            List<AssetReqs> assetreqs =  this.getJdbcTemplate().query(sql, mapper);
            return assetreqs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public AssetReqs findOne(int assetreqsid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {assetreqsid};
		AssetReqsMapper mapper=new AssetReqsMapper();
		
		try {
			AssetReqs assetreqs = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return assetreqs;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public String approval(int assetreqsid, String approve ){
		String sql=UPDATE_SQL+" set APPROVAL = ? where ID= ?";
		Object[] params=new Object[]{approve, assetreqsid};
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
