package com.sbinventory.controller;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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
import com.sbinventory.model.Brand;
import com.sbinventory.model.Hardware;
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.PartNo;
import com.sbinventory.model.Product;
import com.sbinventory.model.StockType;
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
	private StockHistoryDAO stockHistoryDAO;
	
	@Autowired
	private StockQuantity stockquantity;

	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/hardware")
	public String getHardware(Model model) {
		List<Hardware> hardwares = hardwareDAO.getAllHardware();
//		List<Brand> brands = brandDAO.getAllBrand();
//		List<PartNo> partnos = partNoDAO.getAllPartNo();
//		List<Product> products = productDAO.getAllProduct();
		
		model.addAttribute("hardwares",hardwares);
//		model.addAttribute("brands",brands);
//		model.addAttribute("partnos",partnos);
//        model.addAttribute("products",products);
//        
		return "product/hardware";
	}
	
	@GetMapping(value= "/brand")
	public String getBrand(Model model) {
//		List<Hardware> hardwares = hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
//		List<PartNo> partnos = partNoDAO.getAllPartNo();
//		List<Product> products = productDAO.getAllProduct();
//		
//		model.addAttribute("hardwares",hardwares);
		model.addAttribute("brands",brands);
//		model.addAttribute("partnos",partnos);
//        model.addAttribute("products",products);
//        
		return "product/brand";
	}
	
	@GetMapping(value= "/partno")
	public String getPartNo(Model model) {
//		List<Hardware> hardwares = hardwareDAO.getAllHardware();
//		List<Brand> brands = brandDAO.getAllBrand();
		List<PartNo> partnos = partNoDAO.getAllPartNo();
		List<Product> products = productDAO.getAllProduct();
		
//		model.addAttribute("hardwares",hardwares);
//		model.addAttribute("brands",brands);
		model.addAttribute("partnos",partnos);
        model.addAttribute("products",products);
        
		return "product/partno";
	}
	
	@GetMapping(value= "/product")
	public String getProduct(Model model) {
		List<Hardware> hardwares = hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		List<PartNo> partnos = partNoDAO.getAllPartNo();
		List<Product> products = productDAO.getAllProduct();
//		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		
		model.addAttribute("hardwares",hardwares);
		model.addAttribute("brands",brands);
		model.addAttribute("partnos",partnos);
        model.addAttribute("products",products);
//        model.addAttribute("stocktypes",stocktypes);
        
		return "product/product";
	}
	
	/**************** HARDWARE ACTION ***********************/
	@GetMapping(value= "/createHardware")
	public String getCreateHardware(Model model ) {
		model.addAttribute("errorString",null);
			
		return "product/createHardware";
	}
	
	@PostMapping(value= "/createHardware")
	public String postCreateHardware(@RequestParam int hardwarecode, @RequestParam String hardwaretype, Model model ) {
//			String errorString=hardwareDAO.createHardware(hardwarecode, hardwaretype);
//			if(errorString==null) {
			return "redirect:/product";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/createHardware";
//			}
	}
	
	@GetMapping(value= "/editHardware")
	public String getEditHardware(@RequestParam int hardwareid, Model model ) {
			
		Hardware hardware = hardwareDAO.getHardware(hardwareid);
		model.addAttribute("hardware",hardware);
		    
		return "product/editHardware";
	}	
	
	@PostMapping(value= "/editHardware")
	public String postEditHardware(@RequestParam int hardwareid, @RequestParam int hardwarecode,
			@RequestParam String hardwaretype, Model model ) {
//			System.out.println(orgid+" "+deptid+" "+deptcode+" "+deptname);
//			String errorString=hardwareDAO.updateDepartment(hardwareid, hardwarecode, hardwaretype);
//			if(errorString==null) {
			return "redirect:/product";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/editHardware";
//			}
	}

	@GetMapping(value= "/deleteHardware")
	public String getDeleteHardware(@RequestParam int hardwareid, Model model, HttpServletRequest request ) {

		String referer = request.getHeader("Referer");
		Hardware hardware = hardwareDAO.getHardware(hardwareid);
		
		model.addAttribute("hardware", hardware);
		model.addAttribute("referer", referer);
		return "product/deleteHardware";
	}
	
	@PostMapping(value= "/deleteHardware")
	public String postDeleteHardware(@RequestParam int hardwareid, Model model, @RequestParam String referer ) { 
		System.out.println(hardwareid);
//			productDAO.deleteProduct(productid);
		
		return "redirect:"+referer;
	}
	
	/**************** BRAND ACTION ***********************/
	@GetMapping(value= "/createBrand")
	public String getCreateBrand(Model model ) {
		model.addAttribute("errorString",null);
			
		return "product/createBrand";
	}
	
	@PostMapping(value= "/createBrand")
	public String postCreateBrand(@RequestParam int brandcode, @RequestParam String brandname, Model model ) {
//			String errorString= brandDAO.createBrand(brandcode, brandname);
//			if(errorString==null) {
			return "redirect:/product";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/createBrand";
//			}
	}
	
	@GetMapping(value= "/editBrand")
	public String getEditBrand(@RequestParam int brandid, Model model ) {
			
		Brand brand = brandDAO.getBrand(brandid);
		model.addAttribute("brand",brand);
		    
		return "product/editBrand";
	}
	
	@PostMapping(value= "/editBrand")
	public String postEditBrand(@RequestParam int brandid, @RequestParam int brandcode,
			@RequestParam String brandname, Model model ) {
		System.out.println(brandid+" "+brandcode);
//			String errorString=brandDAO.updateBrand(brandid, brandcode, brandname);
//			if(errorString==null) {
			return "redirect:/product";
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/editBrand";
//			}
	}
	
	@GetMapping(value= "/deleteBrand")
	public String getDeleteBrand(@RequestParam int brandid, Model model, HttpServletRequest request ) {

		String referer = request.getHeader("Referer");
		Brand brand = brandDAO.getBrand(brandid);
		
		model.addAttribute("brand", brand);
		model.addAttribute("referer", referer);
		return "product/deleteBrand";
	}
	
	@PostMapping(value= "/deleteBrand")
	public String postDeleteBrand(@RequestParam int brandid, Model model, @RequestParam String referer ) { 
		System.out.println(brandid);
//			productDAO.deleteProduct(productid);
		
		return "redirect:"+referer;
	}
	
	/**************** PART NO ACTION ***********************/
	@GetMapping(value= "/createPartNo")
	public String getCreatePartNo(@RequestParam(defaultValue="0") int productid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		
		stockquantity.update();
		
		model.addAttribute("index", productid);
		model.addAttribute("errorString",null);
		model.addAttribute("products",products);
		model.addAttribute("mainlocs",mainlocs);
		model.addAttribute("sublocs",sublocs);
		
		return "product/createPartNo";
	}
	
	@PostMapping(value= "/createPartNo")
	public String postCreatePartNos(@RequestParam String[] serialno,
			@RequestParam (defaultValue=" ")String[] modelno, @RequestParam String[] upccode, int[] productid, 
			@RequestParam String customername, @RequestParam String invoiceno, @RequestParam int mainlocid, @RequestParam int sublocid, 
			@RequestParam int[] addstock, Model model, @RequestParam String referer ) {
//		String delims = ",";
		String delims = ", \r\n\t\f";
//			String splitString = "one,two,,three,four,,five";
		
//		System.out.println(serialno.length);
//		System.out.println("StringTokenizer Example: \n");
		for(int i=0; i < serialno.length; i++) {
			
//			StringTokenizer st = new StringTokenizer(serialno[i],delims);
//			while (st.hasMoreElements()) {
////				String errorString= partNoDAO.createPartNo(st.nextElement().toString().replaceAll("[^a-zA-Z0-9]", ""), modelno[i], upccode[i], productid[i], customername, invoiceno, mainlocid, sublocid, "Available");
//				System.out.println(productid[i]+" "+modelno[i]+" "+" Token: "+st.nextElement());
//			}
			if(addstock[i]!=0) {
				String date = DateTime.DateNow();
				String time = DateTime.TimeNow();
				String logdatetime = DateTime.Now();
				int stocktypeid = 1;
				int reasonid= 1;
				String remark=" ";
//				System.out.println("Im here");
				String errorString= stockHistoryDAO.createStockHistory(productid[i], mainlocid, sublocid, addstock[i], date, time, stocktypeid, reasonid, remark, logdatetime, null, "approved");
				
				
			}
		}
		stockquantity.update();
//			for(int b:productid){
//				System.out.println(b);
//			}
//			if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/createPartNo";
//			}
	}
	
	@GetMapping(value= "/editPartNo")
	public String getEditPartNos(/*@RequestParam*/ int partnoid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List <Product> products = productDAO.getAllProduct();
		PartNo partno = partNoDAO.getPartNo(partnoid);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc(partno.getMainlocid());
		Product product = productDAO.getProduct(partno.getProductid());
		
		model.addAttribute("partno",partno);
		model.addAttribute("products",products);
		model.addAttribute("mainlocs",mainlocs);
		model.addAttribute("sublocs",sublocs);
		model.addAttribute("product",product);
		    
		return "product/editPartNo";
	}
	
	@PostMapping(value= "/editPartNo")
	public String postEditPartNo(/*@RequestParam*/ int partnoid,
			/*@RequestParam*/ String serialno, /*@RequestParam*/ String modelno, /*@RequestParam*/ String upccode/*, @RequestParam int productid*/, 
			/*@RequestParam*/ String customername, /*@RequestParam*/ String invoiceno, int mainlocid, int sublocid, Model model, /*@RequestParam*/ String referer) {
			System.out.println(mainlocid+" "+sublocid);
			String errorString=partNoDAO.updatePartNo(partnoid, serialno, modelno, upccode, /*productid,*/ customername, invoiceno, mainlocid, sublocid);
//			if(errorString==null) {
			return "redirect:"+referer; 
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/editPartNo";
//			}
	}
	
	@GetMapping(value= "/deletePartNo")
	public String getDeletePartNo(@RequestParam int partnoid, Model model, HttpServletRequest request ) {
//			System.out.println(partnoid);
//			partNoDAO.deletePartNo(partnoid);
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		PartNo partno = partNoDAO.getPartNo(partnoid);
		model.addAttribute("partno", partno);
		
		return "product/deletePartNo";
	}
	
	@PostMapping(value= "/deletePartNo")
	public String postDeletePartNo(@RequestParam int partnoid, Model model, @RequestParam String referer) {
		System.out.println(partnoid);
//			partNoDAO.deletePartNo(partnoid);
		
		return "redirect:"+referer;
	}

	
	/**************** PRODUCT ACTION ***********************/
	@GetMapping(value= "/createProduct")
	public String getCreateProduct(Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
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
	public String postCreateProduct(/*@RequestParam int productcode, @RequestParam String productname, @RequestParam int hardwareid,
			@RequestParam int brandid, @RequestParam int partnoid, @RequestParam int lbvalue,*/ Model model, @RequestParam String referer ) {

//			String errorString=productDAO.createProduct(productcode, productname, hardwareid, brandid, partnoid, lbvalue);
//			System.out.println(productcode+" "+productname+" "+hardwareid+" "+brandid+" "+partnoid+" "+lbvalue);
//			if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/createProduct";
//			}
	}
	
	@GetMapping(value= "/editProduct")
	public String getEditProduct(@RequestParam int productid,Model model, HttpServletRequest request ) {
		
		String referer = request.getHeader("Referer");
//			System.out.println(referer);
//		    return "redirect:"+ referer;
	    
		Product product= productDAO.getProduct(productid);
		List<Hardware> hardwares=hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		List<PartNo> partnos= partNoDAO.getAllPartNo();

        model.addAttribute("product",product);
        model.addAttribute("hardwares",hardwares);
        model.addAttribute("brands",brands);
        model.addAttribute("partnos",partnos);
        model.addAttribute("errorString",null);
        model.addAttribute("referer", referer);
        
		return "product/editProduct";
	}
	
	@PostMapping(value= "/editProduct")
	public String postEditProduct(@RequestParam int productid, @RequestParam int productcode, @RequestParam String productname, 
			@RequestParam int brandid, @RequestParam int hardwareid, @RequestParam int lbvalue, 
			Model model, @RequestParam String referer) {

//			String errorString=productDAO.updateProduct(productid, productcode, productname);
//			if(errorString==null) {
//			return "redirect:/product";
		
//			String referer = request.getHeader("Referer");
//			System.out.println(referer);
	    return "redirect:"+ referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/editProduct";
//			}
	}
	
	@GetMapping(value= "/deleteProduct")
	public String getDeleteProduct(@RequestParam int productid, Model model, HttpServletRequest request ) {
		String referer = request.getHeader("Referer");
		Product product= productDAO.getProduct(productid);
		
		model.addAttribute("product", product);
		model.addAttribute("referer", referer);
		return "product/deleteProduct";
	}
	
	@PostMapping(value= "/deleteProduct")
	public String postDeleteProduct(@RequestParam int productid, Model model, @RequestParam String referer ) {
		System.out.println(productid);
//			productDAO.deleteProduct(productid);
		
		return "redirect:"+referer;
	}
}
