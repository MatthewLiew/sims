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
		List<Organization> orgs= organizationDAO.findAll();
		List<Dept> depts= deptDAO.findAll();
		List<SubDept> subdepts = subDeptDAO.findAll();
		
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
		List<Organization> orgs= organizationDAO.findAll();
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		model.addAttribute("errorString",null);
		
		UserAccount useracc = (UserAccount) model.asMap().get("useracc");
		String message = (String) model.asMap().get("message");

		if(useracc==null) {
			useracc = new UserAccount();
		}
		model.addAttribute("useracc", useracc);
		model.addAttribute("errorString", message);
		model.addAttribute("orgs",orgs);
		model.addAttribute("approles",approles);
		model.addAttribute("sourceURL", sourceURL);
	
		return "userAccount/createUser";
	}
	
	@PostMapping(value= "/createUser")
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
	
	@GetMapping(value= "/editUser")
	public String getEditUser(@RequestParam int userid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		UserAccount useracc= userAccountDAO.findOne(userid);
//		UserRole userrole=userRoleDAO.findOneByUserid(userid);
		
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		List<Organization> orgs= organizationDAO.findAll();
		List<Dept> depts= deptDAO.findAllByOrgid(useracc.getOrgid());
		List<SubDept> subdepts = subDeptDAO.findAllByDeptid(useracc.getDeptid());
		
        model.addAttribute("useracc",useracc);
//        model.addAttribute("userrole",userrole);
        model.addAttribute("approles",approles);
        model.addAttribute("orgs",orgs);
        model.addAttribute("depts",depts);
        model.addAttribute("subdepts",subdepts);
        model.addAttribute("sourceURL", sourceURL);
        model.addAttribute("errorString",null);
        
		return "userAccount/editUser";
	}
	
	@PostMapping(value= "/editUser")

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
	
	@GetMapping(value= "/deleteUser")
	public String getDeleteUser(@RequestParam int userid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		UserAccount useracc= userAccountDAO.findOne(userid);
		model.addAttribute("useracc",useracc);
		
		return "userAccount/deleteUser";
	}
	
	@PostMapping(value= "/deleteUser")
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

	@GetMapping(value= "/changePassword")
	public String getChangePassword(@RequestParam int userid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("userid",userid);
		model.addAttribute("sourceURL", sourceURL);
		return "userAccount/changePassword";
	}
	
	@PostMapping(value= "/changePassword")
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
