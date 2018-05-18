package com.sbinventory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbinventory.dao.AppRoleDAO;
import com.sbinventory.dao.DepartmentDAO;
import com.sbinventory.dao.OrganizationDAO;
import com.sbinventory.dao.SubDepartmentDAO;
import com.sbinventory.dao.UserAccountDAO;
//import com.sbinventory.dao.UserRoleDAO;
import com.sbinventory.model.AppRole;
import com.sbinventory.model.Department;
import com.sbinventory.model.Organization;
import com.sbinventory.model.SubDepartment;
import com.sbinventory.model.UserAccount;

@Controller
public class UserController {
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	@Autowired
	private DepartmentDAO deptDAO;
	
	@Autowired
	private SubDepartmentDAO subDeptDAO;

//	@Autowired
//	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private AppRoleDAO appRoleDAO;
	
	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/useraccount")
	public String getUserAccount(Model model) {
		
		List<UserAccount> useraccs=userAccountDAO.findAll();
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		List<Organization> orgs= organizationDAO.findAll();
		List<Department> depts= deptDAO.findAll();
		List<SubDepartment> subdepts = subDeptDAO.findAll();
		
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
	@GetMapping(value= "/useraccount/createUser")
	public String getCreateUser(Model model, HttpServletRequest request ) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Organization> orgs= organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		model.addAttribute("approles",approles);
		
		UserAccount useracc = (UserAccount) model.asMap().get("useracc");
		String message = (String) model.asMap().get("message");

		if(useracc==null) {
			useracc = new UserAccount();
			useracc.setOrgid(0);
			useracc.setDeptid(0);
			useracc.setSubdeptid(0);
		}
		
		model.addAttribute("useracc", useracc);
		model.addAttribute("errorString", message);
		model.addAttribute("errorString",null);

		return "userAccount/createUser";
	}
	
	@PostMapping(value= "/useraccount/createUser")
	public String postCreateUser(@ModelAttribute UserAccount useracc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
//		System.out.println(useracc.getUsername());
//		System.out.println(useracc.getPassword());
//		System.out.println(useracc.getOrgid());
//		System.out.println(useracc.getDeptid());
//		System.out.println(useracc.getSubdeptid());
		
		String errorString=userAccountDAO.create(useracc.getUsername(), useracc.getPassword(), useracc.getOrgid(), useracc.getDeptid(), useracc.getSubdeptid(), useracc.getRoleid());
		
		if(errorString==null) {
			String message="User Name - "+ useracc.getUsername()+" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
//			ra.addFlashAttribute("useracc", useracc);
			ra.addFlashAttribute("message", errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/useraccount/editUser")
	public String getEditUser(@RequestParam int userid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		UserAccount useracc= userAccountDAO.findOne(userid);
		model.addAttribute("useracc",useracc);
		
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		model.addAttribute("approles",approles);
		
		List<Organization> orgs= organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
		List<Department> depts= deptDAO.findAllByOrgid(useracc.getOrgid());
		model.addAttribute("depts",depts);
		
		List<SubDepartment> subdepts = subDeptDAO.findAllByDeptid(useracc.getDeptid());
		model.addAttribute("subdepts",subdepts);
       
        model.addAttribute("errorString",null);
        
		return "userAccount/editUser";
	}
	
	@PostMapping(value= "/useraccount/editUser")

	public String postEditUser(@ModelAttribute UserAccount useracc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString = userAccountDAO.update(useracc.getUserid(), useracc.getUsername(), useracc.getOrgid(), useracc.getDeptid(), useracc.getSubdeptid(), useracc.getRoleid());
		
		if(errorString==null) {
			String message="User Name - "+ useracc.getUsername() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			ra.addFlashAttribute("message", errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/useraccount/deleteUser")
	public String getDeleteUser(@RequestParam int userid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		UserAccount useracc= userAccountDAO.findOne(userid);
		model.addAttribute("useracc",useracc);
		
		return "userAccount/deleteUser";
	}
	
	@PostMapping(value= "/useraccount/deleteUser")
	public String postDeleteUser(@ModelAttribute UserAccount useracc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = userAccountDAO.delete(useracc.getUserid());

		if(errorString==null) {
			String message="User Name - "+ useracc.getUsername() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		}else {
			ra.addFlashAttribute("message", errorString);
			return "redirect:"+sourceURL;
		}
	}

	@GetMapping(value= "/useraccount/changePassword")
	public String getChangePassword(@RequestParam int userid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("userid",userid);
		model.addAttribute("sourceURL", sourceURL);
		return "userAccount/changePassword";
	}
	
	@PostMapping(value= "/useraccount/changePassword")
	public String postChangePassword(@RequestParam int userid, 
									 @RequestParam String password, 
									 @RequestParam String repassword,
									 Model model, 
									 @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString = userAccountDAO.changePsw(userid, password, repassword);
		
		if(errorString==null) {
			String message="Password updated succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			ra.addFlashAttribute("message", errorString);
			return "redirect:"+sourceURL;
		}
	}
}
