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

import com.sbinventory.mapper.PartNoMapper;
import com.sbinventory.model.PartNo;

@Repository
@Transactional
public class PartNoDAO extends JdbcDaoSupport{
	
	private static final String READ_SQL="SELECT * FROM PART_NO";
	private static final String CREATE_SQL="INSERT INTO PART_NO (SERIAL_NO, MODEL_NO, UPC_CODE, PRODUCT_ID, CUSTOMER_NAME, INVOICE_NO, MAIN_LOC_ID, SUB_LOC_ID, ORG_ID, DEPT_ID, SUB_DEPT_ID, STOCK_STATUS) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_SQL="UPDATE PART_NO";
	private static final String DELETE_SQL="DELETE FROM PART_NO";
	
	@Autowired
	public PartNoDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(String serialno, String modelno, String upccode, int productid, String customername, String invoiceno, int mainlocid,
			int sublocid, int orgid, int deptid, int subdeptid, String status) {
		
		Object[] params=new Object[]{serialno, modelno, upccode, productid, customername, invoiceno, mainlocid, sublocid, orgid, deptid, subdeptid, status};
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
	
	public String update(int partnoid, String serialno, String modelno, String upccode, String customername, String invoiceno, int mainlocid,
			int sublocid, int orgid, int deptid, int subdeptid){
		String sql=UPDATE_SQL+" set SERIAL_NO = ?, MODEL_NO = ?, UPC_CODE = ?, CUSTOMER_NAME = ?, INVOICE_NO = ?, MAIN_LOC_ID = ?, "
				+ "SUB_LOC_ID = ?, ORG_ID = ?, DEPT_ID = ?, SUB_DEPT_ID = ? where ID= ?";
		Object[] params=new Object[] { serialno, modelno, upccode, customername, invoiceno, mainlocid, sublocid, orgid, deptid, subdeptid, partnoid };
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
	
	public String updateStatus(String status, String serialno){
		String sql=UPDATE_SQL+" set STOCK_STATUS = ? where SERIAL_NO = ?";
		Object[] params=new Object[] { status, serialno};
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
	
	public String updateReceiveStock(String serialno, int orgid, int deptid, int subdeptid, int mainlocid, int sublocid, String status ){
		String sql=UPDATE_SQL+" set ORG_ID = ?, DEPT_ID = ?, SUB_DEPT_ID = ?, MAIN_LOC_ID = ?, SUB_LOC_ID = ?, STOCK_STATUS = ? where SERIAL_NO = ?";
		Object[] params=new Object[] { orgid, deptid, subdeptid, mainlocid, sublocid, status, serialno };
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
	
	public String delete(int partnoid){ 
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {partnoid};
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
	
	public List<PartNo> findAll(){
		
		String sql=READ_SQL;
		
		PartNoMapper mapper=new PartNoMapper();
		
		try {
            List<PartNo> partnos =  this.getJdbcTemplate().query(sql, mapper);
            return partnos;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public PartNo findOne(int partnoid){
		
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
	
	public PartNo findOneBySerialNo(String serialno, String status){
//		String status="Available";
		String sql=READ_SQL+ " where SERIAL_NO = ? and STOCK_STATUS = ?";
		Object[] params =new Object[] {serialno, status};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return partno;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public List<PartNo> findAllByProductid(int productid){
		
		String sql=READ_SQL+ " where PRODUCT_ID = ?";
		Object[] params =new Object[] {productid};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
            List<PartNo> partnos =  this.getJdbcTemplate().query(sql, params, mapper);
            return partnos;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public PartNo getSerialNoByProductid(String serialno, int productid){
		
		String sql=READ_SQL+ " where SERIAL_NO = ? AND PRODUCT_ID = ?";
		Object[] params =new Object[] {serialno, productid};
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return partno;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public PartNo getSerialNo(String serialno, int partnoid){
		
		String sql=READ_SQL+ " where SERIAL_NO = ? ";
		Object[] params =new Object[] {serialno};
		if(partnoid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {serialno, partnoid};
		}
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return partno;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public PartNo getModelNo(String modelno, int partnoid){
		
		String sql=READ_SQL+ " where MODEL_NO = ? ";
		Object[] params =new Object[] {modelno};
		if(partnoid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {modelno, partnoid};
		}
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return partno;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	public PartNo getUpcCode(String upccode, int partnoid){
		
		String sql=READ_SQL+ " where UPC_CODE = ? ";
		Object[] params =new Object[] {upccode};
		if(partnoid!=0) {
			sql+="AND ID != ?";
			params= new Object[] {upccode, partnoid};
		}
		PartNoMapper mapper=new PartNoMapper();
		
		try {
			PartNo partno =  this.getJdbcTemplate().queryForObject(sql, params, mapper);
	        return partno;
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}
	
}
