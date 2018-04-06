package com.sbinventory.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbinventory.dao.MainLocDAO;
import com.sbinventory.dao.SubLocDAO;
//import com.sbinventory.dao.UserRoleDAO;
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.SubLoc;

@Controller
public class LocationController {
	
	@Autowired
	private MainLocDAO mainLocDAO;
	
	@Autowired
	private SubLocDAO subLocDAO;
	
	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/mainlocation")
	public String getMainlocation(Model model, Principal principal) {
		
		List<MainLoc> mainlocs=mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
//		User loginedUser = (User) ((Authentication) principal).getPrincipal();
//		UserAccount useracc = userAccountDAO.getUserName(loginedUser.getUsername());
//		System.out.println("User ID: "+useracc.getUserid());
			
//		List<SubLoc> sublocs=subLocDAO.getAllSubLoc();
//		model.addAttribute("sublocs",sublocs);
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "location/mainloc";
	}
	
	@GetMapping(value= "/sublocation")
	public String getSublocation(Model model, Principal principal) {
		List<MainLoc> mainlocs=mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
//		User loginedUser = (User) ((Authentication) principal).getPrincipal();
//		UserAccount useracc = userAccountDAO.getUserName(loginedUser.getUsername());
//		System.out.println("User ID: "+useracc.getUserid());
			
		List<SubLoc> sublocs=subLocDAO.findAll();
		model.addAttribute("sublocs",sublocs);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
			
		return "location/subloc";
	}
		
	/**************** MAIN LOCATION ACTION ***********************/
	@GetMapping(value= "/mainlocation/createMainLoc")
	public String getCreateMainLoc(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		model.addAttribute("mainloc",new MainLoc());
			
		return "location/createMainLoc";
	}
	
	@PostMapping(value= "/mainlocation/createMainLoc")
	public String postCreateMainLoc(@ModelAttribute MainLoc mainloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString=mainLocDAO.create(mainloc.getMainlocname());
		
		if(errorString==null) {
			String message="Main Location - "+ mainloc.getMainlocname() +" created successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/mainlocation/editMainLoc")
	public String getEditMainLoc(@RequestParam int mainlocid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		MainLoc mainloc = mainLocDAO.findOne(mainlocid);
		model.addAttribute("mainloc",mainloc);
		    
		return "location/editMainLoc";
	}
		
	@PostMapping(value= "/mainlocation/editMainLoc")
	public String postEditMainLoc(@ModelAttribute MainLoc mainloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString=mainLocDAO.update(mainloc.getMainlocid(), mainloc.getMainlocname());

		if(errorString==null) {
			String message="Main Location - "+ mainloc.getMainlocname() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/mainlocation/deleteMainLoc")
	public String getDeleteMainLoc(@RequestParam int mainlocid, Model model, HttpServletRequest request ) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		MainLoc mainloc = mainLocDAO.findOne(mainlocid);
		model.addAttribute("mainloc", mainloc);
		
		return "location/deleteMainLoc";
	}
	
	@PostMapping(value= "/mainlocation/deleteMainLoc")
	public String getDeleteMainLoc(@ModelAttribute MainLoc mainloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = mainLocDAO.delete(mainloc.getMainlocid());
		
		if(errorString==null) {
			String message="Main Location - "+ mainloc.getMainlocname() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** SUB LOCATION ACTION ***********************/	
	@GetMapping(value= "/sublocation/createSubLoc")
	public String getCreateSubLoc(Model model, HttpServletRequest request) {
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<MainLoc> mainlocs= mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
		model.addAttribute("subloc",new SubLoc());
			
		return "location/createSubLoc";
	}
		
	@PostMapping(value= "/sublocation/createSubLoc")
	public String postCreateSubLoc(@ModelAttribute SubLoc subloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString=subLocDAO.create(subloc.getSublocname(), subloc.getMainlocid());
		
		if(errorString==null) {
			String message="Sub Location - "+ subloc.getSublocname() +" created successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
		
	@GetMapping(value= "/sublocation/editSubLoc")
	public String getEditSubLoc(@RequestParam int sublocid, Model model, HttpServletRequest request) {
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		SubLoc subloc = subLocDAO.findOne(sublocid);
		model.addAttribute("subloc",subloc);
		
		MainLoc mainloc = mainLocDAO.findOne(subloc.getMainlocid());
		model.addAttribute("mainloc",mainloc);
		    
		return "location/editSubLoc";
	}
		
	@PostMapping(value= "/sublocation/editSubLoc")
	public String postEditSubLoc(@ModelAttribute SubLoc subloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString=subLocDAO.update(subloc.getSublocid(), subloc.getSublocname());
		
		if(errorString==null) {
			String message="Sub Location - "+ subloc.getSublocname() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
		
	@GetMapping(value= "/sublocation/deleteSubLoc")
	public String getDeleteSubLoc(@RequestParam int sublocid, Model model, HttpServletRequest request ) {
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		SubLoc subloc = subLocDAO.findOne(sublocid);
		model.addAttribute("subloc", subloc);
	
		return "location/deleteSubLoc";
	}
	
	@PostMapping(value= "/sublocation/deleteSubLoc")
	public String getDeleteSubLoc(@ModelAttribute SubLoc subloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = subLocDAO.delete(subloc.getSublocid());
		
		if(errorString==null) {
			String message="Sub Location - "+ subloc.getSublocname() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
}
