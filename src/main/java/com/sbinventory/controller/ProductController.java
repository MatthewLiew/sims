package com.sbinventory.controller;

import java.io.File;
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
import com.sbinventory.model.Organization;
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
	@GetMapping(value= "/createHardware")
	public String getCreateHardware(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		model.addAttribute("hardware",new Hardware());
			
		return "product/createHardware";
	}
	
	@PostMapping(value= "/createHardware")
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
	
	@GetMapping(value= "/editHardware")
	public String getEditHardware(@RequestParam int hardwareid, Model model, HttpServletRequest request) {
			
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Hardware hardware = hardwareDAO.findOne(hardwareid);
		model.addAttribute("hardware",hardware);
		    
		return "product/editHardware";
	}	
	
	@PostMapping(value= "/editHardware")
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

	@GetMapping(value= "/deleteHardware")
	public String getDeleteHardware(@RequestParam int hardwareid, Model model, HttpServletRequest request ) {

		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
	
		Hardware hardware = hardwareDAO.findOne(hardwareid);
		model.addAttribute("hardware", hardware);

		return "product/deleteHardware";
	}
	
	@PostMapping(value= "/deleteHardware")
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
	@GetMapping(value= "/createBrand")
	public String getCreateBrand(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		model.addAttribute("brand",new Brand());
			
		return "product/createBrand";
	}
	
	@PostMapping(value= "/createBrand")
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
	
	@GetMapping(value= "/editBrand")
	public String getEditBrand(@RequestParam int brandid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Brand brand = brandDAO.findOne(brandid);
		model.addAttribute("brand",brand);
		    
		return "product/editBrand";
	}
	
	@PostMapping(value= "/editBrand")
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
	
	@GetMapping(value= "/deleteBrand")
	public String getDeleteBrand(@RequestParam int brandid, Model model, HttpServletRequest request ) {

		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Brand brand = brandDAO.findOne(brandid);
		model.addAttribute("brand", brand);

		return "product/deleteBrand";
	}
	
	@PostMapping(value= "/deleteBrand")
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
		model.addAttribute("products",products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs",sublocs);
		
		PartNo partno = new PartNo();
		model.addAttribute("partno",partno);
		
		return "product/createPartNo";
	}
	
	@PostMapping(value= "/createPartNo")
	public String postCreatePartNos(@RequestParam String[] serialno, @RequestParam String[] modelno, @RequestParam String[] upccode, 
			int[] productid, @RequestParam String[] customername, @RequestParam String[] invoiceno, @RequestParam int[] mainlocid, 
			@RequestParam int[] sublocid, @RequestParam (required=false) int[] autoaddstock, Model model, @RequestParam String sourceURL, 
			RedirectAttributes ra ) {
//	public String postCreatePartNos(@ModelAttribute PartNo[] partno, @RequestParam int[] autoaddstock, Model model, 
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
	
		String errorString=null;
		String delims = ", \r\n\t\f";
		
		for(int i=0; i < serialno.length; i++) {
			
			StringTokenizer st = new StringTokenizer(serialno[i],delims);
			while (st.hasMoreElements()) {
				errorString= partNoDAO.create(st.nextElement().toString().replaceAll("[^a-zA-Z0-9]", ""), modelno[i], upccode[i], 
						productid[i], customername[i], invoiceno[i], mainlocid[i], sublocid[i], "Available");
//				System.out.println(productid[i]+" "+modelno[i]+" "+" Token: "+st.nextElement());
			}
		
			if(autoaddstock[i]!=0) {
				String date = DateTime.DateNow();
				String time = DateTime.TimeNow();
				String logdatetime = DateTime.Now();
				int stocktypeid = 1;
				int reasonid= 1;
				String remark=" ";
				errorString= stockHistoryDAO.create(productid[i], mainlocid[i], sublocid[i], autoaddstock[i], date, time, stocktypeid, reasonid, remark, logdatetime, null, "approved");
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
				partno.getCustomername(), partno.getInvoiceno(), partno.getMainlocid(), partno.getSublocid());
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
