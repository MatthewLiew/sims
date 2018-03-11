package com.sbinventory.controller;

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
import com.sbinventory.model.PartNo;
import com.sbinventory.model.Product;

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

	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/product")
	public String getProduct(Model model) {
		List<Hardware> hardwares = hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		List<PartNo> partnos = partNoDAO.getAllPartNo();
		List<Product> products = productDAO.getAllProduct();
		
		model.addAttribute("hardwares",hardwares);
		model.addAttribute("brands",brands);
		model.addAttribute("partnos",partnos);
        model.addAttribute("products",products);
        
		return "product/index";
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
	public String getDeleteHardware(@RequestParam int hardwareid, Model model ) {
		System.out.println(hardwareid);
//			hardwareDAO.deleteDepartment(hardwareid);
		return "redirect:/product";
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
	public String getDeleteBrand(@RequestParam int brandid, Model model ) {
		System.out.println(brandid);
//			brandDAO.deleteBrand(brandid);
		
		return "redirect:/product";
	}
	
	/**************** PART NO ACTION ***********************/
	@GetMapping(value= "/createPartNos")
	public String getCreatePartNos(Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		
		model.addAttribute("errorString",null);
		model.addAttribute("products",products);
		
		return "product/createPartNos";
	}
	
	@PostMapping(value= "/createPartNos")
	public String postCreatePartNos(@RequestParam String[] serialno,
			@RequestParam String[] modelno, @RequestParam String[] upccode, int[] productid, 
			@RequestParam String customername, @RequestParam String invoiceno, Model model, @RequestParam String referer) {
		String delims = ", \r\n\t\f";
//			String splitString = "one,two,,three,four,,five";
 
		System.out.println("StringTokenizer Example: \n");
		for(int i=0;i<serialno.length; i++) {
			StringTokenizer st = new StringTokenizer(serialno[i],delims);
			while (st.hasMoreElements()) {
	//			String errorString= partNoDAO.createPartNo(st.nextElement().toString().replaceAll("[^a-zA-Z0-9]", ""), modelno, upccode, productid, customername, invoiceno);
				System.out.println(" Token: "+st.nextElement()+" "+productid[i]);
			}
		}
//			for(int b:productid){
//				System.out.println(b);
//			}
//			if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/createPartNos";
//			}
	}
	
	@GetMapping(value= "/deletePartNos")
	public String getDeletePartNos(@RequestParam int partnoid, Model model ) {
		partNoDAO.deletePartNo(partnoid);
		
		return "redirect:/stock";
	}
	
	@GetMapping(value= "/editPartNos")
	public String getEditPartNos(/*@RequestParam*/ int partnoid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List <Product> products = productDAO.getAllProduct();
		PartNo partno = partNoDAO.getPartNo(partnoid);
		
		model.addAttribute("partno",partno);
		model.addAttribute("products",products);
		    
		return "product/editPartNos";
	}
	
	@PostMapping(value= "/editPartNos")
	public String postEditPartNos(/*@RequestParam*/ int partnoid,
			/*@RequestParam*/ String serialno, /*@RequestParam*/ String modelno, /*@RequestParam*/ String upccode/*, @RequestParam int productid*/, 
			/*@RequestParam*/ String customername, /*@RequestParam*/ String invoiceno, Model model, /*@RequestParam*/ String referer) {

			String errorString=partNoDAO.updatePartNo(partnoid, serialno, modelno, upccode, /*productid,*/ customername, invoiceno);
//			if(errorString==null) {
			return "redirect:"+referer; 
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "product/editPartNos";
//			}
	}
	
	
	@GetMapping(value= "/deleteStock")
	public String getDeleteStock(@RequestParam int productid, Model model ) {
		System.out.println(productid);
//			productDAO.deleteProduct(productid);
		
		return "redirect:/stockmanagement";
	}
	
	@GetMapping(value= "/createPartNo")
	public String createPartNoGET(Model model ) {
		model.addAttribute("errorString",null);
			
		return "product/createPartNo";
	}
	
	
	@PostMapping(value= "/createPartNo")
	public String createPartNoPOST(@RequestParam String serialno,
			@RequestParam String modelno, @RequestParam String upccode, int productid,
			@RequestParam String customername, @RequestParam String invoiceno, Model model ) {
		String errorString= partNoDAO.createPartNo(serialno, modelno, upccode, productid, customername, invoiceno);
		if(errorString==null) {
			return "redirect:/product";
		} else {
			model.addAttribute("errorString",errorString);
			return "product/createPartNo";
		}
	}
	
	@GetMapping(value= "/editPartNo")
	public String getEditPartNo(@RequestParam int partnoid, Model model ) {
		
		PartNo partno = partNoDAO.getPartNo(partnoid);
		model.addAttribute("partno",partno);
		    
		return "product/editPartNo";
	}
	
	
	@PostMapping(value= "/editPartNo")
	public String postEditPartNo(@RequestParam int partnoid,
			@RequestParam String serialno, @RequestParam String modelno, @RequestParam String upccode, Model model ) {

//			String errorString=partNoDAO.updatePartNo(partnoid, serialno, modelno, upccode);
//			if(errorString==null) {
			return "redirect:/product"; 
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
