package com.sbinventory.controller;

import java.security.Principal;
import com.sbinventory.dao.DeptDAO;
import com.sbinventory.dao.OrganizationDAO;
import com.sbinventory.dao.SubDeptDAO;
import com.sbinventory.dao.UserAccountDAO;
import com.sbinventory.model.Dept;
import com.sbinventory.model.Organization;
import com.sbinventory.model.SubDept;
import com.sbinventory.model.UserAccount;
import com.sbinventory.utils.WebUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@Autowired
    private UserAccountDAO userAccountDAO;
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Autowired
	private SubDeptDAO subDeptDAO;
	
	@GetMapping(value= {"/"/*,"/welcome"*/})
	public String welcomePage(Model model) {
		model.addAttribute("title", "In House Inventory System");
		return "index";
	}
	
	@GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
         
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("myAccInfo", userInfo);
         
        return "adminPage";
    }
 
	@GetMapping(value = "/login")
    public String login2Page(Model model) {
 
        return "login";
    }
 
	@GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }
 
	@GetMapping(value = "/myAccInfo")
    public String myAccInfo(Model model, Principal principal) {
 
        // (1) (en)
        // After user login successfully.
        // (vi)
        // Sau khi user login thanh cong se co principal
		model.addAttribute("title", "My Profile");
		
        String userName = principal.getName();
 
        System.out.println("User Name: " + userName);
 
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("myAccInfo", userInfo);
 
        UserAccount user=userAccountDAO.findOneByUsername(loginedUser.getUsername(),0);
        Organization org=organizationDAO.findOne(user.getOrgid());
        Dept dept=deptDAO.findOne(user.getDeptid());
        SubDept subdept=subDeptDAO.findOne(user.getSubdeptid());
        
        model.addAttribute("user",user);
        model.addAttribute("org",org);
        model.addAttribute("dept",dept);
        model.addAttribute("subdept",subdept);
        
        return "myProfile";
    }
 
	@GetMapping(value = "/403")
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
            String userInfo = WebUtils.toString(loginedUser);
 
            model.addAttribute("myAccInfo", userInfo);
 
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
            
            UserAccount user=userAccountDAO.findOneByUsername(loginedUser.getUsername(),0);
            Organization org=organizationDAO.findOne(user.getOrgid());
            Dept dept=deptDAO.findOne(user.getDeptid());
            SubDept subdept=subDeptDAO.findOne(user.getSubdeptid());
            
            model.addAttribute("user",user);
            model.addAttribute("org",org);
            model.addAttribute("dept",dept);
            model.addAttribute("subdept",subdept);
 
        }
 
        return "403Page";
    }
    
}
