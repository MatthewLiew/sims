package com.sbinventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.sbinventory.model.Dept;
import com.sbinventory.model.Organization;
import com.sbinventory.model.SubDept;

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
        
		return "organization/index";
	}
	
	@GetMapping(value= "/department")
	public String getDepartment(Model model) {
		List<Organization> orgs=organizationDAO.getAllOrganization();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts=deptDAO.getAllDept();
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts=subDeptDAO.getAllSubDept();
		model.addAttribute("subdepts",subdepts);
        
		return "organization/department/index";
	}
	
	@GetMapping(value= "/subdepartment")
	public String getSubDepartment(Model model) {
		List<Organization> orgs=organizationDAO.getAllOrganization();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts=deptDAO.getAllDept();
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts=subDeptDAO.getAllSubDept();
		model.addAttribute("subdepts",subdepts);
        
		return "organization/subdepartment/index";
	}
	
	/**************** ORGANIZATION ACTION ***********************/
	@GetMapping(value= "/createOrganization")
	public String getCreateOrganization(Model model ) {
//			List<Organization> orgs= organizationDAO.getAllOrganization();
//			List<AppRole> approles = appRoleDAO.getAllRoleNames();
		model.addAttribute("errorString",null);
//			model.addAttribute("orgs",orgs);
//			model.addAttribute("approles",approles);
		return "organization/createOrganization";
	}
	
	@PostMapping(value= "/createOrganization")
	public String postCreateOrganization(@RequestParam int orgcode,@RequestParam String orgname, Model model ) {
//			String errorString=organizationDAO.createOrganization(orgcode, orgname);
		System.out.println(orgcode+" "+orgname);
//			if(errorString==null) {
			return "redirect:/organization";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "organization/createOrganization";
//			}
	}
	
	@GetMapping(value= "/editOrganization")
	public String getEditOrganization(@RequestParam int orgid, Model model ) {
		
		Organization org = organizationDAO.getOrganization(orgid);
	    model.addAttribute("org",org);
	    
		return "organization/editOrganization";
	}
	
	@PostMapping(value= "/editOrganization")
	public String postEditOrganization(@RequestParam int orgid, @RequestParam int orgcode,
			@RequestParam String orgname, Model model ) {
		System.out.println(orgid+" "+orgcode+" "+orgname);
//			String errorString=organizationDAO.updateOrganization(orgid, orgcode, orgname);
//			if(errorString==null) {
		return "redirect:/organization";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "organization/editOrganization";
//			}
	}
	
	@GetMapping(value= "/deleteOrganization")
	public String getDeleteOrganization(@RequestParam int orgid, Model model ) {
		organizationDAO.deleteOrganization(orgid);
		
		return "redirect:/organization";
	}
	
	/**************** DEPARTMENT ACTION ***********************/
	@GetMapping(value= "/createDepartment")
	public String getCreateDepartment(Model model ) {
		List<Organization> orgs= organizationDAO.getAllOrganization();

		model.addAttribute("errorString",null);
		model.addAttribute("orgs",orgs);
		
		return "organization/createDepartment";
	}
	
	@PostMapping(value= "/createDepartment")
	public String postCreateDepartment(@RequestParam int orgid, @RequestParam int deptcode, @RequestParam String deptname, Model model ) {
//			String errorString=deptDAO.createDepartment(orgid, deptcode, deptname);
		System.out.println(orgid+" "+deptcode+" "+deptname);
//			if(errorString==null) {
			return "redirect:/organization";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "organization/createDepartment";
//			}
	}
	
	@GetMapping(value= "/editDepartment")
	public String getEditDepartment(@RequestParam int deptid, Model model ) {
		
		Dept dept = deptDAO.getDept(deptid);
		Organization org=organizationDAO.getOrganization(dept.getOrgid());
	    model.addAttribute("dept",dept);
	    model.addAttribute("org",org);
	    
		return "organization/editDepartment";
	}
	
	@PostMapping(value= "/editDepartment")
	public String postEditDepartment(@RequestParam int deptid, @RequestParam int deptcode,
			@RequestParam String deptname, @RequestParam int orgid, Model model ) {
		System.out.println(orgid+" "+deptid+" "+deptcode+" "+deptname);
//			String errorString=deptDAO.updateDepartment(deptid, deptcode, deptname);
//			if(errorString==null) {
		return "redirect:/organization";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "organization/editDepartment";
//			}
	}
	
	@GetMapping(value= "/deleteDepartment")
	public String getDeleteDepartment(@RequestParam int deptid, Model model ) {
		System.out.println(deptid);
//			deptDAO.deleteDepartment(deptid);
		
		return "redirect:/organization";
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
	public String getDeleteSubDepartment(@RequestParam int subdeptid, Model model ) {
			System.out.println(subdeptid);
//			subDeptDAO.deleteDepartment(subdeptid);
			
			return "redirect:/organization";
		}
		
}
