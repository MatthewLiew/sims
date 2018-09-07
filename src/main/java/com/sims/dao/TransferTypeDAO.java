package com.sims.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sims.mapper.TransferTypeMapper;
import com.sims.model.TransferType;

@Repository
@Transactional
public class TransferTypeDAO extends JdbcDaoSupport {

	private static final String CREATE_SQL="INSERT INTO TRANSFER_TYPE (MODE) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM TRANSFER_TYPE";
	private static final String UPDATE_SQL="UPDATE TRANSFER_TYPE";
	private static final String DELETE_SQL="DELETE FROM TRANSFER_TYPE";
	
	@Autowired
	public TransferTypeDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
//	public String createStockType(String stocktype) {
//		
//		Object[] params=new Object[]{stocktype};
//		String sql=CREATE_SQL;
//		try {
//			int rows=this.getJdbcTemplate().update(sql, params);
//			System.out.println(rows + " row(s) updated.");
//			return null;
//		}catch(EmptyResultDataAccessException e ) {
//			return e.getMessage();
//			
//		}catch(DataAccessException  e) {
////			throw new DataAccessException("Something error", e);
//			return e.getMessage();
//		}
//	}
	
	public List<TransferType> findAll(){
		
		String sql=READ_SQL;
		TransferTypeMapper mapper=new TransferTypeMapper();
		
		try {
            List<TransferType> transfertypes =  this.getJdbcTemplate().query(sql, mapper);
            return transfertypes;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<TransferType> findAll(int id){
		
		String sql=READ_SQL + " where ID >= ? ";
		TransferTypeMapper mapper=new TransferTypeMapper();
		Object[] params=new Object[] {id-1};
		try {
            List<TransferType> transfertypes =  this.getJdbcTemplate().query(sql,params, mapper);
            return transfertypes;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public TransferType findOne(int transfertypeid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {transfertypeid};
		TransferTypeMapper mapper=new TransferTypeMapper();
		
		try {
			TransferType transfertype = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return transfertype;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String updateTransferType(int transferid, String mode ){
		String sql=UPDATE_SQL+" set MODE = ?, where ID= ?";
		Object[] params=new Object[]{mode, transferid};
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
	
	public void deleteTransferType(int transferid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {transferid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
