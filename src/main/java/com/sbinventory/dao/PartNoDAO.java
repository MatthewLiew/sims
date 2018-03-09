package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.PartNoMapper;
import com.sbinventory.model.PartNo;

@Repository
@Transactional
public class PartNoDAO extends JdbcDaoSupport{
	
	private static final String READ_SQL="SELECT * FROM PART_NO";
	private static final String CREATE_SQL="INSERT INTO PART_NO (SERIAL_NO, MODEL_NO, UPC_CODE, PRODUCT_ID, CUSTOMER_NAME, INVOICE_NO) VALUES (?,?,?,?,?,?)";
	private static final String UPDATE_SQL="UPDATE PART_NO";
	private static final String DELETE_SQL="DELETE FROM PART_NO";
	
	@Autowired
	public PartNoDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public List<PartNo> getAllPartNo(){
		
		String sql=READ_SQL;
		
		PartNoMapper mapper=new PartNoMapper();
		
		try {
            List<PartNo> partnos =  this.getJdbcTemplate().query(sql, mapper);
            return partnos;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public PartNo getPartNo(int partnoid){
		
		String sql=READ_SQL+ " where ID = ?";
		Object[] params =new Object[] {partnoid};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return partno;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public PartNo getSerialNo(String serialno){
		
		String sql=READ_SQL+ " where Serial_NO = ?";
		Object[] params =new Object[] {serialno};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return partno;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public PartNo getSerialNo(String serialno, int partnoid){
		
		String sql=READ_SQL+ " where Serial_NO = ? AND ID != ?";
		Object[] params =new Object[] {serialno, partnoid};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return partno;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public PartNo getModelNo(String modelno){
		
		String sql=READ_SQL+ " where MODEL_NO = ?";
		Object[] params =new Object[] {modelno};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return partno;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public PartNo getModelNo(String modelno, int partnoid){
		
		String sql=READ_SQL+ " where MODEL_NO = ? AND ID != ?";
		Object[] params =new Object[] {modelno, partnoid};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return partno;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public PartNo getUpcCode(String upccode){
	
		String sql=READ_SQL+ " where UPC_CODE = ?";
		Object[] params =new Object[] {upccode};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
	        return partno;
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}
	
	public PartNo getUpcCode(String upccode, int partnoid){
		
		String sql=READ_SQL+ " where UPC_CODE = ? AND ID != ?";
		Object[] params =new Object[] {upccode, partnoid};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
	        return partno;
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}
	
	public String createPartNo(String serialno, String modelno, String upccode, int productid, String customername, String invoiceno) {
		
		Object[] params=new Object[]{serialno, modelno, upccode, productid, customername, invoiceno};
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
	
	public String updatePartNo(int partnoid, String serialno, String modelno, String upccode, /*int productid,*/ String customername, String invoiceno){
		String sql=UPDATE_SQL+" set SERIAL_NO = ?, MODEL_NO = ?, UPC_CODE = ?, CUSTOMER_NAME = ?, INVOICE_NO = ? where ID= ?";
		Object[] params=new Object[] { serialno, modelno, upccode, /*productid,*/ customername, invoiceno, partnoid };
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
	
	public void deletePartNo(int partnoid){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {partnoid};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
	}
}
