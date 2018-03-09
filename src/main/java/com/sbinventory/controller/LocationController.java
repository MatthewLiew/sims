package com.sbinventory.controller;

import java.security.Principal;
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
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.SubLoc;

@Controller
public class LocationController {
	
	@Autowired
	private MainLocDAO mainLocDAO;
	
	@Autowired
	private SubLocDAO subLocDAO;
	
		@GetMapping(value= "/mainlocation")
	public String mainlocationPage(Model model, Principal principal) {
		List<MainLoc> mainlocs=mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs",mainlocs);
		
//			User loginedUser = (User) ((Authentication) principal).getPrincipal();
//			UserAccount useracc = userAccountDAO.getUserName(loginedUser.getUsername());
//			System.out.println("User ID: "+useracc.getUserid());
			
		List<SubLoc> sublocs=subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs",sublocs);
			
		return "location/index";
	}
	
	@GetMapping(value= "/sublocation")
	public String sublocationPage(Model model, Principal principal) {
		List<MainLoc> mainlocs=mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs",mainlocs);
		
//			User loginedUser = (User) ((Authentication) principal).getPrincipal();
//			UserAccount useracc = userAccountDAO.getUserName(loginedUser.getUsername());
//			System.out.println("User ID: "+useracc.getUserid());
			
		List<SubLoc> sublocs=subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs",sublocs);
			
		return "location/sublocation/index";
	}
		
	@GetMapping(value= "/createMainLoc")
	public String createMainLocGET(Model model ) {
		model.addAttribute("errorString",null);
			
		return "location/createMainLoc";
	}
		
	@PostMapping(value= "/createMainLoc")
	public String createMainLocPOST(@RequestParam String mainlocname, Model model ) {
//			String errorString=mainLocDAO.createMainLoc(mainlocname);
//			if(errorString==null) {
			return "redirect:/location";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "location/createMainLoc";
//			}
	}
		
	@GetMapping(value= "/editMainLoc")
	public String editMainLocPageGET(@RequestParam int mainlocid, Model model ) {
			
		MainLoc mainloc = mainLocDAO.getMainLoc(mainlocid);
		model.addAttribute("mainloc",mainloc);
		    
		return "location/editMainLoc";
	}
		
	@PostMapping(value= "/editMainLoc")
	public String editMainLocPagePOST(@RequestParam int mainlocid, 
			@RequestParam String mainlocname, Model model ) {
//			System.out.println(orgid+" "+deptid+" "+deptcode+" "+deptname);
//			String errorString=mainLocDAO.updateMainLoc(mainlocid, mainlocname);
//			if(errorString==null) {
			return "redirect:/location";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "location/editMainLoc";
//			}
	}
	
	@GetMapping(value= "/deleteMainLoc")
	public String deleteMainLoc(@RequestParam int mainlocid, Model model ) {
			
		mainLocDAO.deleteMainLoc(mainlocid);
			
		return "redirect:/location";
	}
	
		
	@GetMapping(value= "/createSubLoc")
	public String createSubLocGET(Model model ) {
		List<MainLoc> mainlocs= mainLocDAO.getAllMainLoc();
			
		model.addAttribute("errorString",null);
		model.addAttribute("mainlocs",mainlocs);
			
		return "location/createSubLoc";
	}
		
	@PostMapping(value= "/createSubLoc")
	public String createSubLocPOST(@RequestParam int mainlocid, @RequestParam String sublocname, Model model ) {
//			String errorString=subLocDAO.createSubLoc(sublocname, mainlocid);
//			System.out.println(deptid+" "+subdeptcode+" "+subdeptname);
//			if(errorString==null) {
			return "redirect:/location";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "location/createSubLoc";
//			}
	}
		
	@GetMapping(value= "/editSubLoc")
	public String editSubLocPageGET(@RequestParam int sublocid, Model model ) {
			
		SubLoc subloc = subLocDAO.getSubLoc(sublocid);
		MainLoc mainloc = mainLocDAO.getMainLoc(subloc.getMainlocid());
		    
		model.addAttribute("subloc",subloc);
		model.addAttribute("mainloc",mainloc);
		    
		return "location/editSubLoc";
	}
		
	@PostMapping(value= "/editSubLoc")
	public String editSubLocPagePOST(@RequestParam int sublocid,
			@RequestParam String sublocname, Model model ) {
//			System.out.println(deptid+" "+subdeptid+" "+subdeptcode+" "+subdeptname);
//			String errorString=subLocDAO.updateSubLoc(sublocid, sublocname);
//			if(errorString==null) {
			return "redirect:/location";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "location/editSubLoc";
//			}
	}
		
	@GetMapping(value= "/deleteSubLoc")
	public String deleteSubloc(@RequestParam int sublocid, Model model ) {
			System.out.println(sublocid);
//			subDeptDAO.deleteDepartment(subdeptid);
				
			return "redirect:/location";
		}
}
