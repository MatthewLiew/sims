package com.sbinventory.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbinventory.dao.BrandDAO;
import com.sbinventory.dao.DeptDAO;
import com.sbinventory.dao.HardwareDAO;
import com.sbinventory.dao.MainLocDAO;
import com.sbinventory.dao.OrganizationDAO;
import com.sbinventory.dao.PartNoDAO;
import com.sbinventory.dao.ProductDAO;
import com.sbinventory.dao.StockHistoryDAO;
import com.sbinventory.dao.StockTypeDAO;
import com.sbinventory.dao.SubDeptDAO;
import com.sbinventory.dao.SubLocDAO;
//import com.sbinventory.dao.UserRoleDAO;
import com.sbinventory.model.Brand;
import com.sbinventory.model.Dept;
import com.sbinventory.model.Hardware;
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.Organization;
import com.sbinventory.model.PartNo;
import com.sbinventory.model.PartNoForm;
import com.sbinventory.model.Product;
import com.sbinventory.model.SubDept;
import com.sbinventory.model.SubLoc;
import com.sbinventory.utils.DateTime;
import com.sbinventory.utils.StockQuantity;

@Controller
public class ProductController {
	
	@Autowired
	private HardwareDAO hardwareDAO;
	
	@Autowired
	private BrandDAO brandDAO;
	
	@Autowired
	private PartNoDAO partNoDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private StockTypeDAO stockTypeDAO;
	
	@Autowired
	private MainLocDAO mainLocDAO;
	
	@Autowired
	private SubLocDAO subLocDAO;
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Autowired
	private SubDeptDAO subdeptDAO;
	
	@Autowired
	private StockHistoryDAO stockHistoryDAO;
	
	@Autowired
	private StockQuantity stockquantity;

	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/hardware")
	public String getHardware(Model model) {
		
		List<Hardware> hardwares = hardwareDAO.findAll();
		model.addAttribute("hardwares",hardwares);
		        
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
//        
		return "product/hardware";
	}
	
	@GetMapping(value= "/brand")
	public String getBrand(Model model) {

		List<Brand> brands = brandDAO.findAll();
		model.addAttribute("brands",brands);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
     
		return "product/brand";
	}
	
//	@GetMapping(value= "/partno")
//	public String getPartNo(Model model) {
//
//		List<PartNo> partnos = partNoDAO.findAll();
//		List<Product> products = productDAO.findAll();
//		
//		model.addAttribute("partnos",partnos);
//        model.addAttribute("products",products);
//        
//		return "product/partno";
//	}
	
	@GetMapping(value= "/product")
	public String getProduct(Model model) {
		List<Hardware> hardwares = hardwareDAO.findAll();
		List<Brand> brands = brandDAO.findAll();
		List<PartNo> partnos = partNoDAO.findAll();
		List<Product> products = productDAO.findAll();
		
		model.addAttribute("hardwares",hardwares);
		model.addAttribute("brands",brands);
		model.addAttribute("partnos",partnos);
        model.addAttribute("products",products);
        
        String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
        
		return "product/product";
	}
	
	/**************** HARDWARE ACTION ***********************/
	@GetMapping(value= "/hardware/createHardware")
	public String getCreateHardware(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		model.addAttribute("hardware",new Hardware());
			
		return "product/createHardware";
	}
	
	@PostMapping(value= "/hardware/createHardware")
	public String postCreateHardware(@ModelAttribute Hardware hardware, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString=hardwareDAO.create(hardware.getHardwarecode(), hardware.getHardwaretype());
		
		if(errorString==null) {
			String message="Hardware - "+ hardware.getHardwaretype() +" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/hardware/editHardware")
	public String getEditHardware(@RequestParam int hardwareid, Model model, HttpServletRequest request) {
			
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Hardware hardware = hardwareDAO.findOne(hardwareid);
		model.addAttribute("hardware",hardware);
		    
		return "product/editHardware";
	}	
	
	@PostMapping(value= "/hardware/editHardware")
	public String postEditHardware(@ModelAttribute Hardware hardware, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString=hardwareDAO.update(hardware.getHardwareid(), hardware.getHardwarecode(), hardware.getHardwaretype());
		
		if(errorString==null) {
			String message="Hardware - "+ hardware.getHardwaretype() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}

	@GetMapping(value= "/hardware/deleteHardware")
	public String getDeleteHardware(@RequestParam int hardwareid, Model model, HttpServletRequest request ) {

		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
	
		Hardware hardware = hardwareDAO.findOne(hardwareid);
		model.addAttribute("hardware", hardware);

		return "product/deleteHardware";
	}
	
	@PostMapping(value= "/hardware/deleteHardware")
	public String postDeleteHardware(@ModelAttribute Hardware hardware, Model model, @RequestParam String sourceURL, RedirectAttributes ra) { 

		String errorString = hardwareDAO.delete(hardware.getHardwareid());
		
		if(errorString==null) {
			String message="Hardware - "+ hardware.getHardwaretype() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** BRAND ACTION ***********************/
	@GetMapping(value= "/brand/createBrand")
	public String getCreateBrand(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		model.addAttribute("brand",new Brand());
			
		return "product/createBrand";
	}
	
	@PostMapping(value= "/brand/createBrand")
	public String postCreateBrand(@ModelAttribute Brand brand, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		String errorString= brandDAO.create(brand.getBrandcode(), brand.getBrandname());
		
		if(errorString==null) {
			String message = "Brand - "+ brand.getBrandname() +" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/brand/editBrand")
	public String getEditBrand(@RequestParam int brandid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Brand brand = brandDAO.findOne(brandid);
		model.addAttribute("brand",brand);
		    
		return "product/editBrand";
	}
	
	@PostMapping(value= "/brand/editBrand")
	public String postEditBrand(@ModelAttribute Brand brand, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString=brandDAO.update(brand.getBrandid(), brand.getBrandcode(), brand.getBrandname());
		
		if(errorString==null) {
			String message="Brand - "+  brand.getBrandname() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/brand/deleteBrand")
	public String getDeleteBrand(@RequestParam int brandid, Model model, HttpServletRequest request ) {

		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Brand brand = brandDAO.findOne(brandid);
		model.addAttribute("brand", brand);

		return "product/deleteBrand";
	}
	
	@PostMapping(value= "/brand/deleteBrand")
	public String postDeleteBrand(@ModelAttribute Brand brand, Model model, @RequestParam String sourceURL, RedirectAttributes ra) { 

		String errorString = brandDAO.delete(brand.getBrandid());
		
		if(errorString==null) {
			String message="Hardware - "+ brand.getBrandname() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** PART NO ACTION ***********************/
	@GetMapping(value= "/createPartNo")
	public String getCreatePartNo(@RequestParam(defaultValue="0") int productid, Model model, HttpServletRequest request) {
		
		stockquantity.update();
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("index", productid);
		model.addAttribute("products", products);
		
		List<Organization> orgs = organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs",sublocs);
		
		PartNo partno = new PartNo();
		partno.setProductid(productid);
		
//		List<PartNo> partnos = new ArrayList<PartNo>();
//		partnos.add(partNo);
//		
//		PartNoForm partnoform  = new PartNoForm();
//		partnoform.setPartnos(partnos);
		
		model.addAttribute("partno",partno);
		
		return "product/createPartNo";
	}
	
	@PostMapping(value= "/createPartNo")
	public String postCreatePartNo(@RequestParam (required=false) int[] productid, @RequestParam (required=false) int[] orgid, @RequestParam (required=false) int[] deptid, 
			@RequestParam (required=false) int[] subdeptid, @RequestParam (required=false) int[] mainlocid, 
			@RequestParam (defaultValue="0") int[] sublocid,
			@RequestParam String[] serialno, @RequestParam (defaultValue=" ") String[] modelno, @RequestParam (defaultValue=" ") String[] upccode, 
			@RequestParam (defaultValue=" ") String[] customername, @RequestParam (defaultValue=" ") String[] invoiceno, 
			@RequestParam (required=false) int[] stockinQty, Model model, @RequestParam String sourceURL, 
			RedirectAttributes ra ) {
			
			
//	public String postCreatePartNos(@ModelAttribute PartNo partno, @RequestParam (required=false) int[] autoaddstock, Model model, 
//			@RequestParam String sourceURL, RedirectAttributes ra ) {

//		for(int i=0; i < partno.length; i++) {
//			System.out.println(partno[i].getSerialno());
//			System.out.println(partno[i].getModelno());
//			System.out.println(partno[i].getUpccode());
//			System.out.println(partno[i].getProductid());
//			System.out.println(partno[i].getCustomername());
//			System.out.println(partno[i].getInvoiceno());
//			System.out.println(partno[i].getMainlocid());
//			System.out.println(partno[i].getSublocid());
//		}
		
//		for(int i=0; i < serialno.length; i++) {
//			System.out.println(serialno[i]);
//			System.out.println(modelno[i]);
//			System.out.println(upccode[i]);
//			System.out.println(productid[i]);
//			System.out.println(customername[i]);
//			System.out.println(invoiceno[i]);
//			System.out.println(mainlocid[i]);
//			System.out.println(sublocid[i]);
//			System.out.println(autoaddstock[i]);
//		}
	
		/*System.out.println(partno.getPartnoid());
	  	for(int i=0; i < partno.length; i++) {
  		for(PartNo a: partno.getPartnos()) {
		
		StringTokenizer st = new StringTokenizer(a.getSerialno(), delims);
		while (st.hasMoreElements()) {
			errorString= partNoDAO.create(st.nextElement().toString().replaceAll("[^a-zA-Z0-9]", ""), modelno[i], upccode[i], 
					productid[i], customername[i], invoiceno[i], mainlocid[i], sublocid[i], orgid[i], deptid[i], subdeptid[i], "Available");
				System.out.println(i+" "+productid[i]+" "+modelno[i]+" Token: "+st.nextElement());
			System.out.println(a.getProductid()+" "+a.getModelno()+" Token: "+st.nextElement());
			System.out.println(i+" "+partno[i].getProductid()+" "+partno[i].getModelno()+" Token: "+st.nextElement());
		}*/
		
		String errorString=null;
		String delims = ", \r\n\t\f";
		
		for(int i=0; i < serialno.length; i++) {
		
			StringTokenizer st = new StringTokenizer(serialno[i], delims);
			while (st.hasMoreElements()) {
//				errorString= partNoDAO.create(st.nextElement().toString().replaceAll("[^a-zA-Z0-9]", ""), modelno[i], upccode[i], 
//						productid[i], customername[i], invoiceno[i], mainlocid[i], sublocid[i], orgid[i], deptid[i], subdeptid[i], "Available");
				System.out.println(i+" "+productid[i]+" "+stockinQty[i]+" Token: "+st.nextElement());
				
			}
			if(stockinQty[i]!=0 && stockinQty!=null) {
				System.out.println("Add Stock");
				String date = DateTime.DateNow();
				String time = DateTime.TimeNow();
				String logdatetime = DateTime.Now();
				int stocktypeid = 1;
				int reasonid= 1;
				String remark=" ";
//				errorString= stockHistoryDAO.create(productid[i], orgid[i], deptid[i], subdeptid[i], mainlocid[i], sublocid[i], stockinQty[i], date, time, stocktypeid, reasonid, remark, logdatetime, null, "approved");
			}
		}
		
		stockquantity.update();
		if(errorString==null) {
			String message = "Serial number added succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editPartNo")
	public String getEditPartNo(@RequestParam int partnoid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		PartNo partno = partNoDAO.findOne(partnoid);
		model.addAttribute("partno",partno);
		
		List<Organization> orgs= organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts= deptDAO.findAllByOrgid(partno.getOrgid());
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts = subdeptDAO.findAllByDeptid(partno.getDeptid());
		model.addAttribute("subdepts",subdepts);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAllByMainlocid(partno.getMainlocid());
		model.addAttribute("sublocs",sublocs);
		
		Product product = productDAO.findOne(partno.getProductid());
		model.addAttribute("product",product);
		    
		return "product/editPartNo";
	}
	
	@PostMapping(value= "/editPartNo")
	public String postEditPartNo(@ModelAttribute PartNo partno, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString=partNoDAO.update(partno.getPartnoid(), partno.getSerialno(), partno.getModelno(), partno.getUpccode(), /*productid,*/ 
				partno.getCustomername(), partno.getInvoiceno(), partno.getMainlocid(), partno.getSublocid(), partno.getOrgid(), partno.getDeptid(),
				partno.getSubdeptid());
		
		if(errorString==null) {
			String message="Serial No - "+ partno.getSerialno() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/deletePartNo")
	public String getDeletePartNo(@RequestParam int partnoid, Model model, HttpServletRequest request ) {

		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		PartNo partno = partNoDAO.findOne(partnoid);
		model.addAttribute("partno", partno);
		
		return "product/deletePartNo";
	}
	
	@PostMapping(value= "/deletePartNo")
	public String postDeletePartNo(@ModelAttribute PartNo partno, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {

		String errorString = partNoDAO.delete(partno.getPartnoid());
		
		if(errorString==null) {
			String message="Serial No - "+ partno.getSerialno() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}

	/**************** PRODUCT ACTION ***********************/
	@GetMapping(value= "/createProduct")
	public String getCreateProduct(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Hardware> hardwares = hardwareDAO.findAll();
		model.addAttribute("hardwares",hardwares);
		
		List<Brand> brands = brandDAO.findAll();
		model.addAttribute("brands",brands);
		
		List<PartNo> partnos = partNoDAO.findAll();
		model.addAttribute("partnos",partnos);
		
		model.addAttribute("product",new Product());

		return "product/createProduct";
	}
	
	@PostMapping(value= "/createProduct")
	public String postCreateProduct(@ModelAttribute Product product, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString=productDAO.create(product.getProductcode(), product.getProductname(), product.getHardwareid(), 
				product.getBrandid(), product.getPartnoid(), product.getLbvalue());

		if(errorString==null) {
			String message = "Product - "+ product.getProductname() +" created succussfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editProduct")
	public String getEditProduct(@RequestParam int productid,Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
	    
		Product product= productDAO.findOne(productid);
		model.addAttribute("product",product);
		
		List<Hardware> hardwares=hardwareDAO.findAll();
		model.addAttribute("hardwares",hardwares);
		
		List<Brand> brands = brandDAO.findAll();
		model.addAttribute("brands",brands);
		
//		List<PartNo> partnos= partNoDAO.getAllPartNo();
//		model.addAttribute("partnos",partnos);
        
		return "product/editProduct";
	}
	
	@PostMapping(value= "/editProduct")
	public String postEditProduct(@ModelAttribute Product product, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString=productDAO.update(product.getProductid(), product.getProductcode(), product.getProductname(),
				product.getBrandid(), product.getHardwareid(), product.getLbvalue());
		
		if(errorString==null) {
			String message="Product - "+  product.getProductname() +" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/deleteProduct")
	public String getDeleteProduct(@RequestParam int productid, Model model, HttpServletRequest request ) {
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Product product= productDAO.findOne(productid);
		model.addAttribute("product", product);

		return "product/deleteProduct";
	}
	
	@PostMapping(value= "/deleteProduct")
	public String postDeleteProduct(@ModelAttribute Product product, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString = productDAO.delete(product.getProductid());
		
		if(errorString==null) {
			String message="Hardware - "+ product.getProductid() +" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
}
