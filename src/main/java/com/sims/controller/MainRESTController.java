package com.sims.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sims.dao.BrandDAO;
import com.sims.dao.DepartmentDAO;
import com.sims.dao.HardwareDAO;
import com.sims.dao.MainLocationDAO;
import com.sims.dao.OrganizationDAO;
import com.sims.dao.PartNoDAO;
import com.sims.dao.ProductDAO;
import com.sims.dao.ReasonDAO;
import com.sims.dao.SubDepartmentDAO;
import com.sims.dao.SubLocationDAO;
import com.sims.dao.UserAccountDAO;
import com.sims.message.Response;
import com.sims.model.Brand;
import com.sims.model.Department;
import com.sims.model.Hardware;
import com.sims.model.MainLocation;
import com.sims.model.Organization;
import com.sims.model.PartNo;
import com.sims.model.Product;
import com.sims.model.Reason;
import com.sims.model.SubDepartment;
import com.sims.model.SubLocation;
import com.sims.model.TransferHistory;
import com.sims.model.UserAccount;

@RestController
//@RequestMapping("/api")
public class MainRESTController {

	@Autowired
    private UserAccountDAO userAccountDAO;
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
    private DepartmentDAO deptDAO;
	
	@Autowired
    private SubDepartmentDAO subDeptDAO;
	
	@Autowired
    private MainLocationDAO mainLocDAO;

	@Autowired
    private SubLocationDAO subLocDAO;
	
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

	//******* USER ACCOUNT ********//
	@PostMapping(value="/api/checkUserName")
	public Response getUserNameResponse(@RequestBody UserAccount user ) {

		UserAccount result = userAccountDAO.findOneByUsername(user.getUsername(), user.getUserid());

		if(result==null) {
			Response response = new Response("Valid", "true");
			return response;
		} else {
			Response response = new Response("User Name Exist", "false");
			return response;
		}
	}
	
	//******* ORGANIZATION ********//
	
//	@PostMapping(value="/api/checkOrgCode")
//	public Response getOrgCodeResponse(@RequestBody Organization org ) {
//		Organization result = organizationDAO.getOrganizationCode(org.getOrgcode(), org.getOrgid());
//		
//		if(result==null) {
//			Response response = new Response("OK", "true");
//			return response;
//		}
//		else {
//			Response response = new Response("ORG Code Exist", "false");
//			return response;
//		}
//	}
	
	@PostMapping(value="/api/checkOrgName")
	public Response getOrgNameResponse(@RequestParam int id, @RequestParam String name ) {
		Organization result= organizationDAO.getOrganizationName(name, id);
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("ORG Name Exist", "false");
			return response;
		}
		
	}
	
//	@PostMapping(value="/api/checkDeptCode")
//	public Response getDeptCodeResponse(@RequestBody Department dept ) {
//		Department result= deptDAO.getDeptCode dept.getDeptid());
//		
//		if(result==null) {
//			Response response = new Response("OK", "true");
//			return response;
//		}
//		else {
//			Response response = new Response("Dept Code Exist", "false");
//			return response;
//		}
//	}
	
	@PostMapping(value="/api/checkDeptName")
	public Response getDeptNameResponse(@RequestParam int id, @RequestParam String name ) {
		Department result= deptDAO.getDeptName(name, id);
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Dept Name Exist", "false");
			return response;
		}
		
	}
	
	
	@PostMapping(value="/api/checkSubDeptName")
	public Response getSubDeptNameResponse(@RequestParam int id, @RequestParam String name ) {
		SubDepartment result= subDeptDAO.getSubDeptName(name, id);
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Dept Name Exist", "false");
			return response;
		}
		
	}
	
	//********** LOCATION **********//
	@PostMapping(value="/api/checkMainLocName")
	public Response getMainLocNameResponse(@RequestParam int id, @RequestParam String name ) {
		
		MainLocation result= mainLocDAO.getMainLocName(name, id);

		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Main Location Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/checkSubLocName")
	public Response getSubLocNameResponse(@RequestParam int id, @RequestParam String name) {
		SubLocation result= subLocDAO.getSubLocName(name, id);
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Sub Location Exist", "false");
			return response;
		}
		
	}
	
	//*********** PRODUCT ************//
	
	@PostMapping(value="/api/checkHardwareCode")
	public Response getHardwareCodeResponse(@RequestBody Hardware hardware ) {
		Hardware result = hardwareDAO.getHardwareCode(hardware.getHardwarecode(), hardware.getHardwareid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Hardware Code Exist", "false");
			return response;
		}
	}
	
	@PostMapping(value="/api/checkHardwareType")
	public Response getHardwareTypeResponse(@RequestBody Hardware hardware ) {
		Hardware result= hardwareDAO.getHardwareType(hardware.getHardwaretype(), hardware.getHardwareid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Hardware Type Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/checkBrandCode")
	public Response getBrandCodeResponse(@RequestBody Brand brand ) {
		Brand result = brandDAO.getBrandCode(brand.getBrandcode(), brand.getBrandid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Brand Code Exist", "false");
			return response;
		}
	}
	
	@PostMapping(value="/api/checkBrandName")
	public Response getBrandNameResponse(@RequestBody Brand brand ) {
		Brand result= brandDAO.getBrandName(brand.getBrandname(), brand.getBrandid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Brand Name Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/checkModelNo")
	public Response getModelNoResponse(@RequestBody PartNo partno ) {
		PartNo result= partNoDAO.getModelNo(partno.getModelno(), partno.getPartnoid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Model No Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/checkSerialNo")
	public Response getSerialNoResponse(@RequestBody PartNo partno ) {
		PartNo result = partNoDAO.getSerialNo(partno.getSerialno(), partno.getPartnoid());
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Serial No Exist", "false");
			return response;
		}
	}
	
	@PostMapping(value="/api/checkMultipleSerialNo")
	public Response checkMultipleSerialNo(/*@RequestBody PartNo th*/@RequestParam (value="data[]") String[] data, @RequestParam int productid, Principal principal) {
//		System.out.println("Productid"+productid);
		String message = "Serial No ";
		boolean flag = true;
//		UserAccount user = null;
//		if(principal != null) {
//			user = userAccountDAO.findOneByUsername(principal.getName(), 0);
//		}
		
		for(int i=0; i<data.length ; i++) {
			PartNo partno = partNoDAO.getSerialNoByProductid(data[i], productid);
			if(partno!=null) {
//				if(!((user.getOrgid()==partno.getOrgid())&&(user.getDeptid()==partno.getDeptid())&&(user.getSubdeptid()==partno.getSubdeptid()))) {
					message += data[i]+" ";
					flag=false;
//				} else {
//					message += "Serial No "+data[i]+" belongs to you.\n";
//				}
			} /*else {
				message += data[i]+" ";
				flag=false;
			}*/
		}
		message+="exist(s).";
		if(flag) {
			Response response = new Response(message, "true");
			return response;
		} else {
			Response response = new Response(message, "false");
			return response;
		}
	}
	
	@PostMapping(value="/api/checkUpcCode")
	public Response getUpcCodeResponse(@RequestBody PartNo partno ) {
		PartNo result= partNoDAO.getUpcCode(partno.getUpccode(), partno.getPartnoid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("UPC Code Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/checkProductCode")
	public Response getProductCodeResponse(@RequestBody Product product ) {
		Product result= productDAO.getProductCode(product.getProductcode(), product.getProductid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Product Code Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/checkProductName")
	public Response getProductNameResponse(@RequestBody Product product ) {
		Product result = productDAO.getProductName(product.getProductname(), product.getProductid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Product Name Exist", "false");
			return response;
		}
	}
	
	@PostMapping(value="/api/checkLBValue")
	public Response getLBValueResponse(@RequestBody Product product ) {
		Product result= productDAO.getLBValue(product.getLbvalue(), product.getProductid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Low Balance Value Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/checkReason")
	public Response getReasonResponse(@RequestBody Reason reason ) {
		Reason result;
		if(reason.getReasonid()==0) {
			result= reasonDAO.getReason(reason.getReason());
		} else {
			result= reasonDAO.getReason(reason.getReason(), reason.getReasonid());
		}
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Reason Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/getDept")
	public List<Department> getDept(@RequestBody Department dept) {
		List<Department> depts = deptDAO.findAllByOrgid(dept.getOrganizationId());
		
		return depts;
	}
	
	@PostMapping(value="/api/getSubDept")
	public List<SubDepartment> getSubDept(@RequestBody SubDepartment subdept) {
		List<SubDepartment> subdepts=subDeptDAO.findAllByDeptid(subdept.getDepartmentId());
		
		return subdepts;
	}
	
	@PostMapping(value="/api/getReason")
	public List<Reason> getReason(@RequestBody Reason reason) {
		List<Reason> reasons=reasonDAO.findAllByStocktype(reason.getStocktypeid());
		
		return reasons;
	}
	
	@PostMapping(value="/api/getSubLoc")
	public List<SubLocation> getSubLoc(@RequestBody SubLocation subloc) {
		List<SubLocation> sublocs=subLocDAO.findAllByMainlocid(subloc.getId());
		
		return sublocs;
	}
	
	@PostMapping(value="/api/checkSerialAvailability")
	public Response checkSerialAvailability(@RequestBody Product product) {
		Product productd = productDAO.findOne(product.getProductid());
//		System.out.println(productd.getQuantity());
		
		List<PartNo> partnos = partNoDAO.findAllByProductid(product.getProductid());
//		System.out.println(partnos.size());
//		List<SubLoc> sublocs=subLocDAO.getAllSubLoc(subloc.getMainlocid());
		int amount = (productd.getQuantity())-(partnos.size());
		
		Response response = new Response(Integer.toString(amount), " ");
		return response;
	}
	
	@PostMapping(value="/api/checkSerialAuth")
	public Response checkSerialAuth(/*@RequestBody PartNo th*/@RequestParam (value="data[]") String[] data, Principal principal) {

		boolean flag = true;
		UserAccount user = null;
		String status = "Available";
		if(principal != null) {
			user = userAccountDAO.findOneByUsername(principal.getName(), 0);
		}
		Response response = AccessRightVerification(user, data, status);
		return response;

	}
	
	private Response AccessRightVerification(UserAccount user, String[] serialno, String status) {
		
		int role = user.getRoleid();
		boolean flag = true;
		String accessible = "";
		String available = "";
		String message = "";
		for(int i = 0; i < serialno.length; i++) {
			PartNo partno = partNoDAO.findOneBySerialNo(serialno[i], status);
			
			if (partno != null) {
				boolean orglvl = user.getOrgid() == partno.getOrgid();
				boolean deptlvl = user.getDeptid() == partno.getDeptid();
				boolean subdeptlvl = user.getSubdeptid() == partno.getSubdeptid();
				
				if(role == 1) {
					
				} else if (role == 2) {
					if(!(orglvl)) 
					{
						accessible += serialno[i]+" ";
						flag = false;
					}
				} else if (role == 3) {
					if(!(orglvl && deptlvl)) {
						accessible += serialno[i]+" ";
						flag = false;
					}
				} else if (role == 4) {
					if(!(orglvl && deptlvl && subdeptlvl)) {
						accessible += serialno[i]+" ";
						flag = false;
					}
				}
			} else {
				available += serialno[i] + " ";
				flag = false;
			}
		}
		
		if (accessible != "" && available != ""){
			message = "Serial No " + accessible + "not accessible. Serial No " + available + "not available.";
		} else if (accessible != "") {
			message = "Serial No " + accessible + "not accessible.";
		} else if (available != "") {
			message = "Serial No " + available + "not available.";
		}
		
		if(flag) {
			Response response = new Response(message, "true");
			return response;
		} else {
			Response response = new Response(message, "false");
			return response;
		}
	}
}
