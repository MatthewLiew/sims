package com.sbinventory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbinventory.dao.AppRoleDAO;
import com.sbinventory.dao.BrandDAO;
import com.sbinventory.dao.DeptDAO;
import com.sbinventory.dao.HardwareDAO;
import com.sbinventory.dao.MainLocDAO;
import com.sbinventory.dao.OrganizationDAO;
import com.sbinventory.dao.PartNoDAO;
import com.sbinventory.dao.ProductDAO;
import com.sbinventory.dao.ReasonDAO;
import com.sbinventory.dao.StockHistoryDAO;
import com.sbinventory.dao.StockTypeDAO;
import com.sbinventory.dao.SubDeptDAO;
import com.sbinventory.dao.SubLocDAO;
import com.sbinventory.dao.UserAccountDAO;
import com.sbinventory.dao.UserRoleDAO;
import com.sbinventory.model.AppRole;
import com.sbinventory.model.Dept;
import com.sbinventory.model.Organization;
import com.sbinventory.model.SubDept;
import com.sbinventory.model.UserAccount;
import com.sbinventory.model.UserRole;

@Controller
public class UserController {
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Autowired
	private SubDeptDAO subDeptDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private AppRoleDAO appRoleDAO;
	
	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/useraccount")
	public String getUserAccount(Model model) {
		
		List<UserAccount> useraccs=userAccountDAO.getAllUserAccount();
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<Dept> depts= deptDAO.getAllDept();
		List<SubDept> subdepts = subDeptDAO.getAllSubDept();
		
		model.addAttribute("useraccs",useraccs);
		model.addAttribute("approles",approles);
        model.addAttribute("orgs",orgs);
        model.addAttribute("depts",depts);
        model.addAttribute("subdepts",subdepts);
		
		return "userAccount/index";
	}
	
	/**************** USER ACCOUNT ACTION ***********************/
	@GetMapping(value= "/editUser")
	public String getEditUser(@RequestParam int userid, 
							  Model model, 
							  HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		UserAccount useracc= userAccountDAO.getUserAccount(userid);
		UserRole userrole=userRoleDAO.getUserRole(userid);
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<Dept> depts= deptDAO.getAllDept(useracc.getOrgid());
		List<SubDept> subdepts = subDeptDAO.getAllSubDept(useracc.getDeptid());
		
        model.addAttribute("useracc",useracc);
        model.addAttribute("userrole",userrole);
        model.addAttribute("approles",approles);
        model.addAttribute("orgs",orgs);
        model.addAttribute("depts",depts);
        model.addAttribute("subdepts",subdepts);
        model.addAttribute("referer", referer);
        model.addAttribute("errorString",null);
        
		return "userAccount/editUser";
	}
	
	@PostMapping(value= "/editUser")
	public String postEditUser(@RequestParam int userid, 
							   @RequestParam String username, 
							   @RequestParam int userrole, 
							   @RequestParam int orgid, 
							   @RequestParam int deptid, 
							   @RequestParam int subdeptid, 
							   Model model, 
							   @RequestParam String referer ) {

//			String errorString=userAccountDAO.updateUserAccount(userid, username);
//			if(errorString==null) {
		return "redirect:"+referer;
//			} else {
//				UserAccount useracc= userAccountDAO.getUserAccount(userid);
//				model.addAttribute("useracc",useracc);
//				model.addAttribute("errorString",errorString);
//				
//				return "userAccount/editUser";
//			}
	}
	
	@GetMapping(value= "/deleteUser")
	public String getDeleteUser(@RequestParam int userid, 
								Model model, 
								HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		UserAccount useracc= userAccountDAO.getUserAccount(userid);
		System.out.println(useracc.getUsername());
		model.addAttribute("useracc",useracc);
		model.addAttribute("referer", referer);
//			System.out.println(userid);
		return "userAccount/deleteUser";
	}
	
	@PostMapping(value= "/deleteUser")
	public String postDeleteUser(@RequestParam int userid,
								 Model model, 
								 @RequestParam String referer) {
//			userRoleDAO.deleteUserRole(userid);
		//userAccountDAO.deleteUserAccount(userid);
		System.out.println(userid);
		return "redirect:"+referer;
	}
	
	@GetMapping(value= "/createUser")
	public String getCreateUser(Model model, 
								HttpServletRequest request ) {
		
		String referer = request.getHeader("Referer");
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		model.addAttribute("errorString",null);
		model.addAttribute("orgs",orgs);
		model.addAttribute("approles",approles);
		model.addAttribute("referer", referer);
		
		return "userAccount/createUser";
	}
	
	@PostMapping(value= "/createUser")
	public String postCreateUser(/*@RequestParam int usercode,*/ 
								 @RequestParam String username,
								 @RequestParam String password,
								 @RequestParam int orgid, 
								 @RequestParam int deptid, 
								 @RequestParam int subdeptid, 
								 @RequestParam int userrole, 
								 Model model, 
								 @RequestParam String referer) {
			String errorString=userAccountDAO.createUserAccount( username, password, orgid, deptid, subdeptid);
//			UserAccount user=userAccountDAO.getUserName(username);
//
//			userRoleDAO.createUserRole(user.getUserid(), userrole);

//			System.out.println("Userid: "+userid);
		System.out.println(/*"User: "+usercode+" */"UserName: "+username+" Pw: "+password
				+" Org: "+orgid+" Dept: "+deptid+" Sub Dept:"+subdeptid);
//			if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "userAccount/createUser";
//			}
		
//			System.out.println("User role: "+userrole);
//			return "redirect:/userAcc";
	}
	
	@GetMapping(value= "/changePassword")
	public String getChangePassword(@RequestParam int userid, 
									Model model, 
									HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		model.addAttribute("userid",userid);
		model.addAttribute("referer", referer);
		return "userAccount/changePassword";
	}
	
	@PostMapping(value= "/changePassword")
	public String postChangePassword(@RequestParam int userid, 
									 @RequestParam String password, 
									 @RequestParam String repassword,
									 Model model, 
									 @RequestParam String referer  ) {
//		userAccountDAO.changeUserPassword(userid, password, repassword);
		return "redirect:"+referer;
	}
}
