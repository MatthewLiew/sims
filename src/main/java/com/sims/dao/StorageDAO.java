package com.sims.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sims.mapper.StockTypeMapper;
import com.sims.mapper.StorageMapper;
import com.sims.model.StockType;
import com.sims.model.Storage;

@Repository
@Transactional
public class StorageDAO extends JdbcDaoSupport {
	
	private static final String CREATE_SQL="INSERT INTO STORAGE1 (MAIN_LOC_ID, SUB_LOC_ID, PRODUCT_ID, QUANTITY) VALUES (?,?,?,?)";
	private static final String READ_SQL="SELECT * FROM STORAGE1";
	private static final String UPDATE_SQL="UPDATE STORAGE1";
	private static final String DELETE_SQL="DELETE FROM STORAGE1";
	
	@Autowired
	public StorageDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String createStorage(int mainlocid, int sublocid, int productid, int quantity) {
	
		Object[] params=new Object[]{mainlocid, sublocid, productid, quantity};
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
	
	public List<Storage> getAllStorage(){
		
		String sql=READ_SQL;
		StorageMapper mapper=new StorageMapper();
		
		try {
            List<Storage> storages =  this.getJdbcTemplate().query(sql, mapper);
            return storages;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<Storage> findAllByOrgid(int orgid){
		
		String sql=READ_SQL+" WHERE ORG_ID = ? ";
		Object[] params=new Object[] {orgid};
		StorageMapper mapper=new StorageMapper();
		
		try {
            List<Storage> storages =  this.getJdbcTemplate().query(sql, params, mapper);
            return storages;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<Storage> findAllByDeptid(int deptid){
		
		String sql=READ_SQL+" WHERE DEPT_ID = ? ";
		Object[] params=new Object[] {deptid};
		StorageMapper mapper=new StorageMapper();
		
		try {
            List<Storage> storages =  this.getJdbcTemplate().query(sql, params, mapper);
            return storages;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public List<Storage> findAllBySubdeptid(int subdeptid){
	
	String sql=READ_SQL+" WHERE SUB_DEPT_ID = ? ";
	Object[] params=new Object[] {subdeptid};
	StorageMapper mapper=new StorageMapper();
	
	try {
        List<Storage> storages =  this.getJdbcTemplate().query(sql, params, mapper);
        return storages;
    } catch (EmptyResultDataAccessException e) {
        return null;
    }
}
	
	public Storage getStorage(int storageid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {storageid};
		StorageMapper mapper=new StorageMapper();
		
		try {
			Storage storage = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return storage;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public String updateQuantity(int storageid, int quantity ){
		String sql=UPDATE_SQL+" set QUANTITY = ? where ID= ?";
		Object[] params=new Object[] { quantity, storageid};
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
	
	public String updateStorage(int stocktypeid, String stocktype ){
		String sql=UPDATE_SQL+" set STOCK_TYPE = ?, where ID= ?";
		Object[] params=new Object[]{stocktype, stocktypeid};
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
