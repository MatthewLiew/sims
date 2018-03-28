package com.sbinventory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		
		List<UserAccount> useraccs=userAccountDAO.findAll();
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<Dept> depts= deptDAO.getAllDept();
		List<SubDept> subdepts = subDeptDAO.getAllSubDept();
		
		String message = (String)model.asMap().get("message");
		
		model.addAttribute("useraccs",useraccs);
		model.addAttribute("approles",approles);
        model.addAttribute("orgs",orgs);
        model.addAttribute("depts",depts);
        model.addAttribute("subdepts",subdepts);
        model.addAttribute("message", message);
		
		return "userAccount/index";
	}
	
	/**************** USER ACCOUNT ACTION ***********************/
	@GetMapping(value= "/createUser")
	public String getCreateUser(Model model, HttpServletRequest request ) {
		
		String sourceURL = request.getHeader("Referer");
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		model.addAttribute("errorString",null);
		
		UserAccount useracc = new UserAccount();
		
		model.addAttribute("useracc", useracc);
		model.addAttribute("orgs",orgs);
		model.addAttribute("approles",approles);
		model.addAttribute("sourceURL", sourceURL);
		
		return "userAccount/createUser";
	}
	
	@PostMapping(value= "/createUser")
	public String postCreateUser(@ModelAttribute UserAccount useracc, @RequestParam int userrole, Model model, @RequestParam String sourceURL, RedirectAttributes redirectAttributes  ) {
		
//		System.out.println(useracc.getUsername());
//		System.out.println(useracc.getPassword());
//		System.out.println(useracc.getOrgid());
//		System.out.println(useracc.getDeptid());
//		System.out.println(useracc.getSubdeptid());
		
		String errorString=userAccountDAO.create(useracc.getUsername(), useracc.getPassword(), useracc.getOrgid(), useracc.getDeptid(), useracc.getSubdeptid());
//			UserAccount user=userAccountDAO.getUserName(useracc.getUsername());
//			userRoleDAO.createUserRole(user.getUserid(), userrole);

		if(errorString==null) {
			
			String message="User Name - "+ useracc.getUsername()+" created succussfully";
			redirectAttributes.addFlashAttribute("message", message);
			
			return "redirect:"+sourceURL;
		} else {
			System.out.println(errorString);
			model.addAttribute("errorString",errorString);
			return "userAccount/createUser";
		}
	}
	
	@GetMapping(value= "/editUser")
	public String getEditUser(@RequestParam int userid, 
							  Model model, 
							  HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		UserAccount useracc= userAccountDAO.findOne(userid);
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

			String errorString=userAccountDAO.update(userid, username);
			if(errorString==null) {
		return "redirect:"+referer;
			} else {
				UserAccount useracc= userAccountDAO.findOne(userid);
				model.addAttribute("useracc",useracc);
				model.addAttribute("errorString",errorString);
				
				return "userAccount/editUser";
			}
	}
	
	@GetMapping(value= "/deleteUser")
	public String getDeleteUser(@RequestParam int userid, 
								Model model, 
								HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		UserAccount useracc= userAccountDAO.findOne(userid);
		System.out.println(useracc.getUsername());
		model.addAttribute("useracc",useracc);
		model.addAttribute("referer", referer);
//			System.out.println(userid);
		return "userAccount/deleteUser";
	}
	
	@PostMapping(value= "/deleteUser")
	public String postDeleteUser(@RequestParam int userid, Model model, @RequestParam String referer) {
//			userRoleDAO.deleteUserRole(userid);
		userAccountDAO.delete(userid);
		System.out.println(userid);
		return "redirect:"+referer;
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
		userAccountDAO.changePsw(userid, password, repassword);
		return "redirect:"+referer;
	}
}
