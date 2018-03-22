package com.sbinventory.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sbinventory.dao.AssetReqsDAO;
import com.sbinventory.dao.BrandDAO;
import com.sbinventory.dao.DisposalHistoryDAO;
import com.sbinventory.dao.HardwareDAO;
import com.sbinventory.dao.ITFDAO;
import com.sbinventory.dao.MainLocDAO;
import com.sbinventory.dao.PartNoDAO;
import com.sbinventory.dao.ProductDAO;
import com.sbinventory.dao.RMADAO;
import com.sbinventory.dao.ReasonDAO;
import com.sbinventory.dao.StockHistoryDAO;
import com.sbinventory.dao.StockTypeDAO;
import com.sbinventory.dao.StorageDAO;
import com.sbinventory.dao.SubLocDAO;
import com.sbinventory.dao.TransferHistoryDAO;
import com.sbinventory.fileio.ReadCSVFileExample;
import com.sbinventory.model.AssetReqs;
import com.sbinventory.model.Brand;
import com.sbinventory.model.DisposalHistory;
import com.sbinventory.model.Hardware;
import com.sbinventory.model.ITF;
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.PartNo;
import com.sbinventory.model.Product;
import com.sbinventory.model.RMA;
import com.sbinventory.model.Reason;
import com.sbinventory.model.StockHistory;
import com.sbinventory.model.StockType;
import com.sbinventory.model.Storage;
import com.sbinventory.model.SubLoc;
import com.sbinventory.model.TransferHistory;
import com.sbinventory.model.UploadForm;
import com.sbinventory.utils.DateTime;
import com.sbinventory.utils.StockQuantity;
import com.sbinventory.validator.CustomFileValidator;

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
	
	@Autowired
	private DisposalHistoryDAO disposalHistoryDAO;
	
	@Autowired
	private RMADAO rmaDAO;
	
	@Autowired
	private ITFDAO itfDAO;
	
	@Autowired
	private AssetReqsDAO assetReqsDAO;
	
	@Autowired
	CustomFileValidator customFileValidator;
	
	private String doUpload(HttpServletRequest request, Model model, //
			   UploadForm myUploadForm, BindingResult bindingResult) {
	 
	      String description = myUploadForm.getDescription();
	      System.out.println("Description: " + description);
	 
	      // Root Directory.
	      String uploadRootPath = request.getServletContext().getRealPath("upload");
	      System.out.println("uploadRootPath=" + uploadRootPath);
	 
	      File uploadRootDir = new File(uploadRootPath);
	      // Create directory if it not exists.
	      if (!uploadRootDir.exists()) {
	         uploadRootDir.mkdirs();
	      }
	      MultipartFile[] fileDatas = myUploadForm.getFileDatas();
	      
	      customFileValidator.validate(myUploadForm, bindingResult);
	      if (bindingResult.hasErrors()) {
	    	  System.out.println("error");
	          return "stock/serialManagement";
	      }
//	      
	      //
	      List<File> uploadedFiles = new ArrayList<File>();
	      List<String> failedFiles = new ArrayList<String>();
	      
	      
	      for (MultipartFile fileData : fileDatas) {
	 
	         // Client File Name
	         String name = fileData.getOriginalFilename();
	         System.out.println("Client File Name = " + name);
	 
	         if (name != null && name.length() > 0) {
	            try {
	               // Create the file at server
	               File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
	               
	               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	               stream.write(fileData.getBytes());
	               stream.close();
	               //
	               uploadedFiles.add(serverFile);
	               System.out.println("Write file: " + serverFile);
	               ReadCSVFileExample.readCSVFile(serverFile);
	            } catch (Exception e) {
	               System.out.println("Error Write file: " + name);
	               failedFiles.add(name);
	            }
	         }
	      }
	      
	      
	       
	      model.addAttribute("description", description);
	      model.addAttribute("uploadedFiles", uploadedFiles);
	      model.addAttribute("failedFiles", failedFiles);
	      return "stock/serialManagement";
	   }
	
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
		
		UploadForm UploadForm = new UploadForm();
	    model.addAttribute("UploadForm", UploadForm);

		return "stock/serialManagement";
	}
	
	@RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
	public String uploadOneFileHandlerPOST(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("UploadForm") UploadForm myUploadForm, BindingResult bindingResult) {
 
		return this.doUpload(request, model, myUploadForm, bindingResult);
 
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
	
	@GetMapping(value= "/disposalhistory")
	public String getDisposalHistory(Model model) {

		List<DisposalHistory> disposalhistories = disposalHistoryDAO.getAllDisposalHistory();
		model.addAttribute("disposalhistories", disposalhistories);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		return "stock/disposalHistory";
	}
	
	@GetMapping(value= "/rma")
	public String getRMA(Model model) {

		List<RMA> rmas = rmaDAO.getAllRMA();
		model.addAttribute("rmas", rmas);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		return "stock/rma";
	}
	
	@GetMapping(value= "/itf")
	public String getITF(Model model) {

		List<ITF> itfs = itfDAO.getAllITF();
		model.addAttribute("itfs", itfs);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		return "stock/itf";
	}
	
	@GetMapping(value= "/assetrequisition")
	public String getAssetReqs(Model model) {

		List<AssetReqs> assetreqs = assetReqsDAO.getAllAssetReqs();
		model.addAttribute("assetreqs", assetreqs);
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		return "stock/assetReqs";
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
	public String getTransferApproval(@RequestParam int transferhistoryid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
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
		
		List<SubLoc> orisublocs = subLocDAO.getAllSubLoc(transferhistory.getOrimainlocid());
		model.addAttribute("orisublocs", orisublocs);
		
		List<SubLoc> dessublocs = subLocDAO.getAllSubLoc(transferhistory.getDesmainlocid());
		model.addAttribute("dessublocs", dessublocs);
		
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
	
	/**************** DISPOSAL ACTION ***********************/
	@GetMapping(value= "/disposeStock")
	public String getDisposeStock(Model model, HttpServletRequest request) {
	
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
			
		return "stock/disposeStock";
	}
	
	@PostMapping(value= "/disposeStock")
	public String postDisposeStock(@RequestParam int productid,
			@RequestParam int mainlocid,
			@RequestParam int sublocid,
			@RequestParam int quantity,  
			Model model,
			@RequestParam String referer) {
		
//		System.out.println(productid+" "+orimainlocid+" "+orisublocid+" "+desmainlocid+" "+dessublocid+" "+quantity);
		String errorString = disposalHistoryDAO.createDisposalHistory(null, DateTime.Now(), productid, quantity, mainlocid, sublocid, "pending");		
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/stockIn";
//			}
//		}
	}
	
	@GetMapping(value= "/editDisposalHistory")
	public String getEditDisposalHistory(@RequestParam int disposalhistoryid, Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		DisposalHistory disposalhistory= disposalHistoryDAO.getDisposalHistory(disposalhistoryid);
		model.addAttribute("disposalhistory", disposalhistory);
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc(disposalhistory.getMainlocid());
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("errorString",null);
			
		return "stock/editDisposalHistory";
	}
	
	@PostMapping(value= "/editDisposalHistory")
	public String postEditDisposalHistory(@RequestParam int disposalhistoryid,
			@RequestParam int productid, 
			@RequestParam int mainlocid,
			@RequestParam int sublocid,
			@RequestParam int quantity,  
			Model model,
			@RequestParam String referer) {
		
		String errorString = disposalHistoryDAO.updateDisposalHistory(disposalhistoryid, productid, mainlocid, sublocid, quantity);
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/editTransferHistory";
//			}
//		}
	}
	
	@GetMapping(value= "/disposalapproval")
	public String getDisposalApproval(@RequestParam int disposalhistoryid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		disposalHistoryDAO.approval(disposalhistoryid, approve);
		
		return "redirect:"+referer;
	}
	
	@GetMapping(value= "/deleteDisposalHistory")
	public String getDeleteDisposalHistory(@RequestParam int disposalhistoryid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		DisposalHistory disposalhistory = disposalHistoryDAO.getDisposalHistory(disposalhistoryid);
		Product product = productDAO.getProduct(disposalhistory.getProductid());
		
		model.addAttribute("disposalhistory", disposalhistory);		
		model.addAttribute("product", product);	
		
		return "stock/deleteDisposalHistory";
	}
	
	@PostMapping(value= "/deleteDisposalHistory")
	public String postDeleteDisposalHistory(@RequestParam int disposalhistoryid, Model model, @RequestParam String referer ) {
		System.out.println(disposalhistoryid);
//		disposalHistoryDAO.deleteDisposalHistory(disposalhistoryid);
			
		return "redirect:"+referer;
	}
	
	/**************** RMA ACTION ***********************/
	@GetMapping(value= "/createRMA")
	public String getCreateRMA(Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("errorString",null);
			
		return "stock/createRMA";
	}
	
	@PostMapping(value= "/createRMA")
	public String postCreateRMA(@RequestParam int productid,
			@RequestParam int mainlocid,
			@RequestParam int sublocid,
			@RequestParam int quantity, 
			@RequestParam String reason, 
			Model model,
			@RequestParam String referer) {
		
//		System.out.println(productid+" "+orimainlocid+" "+orisublocid+" "+desmainlocid+" "+dessublocid+" "+quantity);
		String errorString = rmaDAO.createRMA(null, DateTime.Now(), productid, quantity, mainlocid, sublocid, "pending", reason);		
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/createRMA";
//			}
//		}
	}
	
	@GetMapping(value= "/editRMA")
	public String getEditRMA(@RequestParam int rmaid, Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		RMA rma= rmaDAO.getRMA(rmaid);
		model.addAttribute("rma", rma);
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc(rma.getMainlocid());
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("errorString",null);
			
		return "stock/editRMA";
	}
	
	@PostMapping(value= "/editRMA")
	public String postEditRMA(@RequestParam int rmaid,
			@RequestParam int productid, 
			@RequestParam int mainlocid,
			@RequestParam (defaultValue="0")int sublocid,
			@RequestParam int quantity,  
			@RequestParam String reason, 
			Model model,
			@RequestParam String referer) {
		
		String errorString = rmaDAO.updateRMA(rmaid, productid, mainlocid, sublocid, quantity, reason);
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/editTransferHistory";
//			}
//		}
	}
	
	@GetMapping(value= "/rmaapproval")
	public String getRMAApproval(@RequestParam int rmaid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		rmaDAO.approval(rmaid, approve);
		
		return "redirect:"+referer;
	}
	
	@GetMapping(value= "/deleteRMA")
	public String getDeleteRMA(@RequestParam int rmaid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		RMA rma = rmaDAO.getRMA(rmaid);
		Product product = productDAO.getProduct(rma.getProductid());
		
		model.addAttribute("rma", rma);		
		model.addAttribute("product", product);	
		
		return "stock/deleteRMA";
	}
	
	@PostMapping(value= "/deleteRMA")
	public String postDeleteRMA(@RequestParam int rmaid, Model model, @RequestParam String referer ) {
		System.out.println(rmaid);
//		rmaDAO.deleteRMA(rmaid);
			
		return "redirect:"+referer;
	}
	
	/**************** ITF ACTION ***********************/
	@GetMapping(value= "/createITF")
	public String getCreateITF(Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("errorString",null);
			
		return "stock/createITF";
	}
	
	@PostMapping(value= "/createITF")
	public String postCreateITF(@RequestParam int productid,
			@RequestParam int mainlocid,
			@RequestParam int sublocid,
			@RequestParam int quantity, 
			Model model,
			@RequestParam String referer) {
		
//		System.out.println(productid+" "+orimainlocid+" "+orisublocid+" "+desmainlocid+" "+dessublocid+" "+quantity);
		String errorString = itfDAO.createITF(null, DateTime.Now(), productid, quantity, mainlocid, sublocid, "pending");		
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/createITF";
//			}
//		}
	}
	
	@GetMapping(value= "/editITF")
	public String getEditITF(@RequestParam int itfid, Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		ITF itf= itfDAO.getITF(itfid);
		model.addAttribute("itf", itf);
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc(itf.getMainlocid());
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("errorString",null);
			
		return "stock/editITF";
	}
	
	@PostMapping(value= "/editITF")
	public String postEditITF(@RequestParam int itfid,
			@RequestParam int productid, 
			@RequestParam int mainlocid,
			@RequestParam (defaultValue="0")int sublocid,
			@RequestParam int quantity,  
			Model model,
			@RequestParam String referer) {
		
		String errorString = itfDAO.updateITF(itfid, productid, mainlocid, sublocid, quantity);
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/editITF";
//			}
//		}
	}
	
	@GetMapping(value= "/itfapproval")
	public String getITFApproval(@RequestParam int itfid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		rmaDAO.approval(itfid, approve);
		
		return "redirect:"+referer;
	}
	
	@GetMapping(value= "/deleteITF")
	public String getDeleteITF(@RequestParam int itfid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		ITF itf = itfDAO.getITF(itfid);
		Product product = productDAO.getProduct(itf.getProductid());
		
		model.addAttribute("itf", itf);		
		model.addAttribute("product", product);	
		
		return "stock/deleteITF";
	}
	
	@PostMapping(value= "/deleteITF")
	public String postDeleteITF(@RequestParam int itfid, Model model, @RequestParam String referer ) {
		System.out.println(itfid);
//		rmaDAO.deleteRMA(rmaid);
			
		return "redirect:"+referer;
	}
	
	/**************** ASSET REQUISITION ACTION ***********************/
	@GetMapping(value= "/createAssetReqs")
	public String getCreateAssetReqs(Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc();
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("errorString",null);
			
		return "stock/createAssetReqs";
	}
	
	@PostMapping(value= "/createAssetReqs")
	public String postCreateAssetReqs(@RequestParam int productid,
			@RequestParam int mainlocid,
			@RequestParam int sublocid,
			@RequestParam int quantity, 
			Model model,
			@RequestParam String referer) {
		
//		System.out.println(productid+" "+orimainlocid+" "+orisublocid+" "+desmainlocid+" "+dessublocid+" "+quantity);
		String errorString = assetReqsDAO.createAssetReqs(null, DateTime.Now(), productid, quantity, mainlocid, sublocid, "pending");		
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/createAssetReqs";
//			}
//		}
	}
	
	@GetMapping(value= "/editAssetReqs")
	public String getEditAssetReqs(@RequestParam int assetreqsid, Model model, HttpServletRequest request) {
	
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		AssetReqs assetreq= assetReqsDAO.getAssetReqs(assetreqsid);
		model.addAttribute("assetreq", assetreq);
		
		List<Product> products = productDAO.getAllProduct();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.getAllMainLoc();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.getAllSubLoc(assetreq.getMainlocid());
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("errorString",null);
			
		return "stock/editAssetReqs";
	}
	
	@PostMapping(value= "/editAssetReqs")
	public String postEditAssetReqs(@RequestParam int assetreqsid,
			@RequestParam int productid, 
			@RequestParam int mainlocid,
			@RequestParam (defaultValue="0")int sublocid,
			@RequestParam int quantity,  
			Model model,
			@RequestParam String referer) {
		
		String errorString = assetReqsDAO.updateAssetReqs(assetreqsid, productid, mainlocid, sublocid, quantity);
//		
//		if(errorString==null) {
			return "redirect:"+referer;
//			} else {
//				model.addAttribute("errorString",errorString);
//				return "stock/editAssetReqs";
//			}
//		}
	}
	
	@GetMapping(value= "/arapproval")
	public String getAssetReqsApproval(@RequestParam int assetreqsid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		assetReqsDAO.approval(assetreqsid, approve);
		
		return "redirect:"+referer;
	}
	
	@GetMapping(value= "/deleteAssetReqs")
	public String getDeleteAssetReqs(@RequestParam int assetreqsid, Model model, HttpServletRequest request) {
		
		String referer = request.getHeader("Referer");
		model.addAttribute("referer", referer);
		
		AssetReqs assetreq = assetReqsDAO.getAssetReqs(assetreqsid);
		Product product = productDAO.getProduct(assetreq.getProductid());
		
		model.addAttribute("assetreq", assetreq);		
		model.addAttribute("product", product);	
		
		return "stock/deleteAssetReqs";
	}
	
	@PostMapping(value= "/deleteAssetReqs")
	public String postDeleteAssetReqs(@RequestParam int assetreqsid, Model model, @RequestParam String referer ) {
		System.out.println(assetreqsid);
//		rmaDAO.deleteRMA(assetreqsid);
			
		return "redirect:"+referer;
	}
}
