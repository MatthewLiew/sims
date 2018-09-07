package com.sims.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sims.mapper.OrganizationMapper;
import com.sims.mapper.UserAccountMapper;
import com.sims.mapper.UserCapMapper;
import com.sims.model.Organization;
import com.sims.model.UserAccount;
import com.sims.model.UserCap;

@Repository
@Transactional
public class UserCapDAO extends JdbcDaoSupport {

	private static final String CREATE_SQL="INSERT INTO USER_CAP (APP_ROLE_ID, ACCESS_RIGHT, SIO_APPROVE, SIO_ADD, SIO_EDIT, "
			+ "SIO_DELETE, SM_ADD, SM_EDIT, SM_DELETE) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String READ_SQL="SELECT * FROM USER_CAP";
	private static final String UPDATE_SQL="UPDATE USER_CAP";
	private static final String DELETE_SQL="DELETE FROM USER_CAP";
	
	@Autowired
	public UserCapDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	public String create(int approleid, boolean accessright, boolean approve, boolean add, boolean edit, boolean delete) {
		
		Object[] params=new Object[]{approleid, accessright, approve, add, edit, delete};
		String sql=CREATE_SQL;
		try {
			int rows=this.getJdbcTemplate().update(sql, params);
			System.out.println(rows + " row(s) updated.");
			return null;
			
		} /*catch (DuplicateKeyException e) {
			return "Organization "+orgname+" Exist. Organization Creation Failed.";
			
		}*/ catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
			
		} catch (DataAccessException e) {
			return e.getMessage();
		}
	}
	
	public String update(int usercapid, int accessright, int sioapprove, int sioadd, int sioedit, int siodelete, int smadd, 
			int smedit, int smdelete, int sttransfer, int streceive, int stapprove, int stedit, int stdelete, int sddispose, 
			int sdapprove, int sdedit, int sddelete){
		String sql=UPDATE_SQL+" set ACCESS_RIGHT = ?, SIO_APPROVE = ?, SIO_ADD = ? , SIO_EDIT = ?, SIO_DELETE = ?, "
				+ "SM_ADD = ?, SM_EDIT = ?, SM_DELETE = ?, ST_TRANSFER = ?, ST_RECEIVE = ?, ST_APPROVE = ?, ST_EDIT = ?, "
				+ "ST_DELETE = ?, SD_DISPOSE = ?, SD_APPROVE = ?, SD_EDIT = ?, SD_DELETE = ? where ID= ?";
		Object[] params=new Object[]{accessright, sioapprove, sioadd, sioedit, siodelete, smadd, smedit, smdelete, sttransfer, streceive, stapprove, stedit,
				stdelete, sddispose, sdapprove, sdedit, sddelete, usercapid};
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
	
	public List<UserCap> findAll(){
		
		String sql=READ_SQL;
		UserCapMapper mapper=new UserCapMapper();
		
		try {
            List<UserCap> usercaps =  this.getJdbcTemplate().query(sql, mapper);
            return usercaps;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
//	public List<UserCap> findByModule(String module){
//		
//		String sql=READ_SQL+" WHERE MODULE = ?";
//		Object[] params=new Object[] {module};
//		UserCapMapper mapper=new UserCapMapper();
//		
//		try {
//            List<UserCap> usercaps =  this.getJdbcTemplate().query(sql, params, mapper);
//            return usercaps;
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//	}
	
	public UserCap findOne(int usercapid){
		
		String sql=READ_SQL+" where ID = ?";
		Object[] params=new Object[] {usercapid};
		UserCapMapper mapper=new UserCapMapper();
		
		try {
            UserCap usercap = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return usercap;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
	public UserCap findOneByApprole(int approleid){
		
		String sql=READ_SQL+" where APP_ROLE_ID = ?";
		Object[] params=new Object[] {approleid};
		UserCapMapper mapper=new UserCapMapper();
		
		try {
            UserCap usercap = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return usercap;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}
	
//	public String delete(int orgid){
//	String sql=DELETE_SQL+" where ID= ?";
//	Object[] params= new Object[] {orgid};
//	try {
//		int rows=this.getJdbcTemplate().update(sql, params);
//		System.out.println(rows + " row(s) updated.");
//		return null; 
//		
//	} catch (EmptyResultDataAccessException e) {
//		throw new EmptyResultDataAccessException("No Result Found.", 0 , e);
//		
//	} catch (DataIntegrityViolationException e) {
//		throw new DataIntegrityViolationException("Entity is tied. Please clear the parent.", e);
//		
//	} catch (DataAccessException e) {
//		return e.getMessage();
//	}
//}
	
//	public Organization getOrganizationCode(int orgcode, int orgid){
//		
//		String sql=READ_SQL+" where ORG_CODE = ?";
//		Object[] params=new Object[] {orgcode};
//		if(orgid!=0) {
//			sql+="AND ID != ?";
//			params= new Object[] {orgcode, orgid};
//		}
//		OrganizationMapper mapper=new OrganizationMapper();
//		
//		try {
//            Organization org = this.getJdbcTemplate().queryForObject(sql, params, mapper);
//            return org;
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//	}
//	
//	public Organization getOrganizationName(String orgname, int orgid){
//		String sql=READ_SQL+" where ORG_NAME = ? ";
//		Object[] params= new Object[] {orgname, orgid};
//		if(orgid!=0) {
//			sql+="AND ID != ?";
//			params= new Object[] {orgname, orgid};
//		}
//		OrganizationMapper mapper=new OrganizationMapper();
//		
//		try {
//			Organization org = this.getJdbcTemplate().queryForObject(sql,params,mapper);
//			return org;
//		}catch(EmptyResultDataAccessException e) {
//			return null;
//		}
//	}
//	
}
