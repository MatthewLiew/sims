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
import com.sbinventory.model.Dept;
import com.sbinventory.model.Organization;
import com.sbinventory.model.Product;
import com.sbinventory.model.SubDept;
import com.sbinventory.model.UserAccount;

@Controller
public class OrganizationController {
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Autowired
	private SubDeptDAO subDeptDAO;

	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/organization")
	public String getOrganization(Model model) {
		List<Organization> orgs=organizationDAO.getAllOrganization();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts=deptDAO.getAllDept();
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts=subDeptDAO.getAllSubDept();
		model.addAttribute("subdepts",subdepts);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
        
		return "organization/organization";
	}
	
	@GetMapping(value= "/department")
	public String getDepartment(Model model) {
		List<Organization> orgs=organizationDAO.getAllOrganization();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts=deptDAO.getAllDept();
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts=subDeptDAO.getAllSubDept();
		model.addAttribute("subdepts",subdepts);
        
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "organization/department";
	}
	
	@GetMapping(value= "/subdepartment")
	public String getSubDepartment(Model model) {
		List<Organization> orgs=organizationDAO.getAllOrganization();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts=deptDAO.getAllDept();
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts=subDeptDAO.getAllSubDept();
		model.addAttribute("subdepts",subdepts);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
        
		return "organization/subdepartment";
	}
	
	/**************** ORGANIZATION ACTION ***********************/
	@GetMapping(value= "/createOrganization")
	public String getCreateOrganization(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		model.addAttribute("org",new Organization());
		
		return "organization/createOrganization";
	}
	
	@PostMapping(value= "/createOrganization")
	public String postCreateOrganization(@ModelAttribute Organization org, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
		
		String errorString=organizationDAO.create(org.getOrgcode(), org.getOrgname());
	
		if(errorString==null) {
			String message="Organization - "+ org.getOrgname() +" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editOrganization")
	public String getEditOrganization(@RequestParam int orgid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Organization org = organizationDAO.getOrganization(orgid);
	    model.addAttribute("org",org);
		
		return "organization/editOrganization";
	}
	
	@PostMapping(value= "/editOrganization")
	public String postEditOrganization(@ModelAttribute Organization org, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString = organizationDAO.updateOrganization(org.getOrgid(), org.getOrgcode(), org.getOrgname());
		
		if(errorString==null) {
			String message="Organization - "+ org.getOrgname() +" updated succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}

	@GetMapping(value= "/deleteOrganization")
	public String getDeleteOrganization(@RequestParam int orgid, Model model, HttpServletRequest request ) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Organization org= organizationDAO.getOrganization(orgid);
		model.addAttribute("org", org);
		
		return "organization/deleteOrganization";
	}
	
	@PostMapping(value= "/deleteOrganization")
	public String postDeleteOrganization(@ModelAttribute Organization org, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = organizationDAO.deleteOrganization(org.getOrgid());
		
		if(errorString==null) {
			String message="Organization - "+ org.getOrgname() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** DEPARTMENT ACTION ***********************/
	@GetMapping(value= "/createDepartment")
	public String getCreateDepartment(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Organization> orgs= organizationDAO.getAllOrganization();
		model.addAttribute("orgs",orgs);
		
		model.addAttribute("dept",new Dept());

		return "organization/createDepartment";
	}
	
	@PostMapping(value= "/createDepartment")
	public String postCreateDepartment(@ModelAttribute Dept dept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString=deptDAO.create(dept.getOrgid(), dept.getDeptcode(), dept.getDeptname());
		
		if(errorString==null) {
			String message="Department - "+ dept.getDeptname() +" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editDepartment")
	public String getEditDepartment(@RequestParam int deptid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Dept dept = deptDAO.getDept(deptid);
		model.addAttribute("dept",dept);
		
		Organization org=organizationDAO.getOrganization(dept.getOrgid());
	    model.addAttribute("org",org);
	    
		return "organization/editDepartment";
	}
	
	@PostMapping(value= "/editDepartment")
	public String postEditDepartment(@ModelAttribute Dept dept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString=deptDAO.update(dept.getDeptid(), dept.getDeptcode(), dept.getDeptname());
		if(errorString==null) {
			String message="Department - "+ dept.getDeptname() +" updated succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
		
	@GetMapping(value= "/deleteDepartment")
	public String getDeleteDepartment(@RequestParam int deptid, Model model, HttpServletRequest request ) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Dept dept= deptDAO.getDept(deptid);
		model.addAttribute("dept", dept);
		
		return "organization/deleteDepartment";
	}
	
	@PostMapping(value= "/deleteDepartment")
	public String postDeleteDepartment(@ModelAttribute Dept dept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = deptDAO.deleteDepartment(dept.getDeptid());
		
		if(errorString==null) {
			String message="Department - "+ dept.getDeptname() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** SUB DEPARTMENT ACTION ***********************/
	@GetMapping(value= "/createSubDepartment")
	public String getCreateSubDepartment(Model model ) {
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<Dept> depts= deptDAO.getAllDept();

		model.addAttribute("errorString",null);
		model.addAttribute("orgs",orgs);
		model.addAttribute("depts",depts);
		
		return "organization/createSubDepartment";
	}
	
	@PostMapping(value= "/createSubDepartment")
	public String postCreateSubDepartment(@RequestParam int deptid, @RequestParam int subdeptcode, @RequestParam String subdeptname, Model model ) {
//			String errorString=subDeptDAO.createSubDepartment(deptid, subdeptcode, subdeptname);
		System.out.println(deptid+" "+subdeptcode+" "+subdeptname);
//			if(errorString==null) {
			return "redirect:/organization";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "organization/createSubDepartment";
//			}
	}
	
	@GetMapping(value= "/editSubDepartment")
	public String getEditSubDepartment(@RequestParam int subdeptid, Model model ) {
		
		SubDept subdept = subDeptDAO.getSubDept(subdeptid);
		Dept dept=deptDAO.getDept(subdept.getDeptid());
		Organization org=organizationDAO.getOrganization(dept.getOrgid());
	    model.addAttribute("subdept",subdept);
	    model.addAttribute("dept",dept);
	    model.addAttribute("org",org);
	    
		return "organization/editSubDepartment";
	}
	
	@PostMapping(value= "/editSubDepartment")
	public String postEditSubDepartment(@RequestParam int subdeptid, @RequestParam int subdeptcode,
			@RequestParam String subdeptname, @RequestParam int deptid, Model model ) {
		System.out.println(deptid+" "+subdeptid+" "+subdeptcode+" "+subdeptname);
//			String errorString=subDeptDAO.updateSubDepartment(subdeptid, subdeptcode, subdeptname);
//			if(errorString==null) {
		return "redirect:/organization";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "organization/editSubDepartment";
//			}
	}
	
	@GetMapping(value= "/deleteSubDepartment")
	public String getDeleteSubDepartment(@RequestParam int subdeptid, Model model, HttpServletRequest request ) {
		String referer = request.getHeader("Referer");
		SubDept subdept = subDeptDAO.getSubDept(subdeptid);
		
		model.addAttribute("subdept", subdept);
		model.addAttribute("referer", referer);
		return "organization/deleteSubDepartment";
	}
	
	@PostMapping(value= "/deleteSubDepartment")
	public String postDeleteSubDepartment(@RequestParam int subdeptid, Model model, @RequestParam String referer ) {
		System.out.println(subdeptid);
//			productDAO.deleteProduct(productid);
		
		return "redirect:"+referer;
	}
}