package com.sbinventory.controller;

import java.security.Principal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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
import com.sbinventory.model.Brand;
import com.sbinventory.model.Dept;
import com.sbinventory.model.Hardware;
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.Organization;
import com.sbinventory.model.PartNo;
import com.sbinventory.model.Product;
import com.sbinventory.model.Reason;
import com.sbinventory.model.StockHistory;
import com.sbinventory.model.StockType;
import com.sbinventory.model.SubDept;
import com.sbinventory.model.SubLoc;
import com.sbinventory.model.UserAccount;
import com.sbinventory.model.UserRole;
import com.sbinventory.utils.WebUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private AppRoleDAO appRoleDAO;
	
	@Autowired
	private MainLocDAO mainLocDAO;
	
	@Autowired
	private SubLocDAO subLocDAO;
	
	@Autowired
	private HardwareDAO hardwareDAO;
	
	@Autowired
	private BrandDAO brandDAO;
	
	@Autowired
	private PartNoDAO partNoDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ReasonDAO reasonDAO;
	
	@Autowired
	private StockHistoryDAO stockHistoryDAO;
	
	@Autowired
	private StockTypeDAO stockTypeDAO;
	
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
    public String loginPage(Model model) {
 
        return "loginPage";
    }
	
	@GetMapping(value = "/login2")
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
 
        UserAccount user=userAccountDAO.getUserName(loginedUser.getUsername());
        Organization org=organizationDAO.getOrganization(user.getOrgid());
        Dept dept=deptDAO.getDept(user.getDeptid());
        SubDept subdept=subDeptDAO.getSubDept(user.getSubdeptid());
        
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
            
            UserAccount user=userAccountDAO.getUserName(loginedUser.getUsername());
            Organization org=organizationDAO.getOrganization(user.getOrgid());
            Dept dept=deptDAO.getDept(user.getDeptid());
            SubDept subdept=subDeptDAO.getSubDept(user.getSubdeptid());
            
            model.addAttribute("user",user);
            model.addAttribute("org",org);
            model.addAttribute("dept",dept);
            model.addAttribute("subdept",subdept);
 
        }
 
        return "403Page";
    }
    
}
