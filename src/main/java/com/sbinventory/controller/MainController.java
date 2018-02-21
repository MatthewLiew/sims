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
	
//	@Autowired
//    private ProductDAO productDAO;
	
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
	
	@GetMapping(value= {"/","/welcome"})

	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message","MY FIRST SPRING BOOT PROJECT");
		return "welcomePage";
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
        
        return "myAccInfoPage";
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
    
	//********* PRODUCT *********//
	@GetMapping(value= "/product")
	public String productPage(Model model) {
		List<Hardware> hardwares = hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		List<PartNo> partnos = partNoDAO.getAllPartNo();
		List<Product> products = productDAO.getAllProduct();
		
		model.addAttribute("hardwares",hardwares);
		model.addAttribute("brands",brands);
		model.addAttribute("partnos",partnos);
        model.addAttribute("products",products);
        
		return "product/productPage";
	}
	@GetMapping(value= "/createHardware")
	public String createHardwareGET(Model model ) {
		model.addAttribute("errorString",null);
			
		return "product/createHardware";
	}	
	@PostMapping(value= "/createHardware")
	public String createHardwarePOST(@RequestParam int hardwarecode, @RequestParam String hardwaretype, Model model ) {
//		String errorString=hardwareDAO.createHardware(hardwarecode, hardwaretype);
//		if(errorString==null) {
			return "redirect:/product";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "product/createHardware";
//		}
	}
	@GetMapping(value= "/editHardware")
	public String editHardwarePageGET(@RequestParam int hardwareid, Model model ) {
			
		Hardware hardware = hardwareDAO.getHardware(hardwareid);
		model.addAttribute("hardware",hardware);
		    
		return "product/editHardware";
	}	
	@PostMapping(value= "/editHardware")
	public String editHardwarePagePOST(@RequestParam int hardwareid, @RequestParam int hardwarecode,
			@RequestParam String hardwaretype, Model model ) {
//		System.out.println(orgid+" "+deptid+" "+deptcode+" "+deptname);
//		String errorString=hardwareDAO.updateDepartment(hardwareid, hardwarecode, hardwaretype);
//		if(errorString==null) {
			return "redirect:/product";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "product/editHardware";
//		}
	}
	@GetMapping(value= "/deleteHardware")
	public String deleteHardware(@RequestParam int hardwareid, Model model ) {
		System.out.println(hardwareid);
//		hardwareDAO.deleteDepartment(hardwareid);
		return "redirect:/product";
	}
	
	@GetMapping(value= "/createBrand")
	public String createBrandGET(Model model ) {
		model.addAttribute("errorString",null);
			
		return "product/createBrand";
	}
	@PostMapping(value= "/createBrand")
	public String createBrandPOST(@RequestParam int brandcode, @RequestParam String brandname, Model model ) {
//		String errorString= brandDAO.createBrand(brandcode, brandname);
//		if(errorString==null) {
			return "redirect:/product";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "product/createBrand";
//		}
	}
	@GetMapping(value= "/editBrand")
	public String editBrandPageGET(@RequestParam int brandid, Model model ) {
			
		Brand brand = brandDAO.getBrand(brandid);
		model.addAttribute("brand",brand);
		    
		return "product/editBrand";
	}
	@PostMapping(value= "/editBrand")
	public String editBrandPagePOST(@RequestParam int brandid, @RequestParam int brandcode,
			@RequestParam String brandname, Model model ) {
		System.out.println(brandid+" "+brandcode);
//		String errorString=brandDAO.updateBrand(brandid, brandcode, brandname);
//		if(errorString==null) {
			return "redirect:/product";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "product/editBrand";
//		}
	}
	@GetMapping(value= "/deleteBrand")
	public String deleteBrand(@RequestParam int brandid, Model model ) {
		System.out.println(brandid);
//		brandDAO.deleteBrand(brandid);
		
		return "redirect:/product";
	}
	
	@GetMapping(value= "/createPartNo")
	public String createPartNoGET(Model model ) {
		model.addAttribute("errorString",null);
			
		return "product/createPartNo";
	}
	@PostMapping(value= "/createPartNo")
	public String createPartNoPOST(@RequestParam String serialno,
			@RequestParam String modelno, @RequestParam String upccode, Model model ) {
		String errorString= partNoDAO.createPartNo(serialno, modelno, upccode);
		if(errorString==null) {
			return "redirect:/product";
		} else {
			model.addAttribute("errorString",errorString);
			return "product/createPartNo";
		}
	}
	@GetMapping(value= "/editPartNo")
	public String editPartNoPageGET(@RequestParam int partnoid, Model model ) {
		
		PartNo partno = partNoDAO.getPartNo(partnoid);
		model.addAttribute("partno",partno);
		    
		return "product/editPartNo";
	}
	@PostMapping(value= "/editPartNo")
	public String editPartNoPagePOST(@RequestParam int partnoid,
			@RequestParam String serialno, @RequestParam String modelno, @RequestParam String upccode, Model model ) {

		String errorString=partNoDAO.updatePartNo(partnoid, serialno, modelno, upccode);
		if(errorString==null) {
			return "redirect:/product"; 
		} else {
			model.addAttribute("errorString",errorString);
			return "product/editPartNo";
		}
	}
	@GetMapping(value= "/deletePartNo")
	public String deletePartNo(@RequestParam int partnoid, Model model ) {
		System.out.println(partnoid);
//		brandDAO.deleteBrand(brandid);
		
		return "redirect:/product";
	}

	@GetMapping(value= "/createProduct")
	public String createProductGET(Model model ) {

		List<Hardware> hardwares = hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		List<PartNo> partnos = partNoDAO.getAllPartNo();

		model.addAttribute("errorString",null);
		model.addAttribute("hardwares",hardwares);
		model.addAttribute("brands",brands);
		model.addAttribute("partnos",partnos);
		
		return "product/createProduct";
	}
	@PostMapping(value= "/createProduct")
	public String createProductPOST(@RequestParam int productcode, @RequestParam String productname, @RequestParam int hardwareid,
			@RequestParam int brandid, @RequestParam int partnoid, @RequestParam int lbvalue, Model model ) {

//		String errorString=productDAO.createProduct(productcode, productname, hardwareid, brandid, partnoid, lbvalue);
//		System.out.println(productcode+" "+productname+" "+hardwareid+" "+brandid+" "+partnoid+" "+lbvalue);
//		if(errorString==null) {
			return "redirect:/product";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "product/createProduct";
//		}
	}
	@GetMapping(value= "/editProduct")
	public String editProductPageGET(@RequestParam int productid,Model model ) {
		Product product= productDAO.getProduct(productid);
		List<Hardware> hardwares=hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		List<PartNo> partnos= partNoDAO.getAllPartNo();

        model.addAttribute("product",product);
        model.addAttribute("hardwares",hardwares);
        model.addAttribute("brands",brands);
        model.addAttribute("partnos",partnos);
        model.addAttribute("errorString",null);
        
		return "product/editProduct";
	}
	@PostMapping(value= "/editProduct")
	public String editProductPOST(@RequestParam int productid, @RequestParam int productcode, @RequestParam String productname, 
			@RequestParam int brandid, @RequestParam int hardwareid, @RequestParam int lbvalue, 
			Model model ) {

//		String errorString=productDAO.updateProduct(productid, productcode, productname);
//		if(errorString==null) {
		return "redirect:/product";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "product/editProduct";
//		}
	}
	@GetMapping(value= "/editProducts")
	public String editProductsPageGET(@RequestParam int productid,Model model ) {
		Product product= productDAO.getProduct(productid);
		List<Hardware> hardwares=hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		List<PartNo> partnos= partNoDAO.getAllPartNo();

        model.addAttribute("product",product);
        model.addAttribute("hardwares",hardwares);
        model.addAttribute("brands",brands);
        model.addAttribute("partnos",partnos);
        model.addAttribute("errorString",null);
        
		return "product/editProducts";
	}
	@PostMapping(value= "/editProducts")
	public String editProductsPOST(@RequestParam int productid, @RequestParam int productcode, @RequestParam String productname, 
			@RequestParam int brandid, @RequestParam int hardwareid, @RequestParam int lbvalue, 
			Model model ) {

//		String errorString=productDAO.updateProduct(productid, productcode, productname);
//		if(errorString==null) {
		return "redirect:/stock";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "product/editProduct";
//		}
	}
	@GetMapping(value= "/deleteProduct")
	public String deleteProduct(@RequestParam int productid, Model model ) {
		System.out.println(productid);
//		productDAO.deleteProduct(productid);
		
		return "redirect:/product";
	}
	@GetMapping(value= "/deleteProducts")
	public String deleteProducts(@RequestParam int productid, Model model ) {
		System.out.println(productid);
//		productDAO.deleteProduct(productid);
		
		return "redirect:/stock";
	}
	
	//********* USERACCOUNT *********//
	@GetMapping(value= "/useraccount")
	public String userAccountPage(Model model) {
		List<UserAccount> useraccs=userAccountDAO.getAllUserAccount();
	
		model.addAttribute("useraccs",useraccs);
		
		return "userAccount/userAccPage";
	}
	
	@GetMapping(value= "/editUser")
	public String editUserPageGET(@RequestParam int userid,Model model ) {
		UserAccount useracc= userAccountDAO.getUserAccount(userid);
		UserRole userrole=userRoleDAO.getUserRole(userid);
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<Dept> depts= deptDAO.getAllDept(useracc.getOrgid());
		List<SubDept> subdepts = subDeptDAO.getAllSubDept(useracc.getDeptid());
        model.addAttribute("useracc",useracc);
        model.addAttribute("userrole",userrole);
        model.addAttribute("approles",approles);
        model.addAttribute("orgs",orgs);
        model.addAttribute("depts",depts);
        model.addAttribute("subdepts",subdepts);
        model.addAttribute("errorString",null);
        
		return "userAccount/editUser";
	}
	
	@PostMapping(value= "/editUser2")
	public String editUserPagePOST2(@RequestParam int userid,Model model ) {
		UserAccount useracc= userAccountDAO.getUserAccount(userid);
		UserRole userrole=userRoleDAO.getUserRole(userid);
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<Dept> depts= deptDAO.getAllDept(useracc.getOrgid());
		List<SubDept> subdepts = subDeptDAO.getAllSubDept(useracc.getDeptid());
        model.addAttribute("useracc",useracc);
        model.addAttribute("userrole",userrole);
        model.addAttribute("approles",approles);
        model.addAttribute("orgs",orgs);
        model.addAttribute("depts",depts);
        model.addAttribute("subdepts",subdepts);
        model.addAttribute("errorString",null);
        
		return "userAccount/editUser";
	}
	
	@PostMapping(value= "/editUser")
	public String editUserPagePOST(@RequestParam int userid, @RequestParam int usercode, @RequestParam String username, 
			@RequestParam int userrole, @RequestParam int orgid, @RequestParam int deptid, @RequestParam int subdeptid, 
			Model model ) {
//		System.out.println(userid);
//		System.out.println(userrole+" "+orgid+" "+deptid+" "+subdeptid);
//		String errorString=userAccountDAO.updateUserAccount(usercode, userid, username);
//		if(errorString==null) {
		return "redirect:/useraccount";
//		} else {
//			UserAccount useracc= userAccountDAO.getUserAccount(userid);
//			model.addAttribute("useracc",useracc);
//			model.addAttribute("errorString",errorString);
//			
//			return "userAccount/editUser";
//		}
	}
	
	@GetMapping(value= "/deleteUser")
	public String deleteUser(@RequestParam int userid,Model model ) {
		userRoleDAO.deleteUserRole(userid);
		userAccountDAO.deleteUserAccount(userid);
		
		return "redirect:/useraccount";
	}
	
	@GetMapping(value= "/createUser")
	public String createUserGET(Model model ) {
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		model.addAttribute("errorString",null);
		model.addAttribute("orgs",orgs);
		model.addAttribute("approles",approles);
		return "userAccount/createUser";
	}
	
	@PostMapping(value= "/createUser")
	public String createUserPOST(@RequestParam String userid, @RequestParam int usercode,@RequestParam String username,@RequestParam String password,
			@RequestParam int orgid, @RequestParam int deptid, @RequestParam int subdeptid, @RequestParam int userrole, Model model ) {
//		String errorString=userAccountDAO.createUserAccount(usercode, username, password, orgid, deptid, subdeptid);
//		UserAccount user=userAccountDAO.getUserName(username);
//
//		userRoleDAO.createUserRole(user.getUserid(), userrole);

//		System.out.println("Userid: "+userid);
		System.out.println("User: "+userid+" UserName: "+username+" Pw: "+password
				+" Org: "+orgid+" Dept: "+deptid+" Sub Dept:"+subdeptid);
//		if(errorString==null) {
			return "redirect:/useraccount";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "userAccount/createUser";
//		}
		
//		System.out.println("User role: "+userrole);
//		return "redirect:/userAcc";
	}
	
	@GetMapping(value= "/changePassword")
	public String changePasswordGET(@RequestParam int userid, Model model ) {
		model.addAttribute("userid",userid);
		return "userAccount/changePassword";
	}
	
	@PostMapping(value= "/changePassword")
	public String changePasswordPOST(@RequestParam int userid, @RequestParam String password, @RequestParam String repassword,Model model ) {
		userAccountDAO.changeUserPassword(userid, password, repassword);
		return "redirect:/useraccount";
	}

	//********* ORGANIZATION *********//
	
	@GetMapping(value= "/organization")
	public String organizationPage(Model model) {
		List<Organization> orgs=organizationDAO.getAllOrganization();
		model.addAttribute("orgs",orgs);
//		model.addAttribute("orgname", arg1)
		
		List<Dept> depts=deptDAO.getAllDept();
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts=subDeptDAO.getAllSubDept();
		model.addAttribute("subdepts",subdepts);
		
		List<Hardware> hardwares = hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		List<Product> products = productDAO.getAllProduct();
		
		model.addAttribute("hardwares",hardwares);
		model.addAttribute("brands",brands);
        model.addAttribute("products",products);
        
		return "organization/organizationPage";
	}
	
	@GetMapping(value= "/createOrganization")
	public String createOrganizationGET(Model model ) {
//		List<Organization> orgs= organizationDAO.getAllOrganization();
//		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		model.addAttribute("errorString",null);
//		model.addAttribute("orgs",orgs);
//		model.addAttribute("approles",approles);
		return "organization/createOrganization";
	}
	
	@PostMapping(value= "/createOrganization")
	public String createOrganizationPOST(@RequestParam int orgcode,@RequestParam String orgname, Model model ) {
//		String errorString=organizationDAO.createOrganization(orgcode, orgname);
		System.out.println(orgcode+" "+orgname);
//		if(errorString==null) {
			return "redirect:/organization";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "organization/createOrganization";
//		}
	}
	
	@GetMapping(value= "/editOrganization")
	public String editOrganizationPageGET(@RequestParam int orgid, Model model ) {
		
		Organization org = organizationDAO.getOrganization(orgid);
	    model.addAttribute("org",org);
	    
		return "organization/editOrganization";
	}
	
	@PostMapping(value= "/editOrganization")
	public String editOrganizationPagePOST(@RequestParam int orgid, @RequestParam int orgcode,
			@RequestParam String orgname, Model model ) {
		System.out.println(orgid+" "+orgcode+" "+orgname);
//		String errorString=organizationDAO.updateOrganization(orgid, orgcode, orgname);
//		if(errorString==null) {
		return "redirect:/organization";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "organization/editOrganization";
//		}
	}
	
	@GetMapping(value= "/deleteOrganization")
	public String deleteOrganization(@RequestParam int orgid, Model model ) {
		organizationDAO.deleteOrganization(orgid);
		
		return "redirect:/organization";
	}
	
	
	@GetMapping(value= "/createDepartment")
	public String createDepartmentGET(Model model ) {
		List<Organization> orgs= organizationDAO.getAllOrganization();

		model.addAttribute("errorString",null);
		model.addAttribute("orgs",orgs);
		
		return "organization/createDepartment";
	}
	
	@PostMapping(value= "/createDepartment")
	public String createDepartmentPOST(@RequestParam int orgid, @RequestParam int deptcode, @RequestParam String deptname, Model model ) {
//		String errorString=deptDAO.createDepartment(orgid, deptcode, deptname);
		System.out.println(orgid+" "+deptcode+" "+deptname);
//		if(errorString==null) {
			return "redirect:/organization";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "organization/createDepartment";
//		}
	}
	
	@GetMapping(value= "/editDepartment")
	public String editDepartmentPageGET(@RequestParam int deptid, Model model ) {
		
		Dept dept = deptDAO.getDept(deptid);
		Organization org=organizationDAO.getOrganization(dept.getOrgid());
	    model.addAttribute("dept",dept);
	    model.addAttribute("org",org);
	    
		return "organization/editDepartment";
	}
	
	@PostMapping(value= "/editDepartment")
	public String editDepartmentPagePOST(@RequestParam int deptid, @RequestParam int deptcode,
			@RequestParam String deptname, @RequestParam int orgid, Model model ) {
		System.out.println(orgid+" "+deptid+" "+deptcode+" "+deptname);
//		String errorString=deptDAO.updateDepartment(deptid, deptcode, deptname);
//		if(errorString==null) {
		return "redirect:/organization";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "organization/editDepartment";
//		}
	}
	
	@GetMapping(value= "/deleteDepartment")
	public String deleteDepartment(@RequestParam int deptid, Model model ) {
		System.out.println(deptid);
//		deptDAO.deleteDepartment(deptid);
		
		return "redirect:/organization";
	}
	
	
	@GetMapping(value= "/createSubDepartment")
	public String createSubDepartmentGET(Model model ) {
		List<Organization> orgs= organizationDAO.getAllOrganization();
		List<Dept> depts= deptDAO.getAllDept();

		model.addAttribute("errorString",null);
		model.addAttribute("orgs",orgs);
		model.addAttribute("depts",depts);
		
		return "organization/createSubDepartment";
	}
	
	@PostMapping(value= "/createSubDepartment")
	public String createSubDepartmentPOST(@RequestParam int deptid, @RequestParam int subdeptcode, @RequestParam String subdeptname, Model model ) {
//		String errorString=subDeptDAO.createSubDepartment(deptid, subdeptcode, subdeptname);
		System.out.println(deptid+" "+subdeptcode+" "+subdeptname);
//		if(errorString==null) {
			return "redirect:/organization";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "organization/createSubDepartment";
//		}
	}
	
	@GetMapping(value= "/editSubDepartment")
	public String editSubDepartmentPageGET(@RequestParam int subdeptid, Model model ) {
		
		SubDept subdept = subDeptDAO.getSubDept(subdeptid);
		Dept dept=deptDAO.getDept(subdept.getDeptid());
		Organization org=organizationDAO.getOrganization(dept.getOrgid());
	    model.addAttribute("subdept",subdept);
	    model.addAttribute("dept",dept);
	    model.addAttribute("org",org);
	    
		return "organization/editSubDepartment";
	}
	
	@PostMapping(value= "/editSubDepartment")
	public String editSubDepartmentPagePOST(@RequestParam int subdeptid, @RequestParam int subdeptcode,
			@RequestParam String subdeptname, @RequestParam int deptid, Model model ) {
		System.out.println(deptid+" "+subdeptid+" "+subdeptcode+" "+subdeptname);
//		String errorString=subDeptDAO.updateSubDepartment(subdeptid, subdeptcode, subdeptname);
//		if(errorString==null) {
		return "redirect:/organization";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "organization/editSubDepartment";
//		}
	}
	
	@GetMapping(value= "/deleteSubDepartment")
	public String deleteSubDepartment(@RequestParam int subdeptid, Model model ) {
		System.out.println(subdeptid);
//		subDeptDAO.deleteDepartment(subdeptid);
		
		return "redirect:/organization";
	}
	
	//********* LOCATION *********//
	
	@GetMapping(value= "/location")
	public String locationPage(Model model, Principal principal) {
		List<MainLoc> mainlocs=mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs",mainlocs);
		
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		UserAccount useracc = userAccountDAO.getUserName(loginedUser.getUsername());
		System.out.println("User ID: "+useracc.getUserid());
			
		List<SubLoc> sublocs=subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs",sublocs);
			
		return "location/locationPage";
	}
		
	@GetMapping(value= "/createMainLoc")
	public String createMainLocGET(Model model ) {
		model.addAttribute("errorString",null);
			
		return "location/createMainLoc";
	}
		
	@PostMapping(value= "/createMainLoc")
	public String createMainLocPOST(@RequestParam String mainlocname, Model model ) {
//		String errorString=mainLocDAO.createMainLoc(mainlocname);
//		if(errorString==null) {
			return "redirect:/location";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "location/createMainLoc";
//		}
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
//		System.out.println(orgid+" "+deptid+" "+deptcode+" "+deptname);
//		String errorString=mainLocDAO.updateMainLoc(mainlocid, mainlocname);
//		if(errorString==null) {
			return "redirect:/location";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "location/editMainLoc";
//		}
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
//		String errorString=subLocDAO.createSubLoc(sublocname, mainlocid);
//		System.out.println(deptid+" "+subdeptcode+" "+subdeptname);
//		if(errorString==null) {
			return "redirect:/location";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "location/createSubLoc";
//		}
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
//		System.out.println(deptid+" "+subdeptid+" "+subdeptcode+" "+subdeptname);
//		String errorString=subLocDAO.updateSubLoc(sublocid, sublocname);
//		if(errorString==null) {
			return "redirect:/location";
//		} else {
//			model.addAttribute("errorString",errorString);
//			return "location/editSubLoc";
//		}
	}
		
	@GetMapping(value= "/deleteSubLoc")
	public String deleteSubloc(@RequestParam int sublocid, Model model ) {
		System.out.println(sublocid);
//		subDeptDAO.deleteDepartment(subdeptid);
			
		return "redirect:/location";
	}
	
	//********* STOCK *********//
	@GetMapping(value= "/stock")
//	, int stocktypeid, int reasonid
	public String stockPage(Model model, String startdate, String enddate, Integer stocktypeid, Integer reasonid) {
		List<StockHistory> stockhistories;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ZoneId sg = ZoneId.of("Asia/Singapore");
		String endDate = formatter.format(ZonedDateTime.now(sg));
		String startDate = formatter.format(ZonedDateTime.now(sg).minusDays(7));
		
		List<Reason> reasons = reasonDAO.getAllReason();
		if((startdate==null)&&(enddate==null)&&(stocktypeid==null)&&(reasonid==null)) {
//			stockhistories = stockHistoryDAO.getAllStockHistory();
			stockhistories = stockHistoryDAO.getAllStockHistory(startDate, endDate, 0, 0);
			
			model.addAttribute("startdate", startDate);
			model.addAttribute("enddate", endDate);
		} else {
			stockhistories = stockHistoryDAO.getAllStockHistory(startdate, enddate, stocktypeid, reasonid);
			
			model.addAttribute("startdate", startdate);
			model.addAttribute("enddate", enddate);
		}
		List<Product> products = productDAO.getAllProduct();
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		
		model.addAttribute("reasons", reasons);
		model.addAttribute("stockhistories", stockhistories);
		model.addAttribute("products", products);
		model.addAttribute("stocktypes", stocktypes);
		
		
		
		List<Hardware> hardwares = hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		
		model.addAttribute("hardwares",hardwares);
		model.addAttribute("brands",brands);

		return "stock/stockPage";
	}
	
	@GetMapping(value= "/createReason")
	public String createReasonGET(Model model ) {
	
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		model.addAttribute("errorString",null);
		model.addAttribute("stocktypes",stocktypes);
			
		return "stock/createReason";
	}
		
	@PostMapping(value= "/createReason")
	public String createReasonPOST(@RequestParam String reason, int stocktypeid, Model model ) {
		String errorString= reasonDAO.createReason(reason, stocktypeid);
		System.out.println(reason);
		if(errorString==null) {
			return "redirect:/stock";
		} else {
			model.addAttribute("errorString",errorString);
			return "stock/createReason";
		}
	}
	
	@GetMapping(value= "/editReason")
	public String editReasonPageGET(@RequestParam int reasonid, Model model ) {
			
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		Reason reason = reasonDAO.getReason(reasonid);
		    
		model.addAttribute("reason",reason);
		model.addAttribute("stocktypes",stocktypes);

		return "stock/editReason";
	}
		
	@PostMapping(value= "/editReason")
	public String editReasonPagePOST(@RequestParam int reasonid,
			@RequestParam String reason, @RequestParam int stocktypeid, Model model ) {
		System.out.println(reasonid+" "+reason);
		String errorString=reasonDAO.updateReason(reasonid, reason, stocktypeid);
		if(errorString==null) {
			return "redirect:/stock";
		} else {
			model.addAttribute("errorString",errorString);
			return "stock/editReason";
		}
	}
	
	@GetMapping(value= "/deleteReason")
	public String deleteReason(@RequestParam int reasonid, Model model ) {
		System.out.println(reasonid);
//		reasonDAO.deleteReason(reasonid);
			
		return "redirect:/stock";
	}
	
	@GetMapping(value= "/stockIn")
	public String stockInGET(@RequestParam int stocktypeid, Model model) {
		
		List<Product> products = productDAO.getAllProduct();
		List<Reason> reasons = reasonDAO.getReasonStockInAndOut(stocktypeid);
		model.addAttribute("products",products);
		model.addAttribute("reasons",reasons);
		model.addAttribute("errorString",null);
		return "stock/stockIn";
	}
	
	@PostMapping(value= "/stockIn")
	public String stockInPOST(@RequestParam int productid, @RequestParam int quantity,  @RequestParam int stocktypeid,
			@RequestParam String date, @RequestParam String time, @RequestParam int reasonid, @RequestParam String reasondesc,
			Model model, Principal principal) {
		
//		if(principal!=null){
			String loguser = principal.getName();
//		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId sg = ZoneId.of("Asia/Singapore");
		String logdatetime = formatter.format(ZonedDateTime.now(sg));
//		System.out.println("Current time in singapore: "+logdatetime);
		
//		String historydate, historytime;
		
		try {
			SimpleDateFormat  dateformatter = new SimpleDateFormat ("yyyy-MM-dd");
			date = dateformatter.format(dateformatter.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			time=time.concat(":00");
			SimpleDateFormat  timeformatter = new SimpleDateFormat ("HH:mm:ss");
			time = timeformatter.format(timeformatter.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String errorString= stockHistoryDAO.createStockHistory(productid, quantity, date, time, stocktypeid, reasonid, reasondesc, logdatetime, loguser);
		if(errorString==null) {
			return "redirect:/stock";
		} else {
			model.addAttribute("errorString",errorString);
			return "stock/stockIn";
		}
	}
	
	@GetMapping(value= "/stockOut")
	public String stockOutGET(@RequestParam int stocktypeid, Model model) {
		
		List<Product> products = productDAO.getAllProduct();
		List<Reason> reasons = reasonDAO.getReasonStockInAndOut(stocktypeid);
		
		model.addAttribute("products",products);
		model.addAttribute("reasons",reasons);
		model.addAttribute("errorString",null);
		return "stock/stockOut";
	}
	
	@PostMapping(value= "/stockOut")
	public String stockOutPOST(@RequestParam int productid, @RequestParam int quantity,  @RequestParam int stocktypeid,
			@RequestParam String date, @RequestParam String time, @RequestParam int reasonid, @RequestParam String reasondesc, 
			Model model, Principal principal) {
		
		String loguser = principal.getName();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId sg = ZoneId.of("Asia/Singapore");
		String logdatetime = formatter.format(ZonedDateTime.now(sg));
//		System.out.println("Current time in singapore: "+logdatetime);
		
//		String historydate, historytime;
		
		try {
			SimpleDateFormat  dateformatter = new SimpleDateFormat ("yyyy-MM-dd");
			date = dateformatter.format(dateformatter.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			time=time.concat(":00");
			SimpleDateFormat  timeformatter = new SimpleDateFormat ("HH:mm:ss");
			time = timeformatter.format(timeformatter.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String errorString= stockHistoryDAO.createStockHistory(productid, quantity, date, time, stocktypeid, reasonid, reasondesc, logdatetime, loguser);
		if(errorString==null) {
			return "redirect:/stock";
		} else {
			model.addAttribute("errorString",errorString);
			return "stock/stockOut";
		}
	}
	
	@GetMapping(value= "/editStockHistory")
	public String editStockHistoryGET(@RequestParam int stockhistoryid, Model model) {
		
		StockHistory stockhistory= stockHistoryDAO.getStockHistory(stockhistoryid);
		List<Product> products = productDAO.getAllProduct();
		List<Reason> reasons = reasonDAO.getAllReason();
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		
		model.addAttribute("products",products);
		model.addAttribute("reasons",reasons);
		model.addAttribute("stocktypes",stocktypes);
		model.addAttribute("stockhistory",stockhistory);
		model.addAttribute("errorString",null);
		
		return "stock/editStockHistory";
	}
	
	@PostMapping(value= "/editStockHistory")
	public String editStockHistoryPOST(@RequestParam int stockhistoryid, @RequestParam int productid, @RequestParam int quantity,  @RequestParam int stocktypeid,
			@RequestParam String date, @RequestParam String time, @RequestParam int reasonid, @RequestParam String reasondesc, Model model) {
		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		ZoneId sg = ZoneId.of("Asia/Singapore");
//		String datetime = formatter.format(ZonedDateTime.now(sg));
//		System.out.println("Current time in singapore: "+datetime);
		
//		String historydate, historytime;
		
		try {
			SimpleDateFormat  dateformatter = new SimpleDateFormat ("yyyy-MM-dd");
			date = dateformatter.format(dateformatter.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			time=time.concat(":00");
			SimpleDateFormat  timeformatter = new SimpleDateFormat ("HH:mm:ss");
			time = timeformatter.format(timeformatter.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String errorString= stockHistoryDAO.updateStockHistory(stockhistoryid, productid, quantity, date, time, stocktypeid, reasonid, reasondesc);
		if(errorString==null) {
			return "redirect:/stock";
		} else {
			model.addAttribute("errorString",errorString);
			return "stock/editStockHistory";
		}
	}
	
	@GetMapping(value= "/deleteStockHistory")
	public String deleteStockHistory(@RequestParam int stockhistoryid, Model model ) {
		System.out.println(stockhistoryid);
//		stockHistoryDAO.deleteStockHistory(stockhistoryid);
			
		return "redirect:/stock";
	}
}
