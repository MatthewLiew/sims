package com.sims.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sims.mapper.RMAItemReasonMapper;
import com.sims.mapper.RMAItemTypeMapper;
import com.sims.model.RMAItemReason;
import com.sims.model.RMAItemType;

@Repository
@Transactional
public class RMAItemReasonDAO extends JdbcDaoSupport {

	private static final String CREATE_SQL="INSERT INTO RMA_ITEM_REASON(NAME) VALUES (?)";
	private static final String READ_SQL="SELECT * FROM RMA_ITEM_REASON";
	private static final String UPDATE_SQL="UPDATE RMA_ITEM_REASON";
	private static final String DELETE_SQL="DELETE FROM RMA_ITEM_REASON";
	
	@Autowired
	public RMAItemReasonDAO(DataSource dataSource) {
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
			return e.getMessage();
		}
	}
	
	public List<RMAItemReason> findAll(){
		
		String sql=READ_SQL;
		RMAItemReasonMapper mapper=new RMAItemReasonMapper();
		
		try {
            List<RMAItemReason> rmaitemreasons =  this.getJdbcTemplate().query(sql, mapper);
            return rmaitemreasons;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public RMAItemReason findOne(int id){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {id};
		RMAItemReasonMapper mapper=new RMAItemReasonMapper();
		
		try {
			RMAItemReason rmaitemreason = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return rmaitemreason;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
}
