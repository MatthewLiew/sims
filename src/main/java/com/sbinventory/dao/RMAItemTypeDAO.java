package com.sbinventory.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sbinventory.mapper.RMAItemTypeMapper;
import com.sbinventory.mapper.RMAMapper;
import com.sbinventory.model.RMA;
import com.sbinventory.model.RMAItemType;

@Repository
@Transactional
public class RMAItemTypeDAO extends JdbcDaoSupport{
	
	private static final String CREATE_SQL="INSERT INTO RMA_ITEM_TYPE(NAME) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM RMA_ITEM_TYPE";
	private static final String UPDATE_SQL="UPDATE RMA_ITEM_TYPE";
	private static final String DELETE_SQL="DELETE FROM RMA_ITEM_TYPE";
	
	@Autowired
	public RMAItemTypeDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(String name) {

		Object[] params=new Object[]{name};

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
	
	public String update(int id, String name){
		String sql=UPDATE_SQL+" set NAME = ? where ID= ?";
		Object[] params=new Object[]{name, id};
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
	
	public String delete(int id){
		String sql=DELETE_SQL+" where ID= ?";
		Object[] params= new Object[] {id};
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<RMAItemType> findAll(){
		
		String sql=READ_SQL;
		RMAItemTypeMapper mapper=new RMAItemTypeMapper();
		
		try {
            List<RMAItemType> rmaitemtypes =  this.getJdbcTemplate().query(sql, mapper);
            return rmaitemtypes;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public RMAItemType findOne(int id){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {id};
		RMAItemTypeMapper mapper=new RMAItemTypeMapper();
		
		try {
			RMAItemType rmaitemtype = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return rmaitemtype;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

}
