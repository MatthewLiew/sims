package com.sbinventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	
	//******* USERACCOUNT ********//
	@PostMapping(value="/api/checkUserCode")
	public Response getUserCodeResponse(@RequestBody UserAccount user ) {

		UserAccount result;
		if(user.getUserid()==0){
			result= userAccountDAO.getUserCode(user.getUsercode());
		} else {
			result= userAccountDAO.getUserCode(user.getUsercode(),user.getUserid());
		}
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		} else {
			Response response = new Response("User Code Exist", "false");
			return response;
		}
	}	
	@PostMapping(value="/api/checkUserName")
	public Response getUserNameResponse(@RequestBody UserAccount user ) {
		
		UserAccount result;
		if(user.getUserid()==0) {
			result= userAccountDAO.getUserName(user.getUsername());
		} else {
			result= userAccountDAO.getUserName(user.getUsername(), user.getUserid());
		}
		if(result==null) {
			Response response = new Response("OK", "true");
			return response;
		}
		else {
			Response response = new Response("User Name Exist", "false");
			return response;
		}
		
	}
	
	//******* ORGANIZATION ********//
	
	@PostMapping(value="/api/checkOrgCode")
	public Response getOrgCodeResponse(@RequestBody Organization org ) {
		Organization result;
		if(org.getOrgid()==0) {
			result = organizationDAO.getOrganizationCode(org.getOrgcode());
		} else {
			result = organizationDAO.getOrganizationCode(org.getOrgcode(), org.getOrgid());
		}
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
		Organization result;
		if(org.getOrgid()==0) {
			result= organizationDAO.getOrganizationName(org.getOrgname());
		} else {
			result= organizationDAO.getOrganizationName(org.getOrgname(), org.getOrgid());
		}
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
		Dept result;
		if(dept.getDeptid()==0) {
			result= deptDAO.getDeptCode(dept.getDeptcode());
		} else {
			result= deptDAO.getDeptCode(dept.getDeptcode(), dept.getDeptid());
		}
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
		Dept result;
		if(dept.getDeptid()==0) {
			result= deptDAO.getDeptName(dept.getDeptname());
		} else {
			result= deptDAO.getDeptName(dept.getDeptname(), dept.getDeptid());
		}
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
		SubDept result;
		if(subdept.getSubdeptid()==0) {
			result= subDeptDAO.getSubDeptCode(subdept.getSubdeptcode());
		} else {
			result= subDeptDAO.getSubDeptCode(subdept.getSubdeptcode(), subdept.getSubdeptid());
		}
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
		SubDept result;
		if(subdept.getSubdeptid()==0) {
			result= subDeptDAO.getSubDeptName(subdept.getSubdeptname());
		} else {
			result= subDeptDAO.getSubDeptName(subdept.getSubdeptname(), subdept.getSubdeptid());
		}
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
		MainLoc result;
		if(mainloc.getMainlocid()==0) {
			result= mainLocDAO.getMainLocName(mainloc.getMainlocname());
		} else {
			result= mainLocDAO.getMainLocName(mainloc.getMainlocname(),mainloc.getMainlocid());
		}
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
		SubLoc result;
		if(subloc.getSublocid()==0) {
			result= subLocDAO.getSubLocName(subloc.getSublocname());
		} else {
			result= subLocDAO.getSubLocName(subloc.getSublocname(), subloc.getSublocid());
		}
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
		Hardware result;
		if(hardware.getHardwareid()==0) {
			result = hardwareDAO.getHardwareCode(hardware.getHardwarecode());
		} else {
			result = hardwareDAO.getHardwareCode(hardware.getHardwarecode(), hardware.getHardwareid());
		}
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
		Hardware result;
		if(hardware.getHardwareid()==0) {
			result= hardwareDAO.getHardwareType(hardware.getHardwaretype());
		} else {
			result= hardwareDAO.getHardwareType(hardware.getHardwaretype(), hardware.getHardwareid());
		}
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
		Brand result;
		if(brand.getBrandid()==0) {
			result = brandDAO.getBrandCode(brand.getBrandcode());
		} else {
			result = brandDAO.getBrandCode(brand.getBrandcode(), brand.getBrandid());
		}
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
		Brand result;
		if(brand.getBrandid()==0) {
			result= brandDAO.getBrandName(brand.getBrandname());
		} else {
			result= brandDAO.getBrandName(brand.getBrandname(), brand.getBrandid());
		}
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
		PartNo result;
		if(partno.getPartnoid()==0) {
			result= partNoDAO.getModelNo(partno.getModelno());
		} else {
			result= partNoDAO.getModelNo(partno.getModelno(), partno.getPartnoid());
		}
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
		PartNo result;
		if(partno.getPartnoid()==0) {
			result = partNoDAO.getSerialNo(partno.getSerialno());
		} else {
			result = partNoDAO.getSerialNo(partno.getSerialno(), partno.getPartnoid());
		}
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
		PartNo result;
		if(partno.getPartnoid()==0) {
			result= partNoDAO.getUpcCode(partno.getUpccode());
		} else {
			result= partNoDAO.getUpcCode(partno.getUpccode(), partno.getPartnoid());
		}
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
		Product result;
		if(product.getProductid()==0) {
			result= productDAO.getProductCode(product.getProductcode());
		} else {
			result= productDAO.getProductCode(product.getProductcode(), product.getProductid());
		}
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
		Product result;
		if(product.getProductid()==0) {
			result = productDAO.getProductName(product.getProductname());
		} else {
			result = productDAO.getProductName(product.getProductname(), product.getProductid());
		}
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
		Product result;
		if(product.getProductid()==0) {
			result= productDAO.getLBValue(product.getLbvalue());
		} else {
			result= productDAO.getLBValue(product.getLbvalue(), product.getProductid());
		}
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
		List<Dept> depts=deptDAO.getAllDept(dept.getOrgid());
		
		return depts;
	}
	
	@PostMapping(value="/api/getSubDept")
	public List<SubDept> getSubDept(@RequestBody SubDept subdept) {
		List<SubDept> subdepts=subDeptDAO.getAllSubDept(subdept.getDeptid());
		
		return subdepts;
	}
}
