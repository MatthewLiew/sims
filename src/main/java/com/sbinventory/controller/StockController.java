package com.sbinventory.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import com.sbinventory.model.Reason;
import com.sbinventory.model.StockHistory;
import com.sbinventory.model.StockType;
import com.sbinventory.model.SubLoc;

@Controller
public class StockController {
	
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
	
	
	//********* STOCK *********//
	@GetMapping(value= "/stockmanagement")
//		, int stocktypeid, int reasonid
	public String getStockManagement(Model model, String startdate, String enddate, Integer stocktypeid, Integer reasonid) {
		List<StockHistory> stockhistories;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ZoneId sg = ZoneId.of("Asia/Singapore");
		String endDate = formatter.format(ZonedDateTime.now(sg));
		String startDate = formatter.format(ZonedDateTime.now(sg).minusDays(14));
		
		List<Reason> reasons = reasonDAO.getAllReason();
		if((startdate==null)&&(enddate==null)&&(stocktypeid==null)&&(reasonid==null)) {
//				stockhistories = stockHistoryDAO.getAllStockHistory();
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
		
		List<PartNo> partnos = partNoDAO.getAllPartNo();
		model.addAttribute("partnos",partnos);

		return "stock/stockManagement";
	}
	
	@GetMapping(value= "/stockmovement")
//		, int stocktypeid, int reasonid
//	public String stockMovementPage(Model model, String startdate, String enddate, Integer stocktypeid, Integer reasonid) {
	public String getStockMovement(Model model, 
			/*@RequestParam (required=false)*/ String startdate, 
			/*@RequestParam (required=false)*/ String enddate, 
			/*@RequestParam (required=false)*/ Integer stocktypeid, 
			/*@RequestParam (required=false)*/ Integer reasonid) {

//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		ZoneId sg = ZoneId.of("Asia/Singapore");
//		String endDate = formatter.format(ZonedDateTime.now(sg));
//		String startDate = formatter.format(ZonedDateTime.now(sg).minusDays(14));
		
		String endDate = ZonedDateTime.now(ZoneId.of("Asia/Singapore")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String startDate = ZonedDateTime.now(ZoneId.of("Asia/Singapore")).minusDays(24).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		List<Reason> reasons = reasonDAO.getAllReason();

		List<StockHistory>stockhistories = stockHistoryDAO.getAllStockHistory(
					startdate = startdate==null?startDate:startdate, 
					enddate = enddate==null?endDate:enddate, 
					stocktypeid==null? 0: stocktypeid, 
					reasonid==null ? 0: reasonid);
			
		List<Product> products = productDAO.getAllProduct();
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		List<Hardware> hardwares = hardwareDAO.getAllHardware();
		List<Brand> brands = brandDAO.getAllBrand();
		List<PartNo> partnos = partNoDAO.getAllPartNo();
		
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		model.addAttribute("reasons", reasons);
		model.addAttribute("stockhistories", stockhistories);
		model.addAttribute("products", products);
		model.addAttribute("stocktypes", stocktypes);
		model.addAttribute("hardwares",hardwares);
		model.addAttribute("brands",brands);
		model.addAttribute("partnos",partnos);
		
		return "stock/stockMovement";
	}
	
	@GetMapping(value= "/serialmanagement")
//		, int stocktypeid, int reasonid
	public String getSerialManagement(Model model, String startdate, String enddate, Integer stocktypeid, Integer reasonid) {
		List<StockHistory> stockhistories;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ZoneId sg = ZoneId.of("Asia/Singapore");
		String endDate = formatter.format(ZonedDateTime.now(sg));
		String startDate = formatter.format(ZonedDateTime.now(sg).minusDays(14));
		
		List<Reason> reasons = reasonDAO.getAllReason();
		if((startdate==null)&&(enddate==null)&&(stocktypeid==null)&&(reasonid==null)) {
//				stockhistories = stockHistoryDAO.getAllStockHistory();
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
		
		List<PartNo> partnos = partNoDAO.getAllPartNo();
		model.addAttribute("partnos",partnos);

		return "stock/serialManagement";
	}
	
	@GetMapping(value= "/settings")
//		, int stocktypeid, int reasonid
	public String getSettings(Model model, String startdate, String enddate, Integer stocktypeid, Integer reasonid) {
		List<StockHistory> stockhistories;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ZoneId sg = ZoneId.of("Asia/Singapore");
		String endDate = formatter.format(ZonedDateTime.now(sg));
		String startDate = formatter.format(ZonedDateTime.now(sg).minusDays(14));
		
		List<Reason> reasons = reasonDAO.getAllReason();
		if((startdate==null)&&(enddate==null)&&(stocktypeid==null)&&(reasonid==null)) {
//				stockhistories = stockHistoryDAO.getAllStockHistory();
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
		
		List<PartNo> partnos = partNoDAO.getAllPartNo();
		model.addAttribute("partnos",partnos);

		return "stock/settings";
	}
	
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
	
//	@GetMapping(value= "/deletePartNos")
//	public String getDeletePartNos(@RequestParam int partnoid, Model model ) {
//		partNoDAO.deletePartNo(partnoid);
//		
//		return "redirect:/stock";
//	}
	
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
	
//	@GetMapping(value= "/deleteStock")
//	public String getDeleteStock(@RequestParam int productid, Model model ) {
//		System.out.println(productid);
////			productDAO.deleteProduct(productid);
//		
//		return "redirect:/stockmanagement";
//	}
	
	@GetMapping(value= "/createReason")
	public String getCreateReason(Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		model.addAttribute("errorString",null);
		model.addAttribute("stocktypes",stocktypes);
			
		return "stock/createReason";
	}
		
	@PostMapping(value= "/createReason")
	public String postCreateReason(@RequestParam String reason, int stocktypeid, Model model, @RequestParam String referer ) {
//			String errorString= reasonDAO.createReason(reason, stocktypeid);
		System.out.println(reason);
//			if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/createReason";
//			}
	}
	
	@GetMapping(value= "/editReason")
	public String getEditReason(@RequestParam int reasonid, Model model ) {
			
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		Reason reason = reasonDAO.getReason(reasonid);
		    
		model.addAttribute("reason",reason);
		model.addAttribute("stocktypes",stocktypes);

		return "stock/editReason";
	}
		
	@PostMapping(value= "/editReason")
	public String postEditReason(@RequestParam int reasonid,
			@RequestParam String reason, @RequestParam int stocktypeid, Model model ) {
		System.out.println(reasonid+" "+reason);
		String errorString=reasonDAO.updateReason(reasonid, reason, stocktypeid);
		if(errorString==null) {
			return "redirect:/stockmanagement";
		} else {
			model.addAttribute("errorString",errorString);
			return "stock/editReason";
		}
	}
	
	@GetMapping(value= "/deleteReason")
	public String getDeleteReason(@RequestParam int reasonid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		Reason reason = reasonDAO.getReason(reasonid);
		StockType stocktype = stockTypeDAO.getStockType(reason.getStocktypeid());
		
		model.addAttribute("reason", reason);
		model.addAttribute("stocktype", stocktype);
		
		
		return "stock/deleteReason";
	}
	
	@PostMapping(value= "/deleteReason")
	public String postDeleteReason(@RequestParam int reasonid, Model model, @RequestParam String referer ) {
		System.out.println(reasonid);
//			reasonDAO.deleteReason(reasonid);
			
		return "redirect:"+referer;
	}
	
	@GetMapping(value= "/stockIn")
	public String getStockIn(@RequestParam int stocktypeid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		List<Reason> reasons = reasonDAO.getReasonStockInAndOut(stocktypeid);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("products",products);
		model.addAttribute("reasons",reasons);
		model.addAttribute("errorString",null);
		model.addAttribute("mainlocs",mainlocs);
		model.addAttribute("sublocs",sublocs);
		
		return "stock/stockIn";
	}
	
	@PostMapping(value= "/stockIn")
	public String postStockIn(@RequestParam int productid, @RequestParam int mainlocid, @RequestParam int sublocid, @RequestParam int quantity,  @RequestParam int stocktypeid,
			@RequestParam String date, @RequestParam String time, @RequestParam int reasonid, @RequestParam String remark,
			Model model, Principal principal, @RequestParam String referer) {
		
		System.out.println(mainlocid+" "+sublocid);
//			if(principal!=null){
//				String loguser = principal.getName();
//			}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId sg = ZoneId.of("Asia/Singapore");
		String logdatetime = formatter.format(ZonedDateTime.now(sg));
//			System.out.println("Current time in singapore: "+logdatetime);
		
//			String historydate, historytime;
		
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
		
		
//			String errorString= stockHistoryDAO.createStockHistory(productid, mainlocid, sublocid, quantity, date, time, stocktypeid, reasonid, remark, logdatetime, "pending");
		
//			int totalquantity=0;
//			List<StockHistory> stockquantity = stockHistoryDAO.getStockQuantity(productid);
//			for(StockHistory a: stockquantity) {
//				if(!(a.getApproval()==null)&&a.getApproval().equals(1)) {
//					if(a.getStocktypeid()==1) {
//						totalquantity+=a.getQuantity();
//					} else {
//						totalquantity-=a.getQuantity();
//					}
//				}
//			}
//			System.out.println("Total: "+totalquantity);
//			productDAO.updateQuantity(productid, totalquantity);
//			
//			if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/stockIn";
//			}
	}
	
	@GetMapping(value= "/stockOut")
	public String getStockOut(@RequestParam int stocktypeid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		List<Reason> reasons = reasonDAO.getReasonStockInAndOut(stocktypeid);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		
		model.addAttribute("products",products);
		model.addAttribute("reasons",reasons);
		model.addAttribute("errorString",null);
		model.addAttribute("mainlocs",mainlocs);
		model.addAttribute("sublocs",sublocs);
		
		return "stock/stockOut";
	}
	
	@PostMapping(value= "/stockOut")
	public String postStockOut(@RequestParam int productid, int mainlocid, int sublocid, @RequestParam int quantity,  @RequestParam int stocktypeid,
			@RequestParam String date, @RequestParam String time, @RequestParam int reasonid, @RequestParam String remark, 
			Model model, Principal principal, @RequestParam String referer) {
		System.out.println(mainlocid+" "+sublocid);
//			String loguser = principal.getName();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ZoneId sg = ZoneId.of("Asia/Singapore");
		String logdatetime = formatter.format(ZonedDateTime.now(sg));
//			System.out.println("Current time in singapore: "+logdatetime);
		
//			String historydate, historytime;
		
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
		
//			String errorString= stockHistoryDAO.createStockHistory(productid, mainlocid, sublocid, quantity, date, time, stocktypeid, reasonid, remark, logdatetime, "pending");
//			
//			int totalquantity=0;
//			List<StockHistory> stockquantity = stockHistoryDAO.getStockQuantity(productid);
//			for(StockHistory a: stockquantity) {
//				if(a.getApproval().equals(1)) {
//					if(a.getStocktypeid()==1) {
//						totalquantity+=a.getQuantity();
//					} else {
//						totalquantity-=a.getQuantity();
//					}
//				}
//			}
//			productDAO.updateQuantity(productid, totalquantity);
//			
//			if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/stockOut";
//			}
	}
	
	@GetMapping(value= "/approval")
	public String getApproval(@RequestParam int stockhistoryid, @RequestParam String approve, @RequestParam int productid, Model model) {
		
		stockHistoryDAO.approval(stockhistoryid, approve);
		
		int totalquantity=0;
		List<StockHistory> stockquantity = stockHistoryDAO.getStockQuantity(productid);
		for(StockHistory a: stockquantity) {
//				System.out.println(a.getApproval());
			if(a.getApproval().equalsIgnoreCase("approved")) {
//					System.out.println("1"+a.getStockhistoryid());
				if(a.getStocktypeid()==1) {
					totalquantity+=a.getQuantity();
				} else {
					totalquantity-=a.getQuantity();
				}
			}
		}
		System.out.println("Total: "+totalquantity);
		productDAO.updateQuantity(productid, totalquantity);
		
		
//			return "";C
		return "redirect:/stock";
	}
	
	@GetMapping(value= "/editStockHistory")
	public String getEditStockHistory(@RequestParam int stockhistoryid, Model model) {
		
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
	public String postEditStockHistory(@RequestParam int stockhistoryid, @RequestParam int productid, @RequestParam int quantity,  @RequestParam int stocktypeid,
			@RequestParam String date, @RequestParam String time, @RequestParam int reasonid, @RequestParam String reasondesc, Model model) {
		
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//			ZoneId sg = ZoneId.of("Asia/Singapore");
//			String datetime = formatter.format(ZonedDateTime.now(sg));
//			System.out.println("Current time in singapore: "+datetime);
		
//			String historydate, historytime;
		
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
			return "redirect:/stockmanagement";
		} else {
			model.addAttribute("errorString",errorString);
			return "stock/editStockHistory";
		}
	}
	
	@GetMapping(value= "/deleteStockHistory")
	public String getDeleteStockHistory(@RequestParam int stockhistoryid, Model model ) {
			System.out.println(stockhistoryid);
//			stockHistoryDAO.deleteStockHistory(stockhistoryid);
				
			return "redirect:/stockmanagement";
		}
}
