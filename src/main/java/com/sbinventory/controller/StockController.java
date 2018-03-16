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

import com.sbinventory.dao.BrandDAO;
import com.sbinventory.dao.HardwareDAO;
import com.sbinventory.dao.MainLocDAO;
import com.sbinventory.dao.PartNoDAO;
import com.sbinventory.dao.ProductDAO;
import com.sbinventory.dao.ReasonDAO;
import com.sbinventory.dao.StockHistoryDAO;
import com.sbinventory.dao.StockTypeDAO;
import com.sbinventory.dao.StorageDAO;
import com.sbinventory.dao.SubLocDAO;
import com.sbinventory.dao.TransferHistoryDAO;
import com.sbinventory.model.Brand;
import com.sbinventory.model.Hardware;
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.PartNo;
import com.sbinventory.model.Product;
import com.sbinventory.model.Reason;
import com.sbinventory.model.StockHistory;
import com.sbinventory.model.StockType;
import com.sbinventory.model.Storage;
import com.sbinventory.model.SubLoc;
import com.sbinventory.model.TransferHistory;
import com.sbinventory.utils.DateTime;
import com.sbinventory.utils.StockQuantity;

@Controller
public class StockController {
	
	@Autowired
	private StockQuantity stockquantity;
	
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
	
	@Autowired
	private StorageDAO storageDAO;
	
	@Autowired
	private TransferHistoryDAO transferHistoryDAO;
		
	/**************** INFORMATION PORTAL ***********************/
	@GetMapping(value= "/stockmanagement")
	public String getStockManagement(Model model) {
		
		stockquantity.update();
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		List<Hardware> hardwares = hardwareDAO.getAllHardware();
		model.addAttribute("hardwares",hardwares);
		List<Brand> brands = brandDAO.getAllBrand();
		model.addAttribute("brands",brands);
		
		return "stock/stockManagement";
	}
	
	@GetMapping(value= "/stockmovement")
	public String getStockMovement(Model model, 
								   @RequestParam (required=false) String startdate, 
								   @RequestParam (required=false) String enddate, 
								   @RequestParam (required=false) Integer stocktypeid, 
								   @RequestParam (required=false) Integer reasonid) {

//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		ZoneId sg = ZoneId.of("Asia/Singapore");
//		String endDate = formatter.format(ZonedDateTime.now(sg));
//		String startDate = formatter.format(ZonedDateTime.now(sg).minusDays(14));
		
		String preset_enddate = DateTime.DateNow();
		String preset_startdate = DateTime.DateOneMonthBefore();

		List<StockHistory>stockhistories = stockHistoryDAO.getAllStockHistory(
					startdate = startdate == null ? preset_startdate : startdate, 
					enddate = enddate == null ? preset_enddate : enddate, 
					stocktypeid == null ? 0 : stocktypeid, 
					reasonid == null ? 0 : reasonid);
		
		List<Reason> reasons = reasonDAO.getAllReason();	
		List<Product> products = productDAO.getAllProduct();
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		model.addAttribute("reasons", reasons);
		model.addAttribute("stockhistories", stockhistories);
		model.addAttribute("products", products);
		model.addAttribute("stocktypes", stocktypes);
		model.addAttribute("mainlocs",mainlocs);
		model.addAttribute("sublocs",sublocs);
		
		return "stock/stockMovement";
	}
	
	@GetMapping(value= "/serialmanagement")
	public String getSerialManagement(Model model) {

		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		List<PartNo> partnos = partNoDAO.getAllPartNo();
		model.addAttribute("partnos",partnos);

		return "stock/serialManagement";
	}
	
	@GetMapping(value= "/settings")
//		, int stocktypeid, int reasonid
	public String getSettings(Model model) {
		
		List<Reason> reasons = reasonDAO.getAllReason();
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		
		model.addAttribute("reasons", reasons);
		model.addAttribute("stocktypes", stocktypes);

		return "stock/settings";
	}
	
	@GetMapping(value= "/storage")
	public String getStorage(Model model) {
		
		stockquantity.update();

		List<Storage> storages = storageDAO.getAllStorage();
		model.addAttribute("storages", storages);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		
		return "stock/storage";
	}
	
	@GetMapping(value= "/transferhistory")
	public String getTransferHistory(Model model) {

		List<TransferHistory> transferhistories = transferHistoryDAO.getAllTransferHistory();
		model.addAttribute("transferhistories", transferhistories);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		
		return "stock/transferHistory";
	}
	
	/**************** REASON ACTION ***********************/
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
	
	/**************** STOCK ACTION ***********************/
	@GetMapping(value= "/stockIn")
	public String getStockIn(@RequestParam(defaultValue="0") int productid, @RequestParam(defaultValue="1") int stocktypeid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		List<Reason> reasons = reasonDAO.getReasonStockInAndOut(stocktypeid);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		
		model.addAttribute("index", productid);
		model.addAttribute("products",products);
		model.addAttribute("reasons",reasons);
		model.addAttribute("errorString",null);
		model.addAttribute("mainlocs",mainlocs);
		model.addAttribute("sublocs",sublocs);
		
		return "stock/stockIn";
	}
	
	@PostMapping(value= "/stockIn")
	public String postStockIn(@RequestParam int productid, @RequestParam int mainlocid, @RequestParam int sublocid, @RequestParam int quantity,  @RequestParam int stocktypeid,
			@RequestParam String date, @RequestParam String time, @RequestParam int reasonid, @RequestParam String remark, String serialno,
			Model model, Principal principal, @RequestParam String referer) {
		
		String delims = ", \r\n\t\f";

		System.out.println("StringTokenizer Example: \n");

			StringTokenizer st = new StringTokenizer(serialno,delims);
			while (st.hasMoreElements()) {
	//			String errorString= partNoDAO.createPartNo(st.nextElement().toString().replaceAll("[^a-zA-Z0-9]", ""), modelno[i], upccode[i], productid[i], customername, invoiceno, mainlocid, sublocid, "Available");
				System.out.println("Token: "+st.nextElement());
			}

//			if(principal!=null){
//				String loguser = principal.getName();
//			}
		
		String logdatetime = DateTime.Now();
		
		
//			String errorString= stockHistoryDAO.createStockHistory(productid, mainlocid, sublocid, quantity, date, time, stocktypeid, reasonid, remark, logdatetime, null, "pending");
		
//			
//			if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/stockIn";
//			}
	}
	
	@GetMapping(value= "/stockOut")
	public String getStockOut(@RequestParam(defaultValue="0") int productid, @RequestParam(defaultValue="2") int stocktypeid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		List<Reason> reasons = reasonDAO.getReasonStockInAndOut(stocktypeid);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		
		model.addAttribute("index",productid);
		model.addAttribute("products",products);
		model.addAttribute("reasons",reasons);
		model.addAttribute("errorString",null);
		model.addAttribute("mainlocs",mainlocs);
		model.addAttribute("sublocs",sublocs);
		
		return "stock/stockOut";
	}
	
	@PostMapping(value= "/stockOut")
	public String postStockOut(@RequestParam int productid, int mainlocid, int sublocid, @RequestParam int quantity,  @RequestParam int stocktypeid,
			@RequestParam String date, @RequestParam String time, @RequestParam int reasonid, @RequestParam String remark, String serialno,
			Model model, Principal principal, @RequestParam String referer) {

//			String loguser = principal.getName();
		
		String delims = ", \r\n\t\f";

		System.out.println("StringTokenizer Example: \n");

		StringTokenizer st = new StringTokenizer(serialno,delims);
		while (st.hasMoreElements()) {
//			String errorString= partNoDAO.createPartNo(st.nextElement().toString().replaceAll("[^a-zA-Z0-9]", ""), modelno[i], upccode[i], productid[i], customername, invoiceno, mainlocid, sublocid, "Available");
			System.out.println("Token: "+st.nextElement());
		}
			
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
	
	@GetMapping(value= "/stockioapproval")
	public String getStockIOApproval(@RequestParam int stockhistoryid, @RequestParam String approve,/*, @RequestParam int productid,*/ Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		stockHistoryDAO.approval(stockhistoryid, approve);
		
		return "redirect:"+referer;
	}
	
	@GetMapping(value= "/editStockHistory")
	public String getEditStockHistory(@RequestParam int stockhistoryid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		StockHistory stockhistory= stockHistoryDAO.getStockHistory(stockhistoryid);
		List<Product> products = productDAO.getAllProduct();
		List<Reason> reasons = reasonDAO.getAllReason();
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc(stockhistory.getMainlocid());
		
		model.addAttribute("products",products);
		model.addAttribute("reasons",reasons);
		model.addAttribute("stocktypes",stocktypes);
		model.addAttribute("stockhistory",stockhistory);
		model.addAttribute("mainlocs",mainlocs);
		model.addAttribute("sublocs",sublocs);
		model.addAttribute("errorString",null);
		
		return "stock/editStockHistory";
	}
	
	@PostMapping(value= "/editStockHistory")
	public String postEditStockHistory(@RequestParam int stockhistoryid, @RequestParam int productid, @RequestParam int quantity,  @RequestParam int stocktypeid,
			@RequestParam String date, @RequestParam String time, @RequestParam int reasonid, @RequestParam String remark, @RequestParam int mainlocid,
			@RequestParam int sublocid, Model model, @RequestParam String referer) {
		
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
		
		String errorString= stockHistoryDAO.updateStockHistory(stockhistoryid, productid, quantity, date, time, stocktypeid, reasonid, remark, mainlocid, sublocid);
		if(errorString==null) {
			return "redirect:"+referer;
		} else {
			model.addAttribute("errorString",errorString);
			return "stock/editStockHistory";
		}
	}
	
	@GetMapping(value= "/deleteStockHistory")
	public String getDeleteStockHistory(@RequestParam int stockhistoryid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		StockHistory stockhistory = stockHistoryDAO.getStockHistory(stockhistoryid);
		Product product = productDAO.getProduct(stockhistory.getProductid());
		
		model.addAttribute("stockhistory", stockhistory);		
		model.addAttribute("product", product);	
		
		return "stock/deleteStockHistory";
	}
	
	@PostMapping(value= "/deleteStockHistory")
	public String postDeleteStockHistory(@RequestParam int stockhistoryid, Model model, @RequestParam String referer ) {
		System.out.println(stockhistoryid);
//			reasonDAO.deleteReason(reasonid);
			
		return "redirect:"+referer;
	}
	
	/**************** TRANSFER ACTION ***********************/
	@GetMapping(value= "/transferStock")
	public String getTransferStock(Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
//		List<Storage> storages = storageDAO.getAllStorage();
//		model.addAttribute("storages", storages);
		
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("errorString",null);
			
		return "stock/transferStock";
	}
	
	@PostMapping(value= "/transferStock")
	public String postTransferStock(@RequestParam int productid,
			@RequestParam int orimainlocid,
			@RequestParam int orisublocid,
			@RequestParam int desmainlocid,
			@RequestParam int dessublocid,
			@RequestParam int quantity,  
			Model model,
			@RequestParam String referer) {
		
//		System.out.println(productid+" "+orimainlocid+" "+orisublocid+" "+desmainlocid+" "+dessublocid+" "+quantity);
		String errorString = transferHistoryDAO.createTransferHistory(null, DateTime.Now(), productid, quantity, orimainlocid, orisublocid, desmainlocid, dessublocid, "pending");		
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/stockIn";
//			}
//		}
	}
	
	@GetMapping(value= "/transferapproval")
	public String getApproval(@RequestParam int transferhistoryid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		transferHistoryDAO.approval(transferhistoryid, approve);
		
		return "redirect:"+referer;
	}
	@GetMapping(value= "/editTransferHistory")
	public String getEditTransferHistory(@RequestParam int transferhistoryid, Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		TransferHistory transferhistory= transferHistoryDAO.getTransferHistory(transferhistoryid);
		model.addAttribute("transferhistory", transferhistory);
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("errorString",null);
			
		return "stock/editTransferHistory";
	}
	
	@PostMapping(value= "/editTransferHistory")
	public String postEditTransferHistory(@RequestParam int transferhistoryid,
			@RequestParam int productid, 
			@RequestParam int orimainlocid,
			@RequestParam int orisublocid,
			@RequestParam int desmainlocid,
			@RequestParam int dessublocid,
			@RequestParam int quantity,  
			Model model,
			@RequestParam String referer) {
		
		String errorString = transferHistoryDAO.updateTransferHistory(transferhistoryid, productid, orimainlocid, orisublocid, desmainlocid, dessublocid, quantity);
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/editTransferHistory";
//			}
//		}
	}
	
	@GetMapping(value= "/deleteTransferHistory")
	public String getDeleteTransferHistory(@RequestParam int transferhistoryid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		TransferHistory transferhistory = transferHistoryDAO.getTransferHistory(transferhistoryid);
		Product product = productDAO.getProduct(transferhistory.getProductid());
		
		model.addAttribute("transferhistory", transferhistory);		
		model.addAttribute("product", product);	
		
		return "stock/deleteTransferHistory";
	}
	
	@PostMapping(value= "/deleteTransferHistory")
	public String postDeleteTransferHistory(@RequestParam int transferhistoryid, Model model, @RequestParam String referer ) {
		System.out.println(transferhistoryid);
//		transferHistoryDAO.deleteStockHistory(transferhistoryid);
			
		return "redirect:"+referer;
	}
}
