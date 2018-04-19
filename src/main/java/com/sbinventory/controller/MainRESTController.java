package com.sbinventory.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbinventory.dao.BrandDAO;
import com.sbinventory.dao.DeptDAO;
import com.sbinventory.dao.HardwareDAO;
import com.sbinventory.dao.MainLocDAO;
import com.sbinventory.dao.OrganizationDAO;
import com.sbinventory.dao.PartNoDAO;
import com.sbinventory.dao.ProductDAO;
import com.sbinventory.dao.ReasonDAO;
import com.sbinventory.dao.SubDeptDAO;
import com.sbinventory.dao.SubLocDAO;
import com.sbinventory.dao.UserAccountDAO;
import com.sbinventory.message.Response;

import com.sbinventory.model.SubDept;
import com.sbinventory.model.SubLoc;
import com.sbinventory.model.TransferHistory;
import com.sbinventory.model.Brand;
import com.sbinventory.model.Dept;
import com.sbinventory.model.Hardware;
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.Organization;
import com.sbinventory.model.PartNo;
import com.sbinventory.model.Product;
import com.sbinventory.model.Reason;
import com.sbinventory.model.UserAccount;

@RestController
//@RequestMapping("/api")
public class MainRESTController {

	@Autowired
    private UserAccountDAO userAccountDAO;
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
    private DeptDAO deptDAO;
	
	@Autowired
    private SubDeptDAO subDeptDAO;
	
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
	
	@PostMapping(value="/api/checkOrgCode")
	public Response getOrgCodeResponse(@RequestBody Organization org ) {
		Organization result = organizationDAO.getOrganizationCode(org.getOrgcode(), org.getOrgid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("ORG Code Exist", "false");
			return response;
		}
	}
	
	@PostMapping(value="/api/checkOrgName")
	public Response getOrgNameResponse(@RequestBody Organization org ) {
		Organization result= organizationDAO.getOrganizationName(org.getOrgname(), org.getOrgid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("ORG Name Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/checkDeptCode")
	public Response getDeptCodeResponse(@RequestBody Dept dept ) {
		Dept result= deptDAO.getDeptCode(dept.getDeptcode(), dept.getDeptid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Dept Code Exist", "false");
			return response;
		}
	}
	
	@PostMapping(value="/api/checkDeptName")
	public Response getDeptNameResponse(@RequestBody Dept dept ) {
		Dept result= deptDAO.getDeptName(dept.getDeptname(), dept.getDeptid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Dept Name Exist", "false");
			return response;
		}
		
	}
	
	@PostMapping(value="/api/checkSubDeptCode")
	public Response getSubDeptCodeResponse(@RequestBody SubDept subdept ) {
		SubDept result= subDeptDAO.getSubDeptCode(subdept.getSubdeptcode(), subdept.getSubdeptid());
		
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("Dept Code Exist", "false");
			return response;
		}
	}
	
	@PostMapping(value="/api/checkSubDeptName")
	public Response getSubDeptNameResponse(@RequestBody SubDept subdept ) {
		SubDept result= subDeptDAO.getSubDeptName(subdept.getSubdeptname(), subdept.getSubdeptid());
		
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
	public Response getMainLocNameResponse(@RequestBody MainLoc mainloc ) {
		MainLoc result= mainLocDAO.getMainLocName(mainloc.getMainlocname(),mainloc.getMainlocid());
		
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
	public Response getSubLocNameResponse(@RequestBody SubLoc subloc ) {
		SubLoc result= subLocDAO.getSubLocName(subloc.getSublocname(), subloc.getSublocid());
		
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
	
//	@PostMapping(value="/api/checkPartNoCode")
//	public Response getPartNoCodeResponse(@RequestBody PartNo partno ) {
//		PartNo result;
//		if(partno.getPartnoid()==0) {
//			result = partNoDAO.getPartNoCode(partno.getPartnocode());
//		} else {
//			result = partNoDAO.getPartNoCode(partno.getPartnocode(), partno.getPartnoid());
//		}
//		if(result==null) {
//			Response response = new Response("OK", "true");
//			return response;
//		}
//		else {
//			Response response = new Response("Part No Code Exist", "false");
//			return response;
//		}
//	}
	
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
	public List<Dept> getDept(@RequestBody Dept dept) {
		List<Dept> depts=deptDAO.findAllByOrgid(dept.getOrgid());
		
		return depts;
	}
	
	@PostMapping(value="/api/getSubDept")
	public List<SubDept> getSubDept(@RequestBody SubDept subdept) {
		List<SubDept> subdepts=subDeptDAO.findAllByDeptid(subdept.getDeptid());
		
		return subdepts;
	}
	
	@PostMapping(value="/api/getReason")
	public List<Reason> getReason(@RequestBody Reason reason) {
		List<Reason> reasons=reasonDAO.findAllByStocktype(reason.getStocktypeid());
		
		return reasons;
	}
	
	@PostMapping(value="/api/getSubLoc")
	public List<SubLoc> getSubLoc(@RequestBody SubLoc subloc) {
		List<SubLoc> sublocs=subLocDAO.findAllByMainlocid(subloc.getMainlocid());
		
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
		String message = "Serial No ";
		boolean flag = true;
		UserAccount user = null;
		if(principal != null) {
			user = userAccountDAO.findOneByUsername(principal.getName(), 0);
		}
		
		for(int i=0; i<data.length ; i++) {
			PartNo partno = partNoDAO.findOneBySerialNo(data[i]);
			if(partno!=null) {
				if(!((user.getOrgid()==partno.getOrgid())&&(user.getDeptid()==partno.getDeptid())&&(user.getSubdeptid()==partno.getSubdeptid()))) {
					message += data[i]+" ";
					flag=false;
				} else {
//					message += "Serial No "+data[i]+" belongs to you.\n";
				}
			} else {
				message += data[i]+" ";
				flag=false;
			}
		}
		message+="unaccessable.";
		if(flag) {
			Response response = new Response(message, "true");
			return response;
		} else {
			Response response = new Response(message, "false");
			return response;
		}
	}
}
