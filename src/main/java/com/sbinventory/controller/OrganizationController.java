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
		List<Organization> orgs=organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts=deptDAO.findAll();
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts=subDeptDAO.findAll();
		model.addAttribute("subdepts",subdepts);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
        
		return "organization/organization";
	}
	
	@GetMapping(value= "/department")
	public String getDepartment(Model model) {
		List<Organization> orgs=organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts=deptDAO.findAll();
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts=subDeptDAO.findAll();
		model.addAttribute("subdepts",subdepts);
        
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "organization/department";
	}
	
	@GetMapping(value= "/subdepartment")
	public String getSubDepartment(Model model) {
		List<Organization> orgs=organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts=deptDAO.findAll();
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts=subDeptDAO.findAll();
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
		
		Organization org = organizationDAO.findOne(orgid);
	    model.addAttribute("org",org);
		
		return "organization/editOrganization";
	}
	
	@PostMapping(value= "/editOrganization")
	public String postEditOrganization(@ModelAttribute Organization org, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString = organizationDAO.update(org.getOrgid(), org.getOrgcode(), org.getOrgname());
		
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
		
		Organization org= organizationDAO.findOne(orgid);
		model.addAttribute("org", org);
		
		return "organization/deleteOrganization";
	}
	
	@PostMapping(value= "/deleteOrganization")
	public String postDeleteOrganization(@ModelAttribute Organization org, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = organizationDAO.delete(org.getOrgid());
		
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
		
		List<Organization> orgs= organizationDAO.findAll();
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
		
		Dept dept = deptDAO.findOne(deptid);
		model.addAttribute("dept",dept);
		
		Organization org=organizationDAO.findOne(dept.getOrgid());
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
		
		Dept dept= deptDAO.findOne(deptid);
		model.addAttribute("dept", dept);
		
		return "organization/deleteDepartment";
	}
	
	@PostMapping(value= "/deleteDepartment")
	public String postDeleteDepartment(@ModelAttribute Dept dept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = deptDAO.delete(dept.getDeptid());

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
	public String getCreateSubDepartment(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Organization> orgs= organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts= deptDAO.findAll();
		model.addAttribute("depts",depts);

		model.addAttribute("subdept", new SubDept());
		
		return "organization/createSubDepartment";
	}
	
	@PostMapping(value= "/createSubDepartment")
	public String postCreateSubDepartment(@ModelAttribute SubDept subdept, Model model,  @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString=subDeptDAO.create(subdept.getDeptid(), subdept.getSubdeptcode(), subdept.getSubdeptname());

		if(errorString==null) {
			String message="Sub Department - "+ subdept.getSubdeptname()+" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editSubDepartment")
	public String getEditSubDepartment(@RequestParam int subdeptid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		SubDept subdept = subDeptDAO.findOne(subdeptid);
		model.addAttribute("subdept",subdept);
		
		Dept dept=deptDAO.findOne(subdept.getDeptid());
		model.addAttribute("dept",dept);
		
		Organization org=organizationDAO.findOne(dept.getOrgid());
		model.addAttribute("org",org);
	    
		return "organization/editSubDepartment";
	}
	
	@PostMapping(value= "/editSubDepartment")
	public String postEditSubDepartment(@ModelAttribute SubDept subdept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString = subDeptDAO.update(subdept.getSubdeptid(), subdept.getSubdeptcode(), subdept.getSubdeptname());
		if(errorString==null) {

			String message="Sub Department - "+ subdept.getSubdeptname() +" updated succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/deleteSubDepartment")
	public String getDeleteSubDepartment(@RequestParam int subdeptid, Model model, HttpServletRequest request ) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		SubDept subdept = subDeptDAO.findOne(subdeptid);
		model.addAttribute("subdept", subdept);
		
		return "organization/deleteSubDepartment";
	}
	
	@PostMapping(value= "/deleteSubDepartment")
	public String postDeleteSubDepartment(@ModelAttribute SubDept subdept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = subDeptDAO.delete(subdept.getSubdeptid());
		
		if(errorString==null) {
			String message="Sub Department - "+ subdept.getSubdeptname() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
}