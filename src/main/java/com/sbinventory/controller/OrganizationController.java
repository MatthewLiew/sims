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

import com.sbinventory.dao.DepartmentDAO;
import com.sbinventory.dao.OrganizationDAO;
import com.sbinventory.dao.SubDepartmentDAO;
//import com.sbinventory.dao.UserRoleDAO;
import com.sbinventory.model.Department;
import com.sbinventory.model.Organization;
import com.sbinventory.model.SubDepartment;

@Controller
public class OrganizationController {
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Autowired
	private SubDepartmentDAO subDepartmentDAO;

	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/organization")
	public String getOrganization(Model model) {
		List<Organization> organizations = organizationDAO.findAll();
		model.addAttribute("organizations", organizations);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
        
		return "organization/organization";
	}
	
	@GetMapping(value= "/department")
	public String getDepartment(Model model) {
		
		List<Department> departments = departmentDAO.findAll();
		model.addAttribute("departments", departments);
        
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "organization/department";
	}
	
	@GetMapping(value= "/subdepartment")
	public String getSubDepartment(Model model) {
//		List<Organization> orgs=organizationDAO.findAll();
//		model.addAttribute("orgs",orgs);
//		
//		List<Department> depts=deptDAO.findAll();
//		model.addAttribute("depts",depts);
		
		List<SubDepartment> subdepartments = subDepartmentDAO.findAll();
		model.addAttribute("subdepartments", subdepartments);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
        
		return "organization/subdepartment";
	}
	
	/**************** ORGANIZATION ACTION ***********************/
	@GetMapping(value= "/organization/createOrganization")
	public String getCreateOrganization(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		model.addAttribute("org",new Organization());
		
		return "organization/createOrganization";
	}
	
	@PostMapping(value= "/organization/createOrganization")
	public String postCreateOrganization(@ModelAttribute Organization org, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
		
		String errorString=organizationDAO.create(org.getName());
	
		if(errorString==null) {
			int organizationId = organizationDAO.getIdentCurrent();
			departmentDAO.create(organizationId, "");
			int departmentId = departmentDAO.getIdentCurrent();
			subDepartmentDAO.create(departmentId, "");
			
			String message="Organization - "+ org.getName() +" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/organization/editOrganization")
	public String getEditOrganization(@RequestParam int orgid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Organization org = organizationDAO.findOne(orgid);
	    model.addAttribute("org",org);
		
		return "organization/editOrganization";
	}
	
	@PostMapping(value= "/organization/editOrganization")
	public String postEditOrganization(@ModelAttribute Organization org, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString = organizationDAO.update(org.getId(), org.getName());
		
		if(errorString==null) {
			String message="Organization - "+ org.getName() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}

	@GetMapping(value= "/organization/deleteOrganization")
	public String getDeleteOrganization(@RequestParam int orgid, Model model, HttpServletRequest request ) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Organization org= organizationDAO.findOne(orgid);
		model.addAttribute("org", org);
		
		return "organization/deleteOrganization";
	}
	
	@PostMapping(value= "/organization/deleteOrganization")
	public String postDeleteOrganization(@ModelAttribute Organization org, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = organizationDAO.delete(org.getId());
		
		if(errorString==null) {
			String message="Organization - "+ org.getName() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** DEPARTMENT ACTION ***********************/
	@GetMapping(value= "/department/createDepartment")
	public String getCreateDepartment(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Organization> orgs= organizationDAO.findAll();
		model.addAttribute("orgs", orgs);
		
		model.addAttribute("dept", new Department());

		return "organization/createDepartment";
	}
	
	@PostMapping(value= "/department/createDepartment")
	public String postCreateDepartment(@ModelAttribute Department dept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString=departmentDAO.create(dept.getId(), dept.getName());

		if(errorString==null) {
			String message="Department - "+ dept.getName() +" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/department/editDepartment")
	public String getEditDepartment(@RequestParam int deptid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Department dept = departmentDAO.findOne(deptid);
		model.addAttribute("dept", dept);
		
		return "organization/editDepartment";
	}
	
	@PostMapping(value= "/department/editDepartment")
	public String postEditDepartment(@ModelAttribute Department dept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString=departmentDAO.update(dept.getId(), dept.getName());
		if(errorString==null) {
			String message="Department - "+ dept.getName() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
		
	@GetMapping(value= "/department/deleteDepartment")
	public String getDeleteDepartment(@RequestParam int deptid, Model model, HttpServletRequest request ) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Department dept= departmentDAO.findOne(deptid);
		model.addAttribute("dept", dept);
		
		return "organization/deleteDepartment";
	}
	
	@PostMapping(value= "/department/deleteDepartment")
	public String postDeleteDepartment(@ModelAttribute Department dept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = departmentDAO.delete(dept.getId());

		if(errorString==null) {
			String message="Department - "+ dept.getName() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** SUB DEPARTMENT ACTION ***********************/
	@GetMapping(value= "/subdepartment/createSubDepartment")
	public String getCreateSubDepartment(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Organization> organizations = organizationDAO.findAll();
		model.addAttribute("organizations", organizations);
		
		List<Department> departments = departmentDAO.findAll();
		model.addAttribute("departments", departments);

		model.addAttribute("sd", new SubDepartment());
		
		return "organization/createSubDepartment";
	}
	
	@PostMapping(value= "/subdepartment/createSubDepartment")
	public String postCreateSubDepartment(@ModelAttribute SubDepartment subdept, Model model,  @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString=subDepartmentDAO.create(subdept.getDepartmentId(), subdept.getName());

		if(errorString==null) {
			String message="Sub Department - "+ subdept.getName()+" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/subdepartment/editSubDepartment")
	public String getEditSubDepartment(@RequestParam int subdeptid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		SubDepartment sd = subDepartmentDAO.findOne(subdeptid);
		model.addAttribute("sd", sd);
		
//		Department dept=deptDAO.findOne(subdept.getDepartmentId());
//		model.addAttribute("dept",dept);
//		
//		Organization org=organizationDAO.findOne(dept.getId());
//		model.addAttribute("org",org);
	    
		return "organization/editSubDepartment";
	}
	
	@PostMapping(value= "/subdepartment/editSubDepartment")
	public String postEditSubDepartment(@ModelAttribute SubDepartment subdept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString = subDepartmentDAO.update(subdept.getId(), subdept.getName());
		if(errorString==null) {

			String message="Sub Department - "+ subdept.getName() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/subdepartment/deleteSubDepartment")
	public String getDeleteSubDepartment(@RequestParam int subdeptid, Model model, HttpServletRequest request ) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		SubDepartment sd = subDepartmentDAO.findOne(subdeptid);
		model.addAttribute("sd", sd);
		
		return "organization/deleteSubDepartment";
	}
	
	@PostMapping(value= "/subdepartment/deleteSubDepartment")
	public String postDeleteSubDepartment(@ModelAttribute SubDepartment subdept, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = subDepartmentDAO.delete(subdept.getId());
		
		if(errorString==null) {
			String message="Sub Department - "+ subdept.getName() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
}