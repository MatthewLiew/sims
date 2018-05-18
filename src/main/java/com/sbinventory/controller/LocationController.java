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

import com.sbinventory.dao.MainLocationDAO;
import com.sbinventory.dao.SubLocationDAO;
//import com.sbinventory.dao.UserRoleDAO;
import com.sbinventory.model.MainLocation;
import com.sbinventory.model.SubLocation;

@Controller
public class LocationController {
	
	@Autowired
	private MainLocationDAO mainLocationDAO;
	
	@Autowired
	private SubLocationDAO subLocationDAO;
	
	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/mainlocation")
	public String getMainlocation(Model model, Principal principal) {
		
		List<MainLocation> mainlocations = mainLocationDAO.findAll();
		model.addAttribute("mainlocations", mainlocations);
		
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
		
//		User loginedUser = (User) ((Authentication) principal).getPrincipal();
//		UserAccount useracc = userAccountDAO.getUserName(loginedUser.getUsername());
//		System.out.println("User ID: "+useracc.getUserid());
			
		List<SubLocation> sublocations = subLocationDAO.findAll();
		model.addAttribute("sublocations",sublocations);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
			
		return "location/subloc";
	}
		
	/**************** MAIN LOCATION ACTION ***********************/
	@GetMapping(value= "/mainlocation/createMainLoc")
	public String getCreateMainLoc(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		model.addAttribute("ml",new MainLocation());
			
		return "location/createMainLoc";
	}
	
	@PostMapping(value= "/mainlocation/createMainLoc")
	public String postCreateMainLoc(@ModelAttribute MainLocation mainloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString=mainLocationDAO.create(mainloc.getName());
		
		if(errorString==null) {
			int mainLocationId = mainLocationDAO.getIdentCurrent();
			subLocationDAO.create("", "default sub location of "+mainloc.getName(), mainLocationId);
			
			String message="Main Location - "+ mainloc.getName() +" created successfully";
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
		
		MainLocation ml = mainLocationDAO.findOne(mainlocid);
		model.addAttribute("ml", ml);
		    
		return "location/editMainLoc";
	}
		
	@PostMapping(value= "/mainlocation/editMainLoc")
	public String postEditMainLoc(@ModelAttribute MainLocation mainloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString=mainLocationDAO.update(mainloc.getId(), mainloc.getName());

		if(errorString==null) {
			String message="Main Location - "+ mainloc.getName() +" updated successfully";
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
		
		MainLocation ml = mainLocationDAO.findOne(mainlocid);
		model.addAttribute("ml", ml);
		
		return "location/deleteMainLoc";
	}
	
	@PostMapping(value= "/mainlocation/deleteMainLoc")
	public String getDeleteMainLoc(@ModelAttribute MainLocation mainloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = mainLocationDAO.delete(mainloc.getId());
		
		if(errorString==null) {
			String message="Main Location - "+ mainloc.getName() +" has deleted";
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
		
		List<MainLocation> mainlocations= mainLocationDAO.findAll();
		model.addAttribute("mainlocations", mainlocations);
		
		model.addAttribute("sublocation",new SubLocation());
			
		return "location/createSubLoc";
	}
		
	@PostMapping(value= "/sublocation/createSubLoc")
	public String postCreateSubLoc(@ModelAttribute SubLocation subloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString=subLocationDAO.create(subloc.getName(), subloc.getDescription(), subloc.getMainLocationId());
		
		if(errorString==null) {
			String message="Sub Location - "+ subloc.getName() +" created successfully";
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
		
		SubLocation sl = subLocationDAO.findOne(sublocid);
		model.addAttribute("sl", sl);
		
		MainLocation ml = mainLocationDAO.findOne(sl.getMainLocationId());
		model.addAttribute("ml", ml);
		    
		return "location/editSubLoc";
	}
		
	@PostMapping(value= "/sublocation/editSubLoc")
	public String postEditSubLoc(@ModelAttribute SubLocation subloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString=subLocationDAO.update(subloc.getId(), subloc.getName(), subloc.getDescription());
		
		if(errorString==null) {
			String message="Sub Location - "+ subloc.getName() +" updated successfully";
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
		
		SubLocation sb = subLocationDAO.findOne(sublocid);
		model.addAttribute("sb", sb);
	
		return "location/deleteSubLoc";
	}
	
	@PostMapping(value= "/sublocation/deleteSubLoc")
	public String getDeleteSubLoc(@ModelAttribute SubLocation subloc, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = subLocationDAO.delete(subloc.getId());
		
		if(errorString==null) {
			String message="Sub Location - "+ subloc.getName() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
}
