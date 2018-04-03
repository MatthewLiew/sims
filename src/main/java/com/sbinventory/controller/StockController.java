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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String getStockManagement(Model model, HttpServletRequest request) {
		
		stockquantity.update();
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<Hardware> hardwares = hardwareDAO.findAll();
		model.addAttribute("hardwares",hardwares);
		
		List<Brand> brands = brandDAO.findAll();
		model.addAttribute("brands",brands);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		model.addAttribute("stockhistory", new StockHistory());
		
		return "stock/stockManagement";
	}
	
	@GetMapping(value= "/stockmovement")
	public String getStockMovement(Model model, @RequestParam (required=false) String startdate, 
			@RequestParam (required=false) String enddate, @RequestParam (required=false) Integer stocktypeid, 
			@RequestParam (required=false) Integer reasonid) {
		
		String preset_enddate = DateTime.DateNow();
		String preset_startdate = DateTime.DateOneMonthBefore();

		List<StockHistory>stockhistories = stockHistoryDAO.findAllByFilter(
					startdate = startdate == null ? preset_startdate : startdate, 
					enddate = enddate == null ? preset_enddate : enddate, 
					stocktypeid == null ? 0 : stocktypeid, 
					reasonid == null ? 0 : reasonid);
		
		List<Reason> reasons = reasonDAO.getAllReason();
		model.addAttribute("reasons", reasons);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		model.addAttribute("stocktypes", stocktypes);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs",sublocs);
		
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		model.addAttribute("stockhistories", stockhistories);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "stock/stockMovement";
	}
	
	@GetMapping(value= "/serialmanagement")
	public String getSerialManagement(Model model) {

		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<PartNo> partnos = partNoDAO.findAll();
		model.addAttribute("partnos",partnos);
		
		UploadForm UploadForm = new UploadForm();
	    model.addAttribute("UploadForm", UploadForm);
	    
	    List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);

		return "stock/serialManagement";
	}
	
//	@RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
//	public String uploadOneFileHandlerPOST(HttpServletRequest request, //
//			Model model, //
//			@ModelAttribute("UploadForm") UploadForm myUploadForm, BindingResult bindingResult) {
// 
//		return this.doUpload(request, model, myUploadForm, bindingResult);
// 
//	}
	
	@GetMapping(value= "/settings")
	public String getSettings(Model model) {
		
		List<Reason> reasons = reasonDAO.getAllReason();
		model.addAttribute("reasons", reasons);
		
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		model.addAttribute("stocktypes", stocktypes);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "stock/settings";
	}
	
	@GetMapping(value= "/storage")
	public String getStorage(Model model) {
		
		stockquantity.update();

		List<Storage> storages = storageDAO.getAllStorage();
		model.addAttribute("storages", storages);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "stock/storage";
	}
	
	@GetMapping(value= "/transferhistory")
	public String getTransferHistory(Model model) {

		List<TransferHistory> transferhistories = transferHistoryDAO.findAll();
		model.addAttribute("transferhistories", transferhistories);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "stock/transferHistory";
	}
	
	@GetMapping(value= "/disposalhistory")
	public String getDisposalHistory(Model model) {

		List<DisposalHistory> disposalhistories = disposalHistoryDAO.getAllDisposalHistory();
		model.addAttribute("disposalhistories", disposalhistories);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "stock/disposalHistory";
	}
	
	@GetMapping(value= "/rma")
	public String getRMA(Model model) {

		List<RMA> rmas = rmaDAO.getAllRMA();
		model.addAttribute("rmas", rmas);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "stock/rma";
	}
	
	@GetMapping(value= "/itf")
	public String getITF(Model model) {

		List<ITF> itfs = itfDAO.getAllITF();
		model.addAttribute("itfs", itfs);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "stock/itf";
	}
	
	@GetMapping(value= "/assetrequisition")
	public String getAssetReqs(Model model) {

		List<AssetReqs> assetreqs = assetReqsDAO.getAllAssetReqs();
		model.addAttribute("assetreqs", assetreqs);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
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
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("index", productid);
		model.addAttribute("products",products);
		
		List<Reason> reasons = reasonDAO.getReasonStockInAndOut(stocktypeid);
		model.addAttribute("reasons",reasons);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs",sublocs);
		
		StockHistory sh =new StockHistory();
		sh.setProductid(productid);
		sh.setStocktypeid(stocktypeid);
		sh.setQuantity(1);
		sh.setHistorydate(DateTime.DateNow());
		sh.setHistorytime(DateTime.TimeNow());
		model.addAttribute("sh", sh);
		
		return "stock/stockIn";
	}
	
	@PostMapping(value= "/stockIn")
	public String postStockIn(@ModelAttribute StockHistory sh, String serialno, Model model, Principal principal, 
			@RequestParam String sourceURL, RedirectAttributes ra) {
		
//		String delims = ", \r\n\t\f";
//
//		System.out.println("StringTokenizer Example: \n");

//		StringTokenizer st = new StringTokenizer(serialno, delims);
//		while (st.hasMoreElements()) {
//			String errorString= partNoDAO.createPartNo(st.nextElement().toString().replaceAll("[^a-zA-Z0-9]", ""), modelno[i], upccode[i], productid[i], customername, invoiceno, mainlocid, sublocid, "Available");
//			System.out.println("Token: "+st.nextElement());
//		}

//		if(principal!=null){
//			String loguser = principal.getName();
//		}
		
		String logdatetime = DateTime.Now();
		String errorString= stockHistoryDAO.create(sh.getProductid(), sh.getMainlocid(), sh.getSublocid(), sh.getQuantity(), 
				sh.getHistorydate(), sh.getHistorytime(), sh.getStocktypeid(), sh.getReasonid(), sh.getRemark(), logdatetime, null, "pending");
		
		Product product = productDAO.findOne(sh.getProductid());
		if(errorString==null) {
			String message= product.getProductname() +" stocked in successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/stockOut")
	public String getStockOut(@RequestParam(defaultValue="0") int productid, @RequestParam(defaultValue="2") int stocktypeid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("index",productid);
		model.addAttribute("products",products);
		
		List<Reason> reasons = reasonDAO.getReasonStockInAndOut(stocktypeid);
		model.addAttribute("reasons",reasons);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs",sublocs);
		
		StockHistory sh =new StockHistory();
		sh.setProductid(productid);
		sh.setStocktypeid(stocktypeid);
		sh.setQuantity(1);
		sh.setHistorydate(DateTime.DateNow());
		sh.setHistorytime(DateTime.TimeNow());
		model.addAttribute("sh", sh);
		
		return "stock/stockOut";
	}
	
	@PostMapping(value= "/stockOut")
	public String postStockOut(@ModelAttribute StockHistory sh, String serialno, Model model, Principal principal, 
			@RequestParam String sourceURL, RedirectAttributes ra) {

//		String loguser = principal.getName();
		
//		String delims = ", \r\n\t\f";

//		System.out.println("StringTokenizer Example: \n");

//		StringTokenizer st = new StringTokenizer(serialno,delims);
//		while (st.hasMoreElements()) {
//			String errorString= partNoDAO.createPartNo(st.nextElement().toString().replaceAll("[^a-zA-Z0-9]", ""), modelno[i], upccode[i], productid[i], customername, invoiceno, mainlocid, sublocid, "Available");
//			System.out.println("Token: "+st.nextElement());
//		}
			
		String logdatetime = DateTime.Now();
		String errorString= stockHistoryDAO.create(sh.getProductid(), sh.getMainlocid(), sh.getSublocid(), sh.getQuantity(), 
				sh.getHistorydate(), sh.getHistorytime(), sh.getStocktypeid(), sh.getReasonid(), sh.getRemark(), logdatetime, null, "pending");

		Product product = productDAO.findOne(sh.getProductid());
		if(errorString==null) {
			String message= product.getProductname() +" stocked out successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/stockioapproval")
	public String getStockIOApproval(@RequestParam int stockhistoryid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		stockHistoryDAO.approval(stockhistoryid, approve);
		
		return "redirect:"+sourceURL;
	}
	
	@GetMapping(value= "/editStockHistory")
	public String getEditStockHistory(@RequestParam int stockhistoryid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		StockHistory stockhistory= stockHistoryDAO.findOne(stockhistoryid);
		model.addAttribute("sh",stockhistory);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products",products);
		
		List<Reason> reasons = reasonDAO.getAllReason();
		model.addAttribute("reasons",reasons);
		
		List<StockType> stocktypes = stockTypeDAO.getAllStockType();
		model.addAttribute("stocktypes",stocktypes);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAllByMainlocid(stockhistory.getMainlocid());
		model.addAttribute("sublocs",sublocs);
		
		return "stock/editStockHistory";
	}
	
	@PostMapping(value= "/editStockHistory")
	public String postEditStockHistory(@ModelAttribute StockHistory sh, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString= stockHistoryDAO.update(sh.getStockhistoryid(), sh.getProductid(), sh.getQuantity(), sh.getHistorydate(), 
				sh.getHistorytime(), sh.getStocktypeid(), sh.getReasonid(), sh.getRemark(), sh.getMainlocid(), sh.getSublocid());
		if(errorString==null) {
			String message= "Stock record updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/deleteStockHistory")
	public String getDeleteStockHistory(@RequestParam int stockhistoryid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		StockHistory stockhistory = stockHistoryDAO.findOne(stockhistoryid);
		model.addAttribute("stockhistory", stockhistory);
		
		Product product = productDAO.findOne(stockhistory.getProductid());
		model.addAttribute("product", product);	
		
		return "stock/deleteStockHistory";
	}
	
	@PostMapping(value= "/deleteStockHistory")
	public String postDeleteStockHistory(@ModelAttribute StockHistory stockhistory, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
//		System.out.println(stockhistoryid);
		String errorString = stockHistoryDAO.delete(stockhistory.getStockhistoryid());
			
		if(errorString==null) {
			String message="Stock History has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** TRANSFER ACTION ***********************/
	@GetMapping(value= "/transferStock")
	public String getTransferStock(Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		TransferHistory transferhistory = new TransferHistory();
		transferhistory.setQuantity(1);
		model.addAttribute("th", transferhistory);
			
		return "stock/transferStock";
	}
	
	@PostMapping(value= "/transferStock")
	public String postTransferStock(@ModelAttribute TransferHistory th, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = transferHistoryDAO.create(null, DateTime.Now(),th.getProductid() , th.getQuantity(), 
				th.getOrimainlocid(), th.getOrisublocid(), th.getDesmainlocid(), th.getDessublocid(), "pending");		
//		
		if(errorString==null) {
			String message= "Stock Transfered successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/transferapproval")
	public String getTransferApproval(@RequestParam int transferhistoryid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		transferHistoryDAO.approval(transferhistoryid, approve);
		
		return "redirect:"+sourceURL;
	}
	
	@GetMapping(value= "/editTransferHistory")
	public String getEditTransferHistory(@RequestParam int transferhistoryid, Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		TransferHistory transferhistory= transferHistoryDAO.findOne(transferhistoryid);
		model.addAttribute("th", transferhistory);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> orisublocs = subLocDAO.findAllByMainlocid(transferhistory.getOrimainlocid());
		model.addAttribute("orisublocs", orisublocs);
		
		List<SubLoc> dessublocs = subLocDAO.findAllByMainlocid(transferhistory.getDesmainlocid());
		model.addAttribute("dessublocs", dessublocs);
			
		return "stock/editTransferHistory";
	}
	
	@PostMapping(value= "/editTransferHistory")
	public String postEditTransferHistory(@ModelAttribute TransferHistory th, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = transferHistoryDAO.update(th.getTransferhistoryid(), th.getProductid(), th.getOrimainlocid(), 
				th.getOrisublocid(), th.getDesmainlocid(), th.getDessublocid(), th.getQuantity());
		
		if(errorString==null) {
			String message= "Stock transfer record updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/deleteTransferHistory")
	public String getDeleteTransferHistory(@RequestParam int transferhistoryid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		TransferHistory transferhistory = transferHistoryDAO.findOne(transferhistoryid);
		model.addAttribute("transferhistory", transferhistory);	
		
		Product product = productDAO.findOne(transferhistory.getProductid());	
		model.addAttribute("product", product);	
		
		return "stock/deleteTransferHistory";
	}
	
	@PostMapping(value= "/deleteTransferHistory")
	public String postDeleteTransferHistory(@RequestParam int transferhistoryid, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
		
		String errorString = transferHistoryDAO.delete(transferhistoryid);
			
		if(errorString==null) {
			String message="Stock transfer record has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** DISPOSAL ACTION ***********************/
	@GetMapping(value= "/disposeStock")
	public String getDisposeStock(Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("dh",new DisposalHistory());
			
		return "stock/disposeStock";
	}
	
	@PostMapping(value= "/disposeStock")
	public String postDisposeStock(@ModelAttribute DisposalHistory dh, @RequestParam int productid, Model model, 
			@RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = disposalHistoryDAO.createDisposalHistory(null, DateTime.Now(), dh.getProductid(), dh.getQuantity(), 
				dh.getMainlocid(), dh.getSublocid(), "pending");		
//		
		if(errorString==null) {
			String message= "Stock Disposed successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editDisposalHistory")
	public String getEditDisposalHistory(@RequestParam int disposalhistoryid, Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		DisposalHistory disposalhistory= disposalHistoryDAO.getDisposalHistory(disposalhistoryid);
		model.addAttribute("dh", disposalhistory);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAllByMainlocid(disposalhistory.getMainlocid());
		model.addAttribute("sublocs", sublocs);
			
		return "stock/editDisposalHistory";
	}
	
	@PostMapping(value= "/editDisposalHistory")
	public String postEditDisposalHistory(@ModelAttribute DisposalHistory dh, Model model, @RequestParam String sourceURL, 
			RedirectAttributes ra) {
		
		String errorString = disposalHistoryDAO.updateDisposalHistory(dh.getDisposalhistoryid(), dh.getProductid(), dh.getMainlocid(), 
				dh.getSublocid(), dh.getQuantity());
		
		if(errorString==null) {
			String message= "Stock disposal record updated";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/disposalapproval")
	public String getDisposalApproval(@RequestParam int disposalhistoryid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		disposalHistoryDAO.approval(disposalhistoryid, approve);
		
		return "redirect:"+sourceURL;
	}
	
	@GetMapping(value= "/deleteDisposalHistory")
	public String getDeleteDisposalHistory(@RequestParam int disposalhistoryid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		DisposalHistory disposalhistory = disposalHistoryDAO.getDisposalHistory(disposalhistoryid);
		model.addAttribute("disposalhistory", disposalhistory);	
		
		Product product = productDAO.findOne(disposalhistory.getProductid());	
		model.addAttribute("product", product);	
		
		return "stock/deleteDisposalHistory";
	}
	
	@PostMapping(value= "/deleteDisposalHistory")
	public String postDeleteDisposalHistory(@RequestParam int disposalhistoryid, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {

		String errorString = disposalHistoryDAO.deleteDisposalHistory(disposalhistoryid);
			
		if(errorString==null) {
			String message= "Stock disposal record has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** RMA ACTION ***********************/
	@GetMapping(value= "/createRMA")
	public String getCreateRMA(Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("rma",new RMA());
			
		return "stock/createRMA";
	}
	
	@PostMapping(value= "/createRMA")
	public String postCreateRMA(@ModelAttribute RMA rma, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = rmaDAO.createRMA(null, DateTime.Now(), rma.getProductid(), rma.getQuantity(), rma.getMainlocid(), rma.getSublocid(),
				"pending", rma.getReason());		
		
		if(errorString==null) {
			String message= "RMA created successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editRMA")
	public String getEditRMA(@RequestParam int rmaid, Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		RMA rma= rmaDAO.getRMA(rmaid);
		model.addAttribute("rma", rma);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAllByMainlocid(rma.getMainlocid());
		model.addAttribute("sublocs", sublocs);
			
		return "stock/editRMA";
	}
	
	@PostMapping(value= "/editRMA")
	public String postEditRMA(/*@RequestParam (defaultValue="0")int sublocid,*/
			@ModelAttribute RMA rma, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = rmaDAO.updateRMA(rma.getRmaid(), rma.getProductid(), rma.getMainlocid(), rma.getSublocid(), rma.getQuantity(), 
				rma.getReason());
		
		if(errorString==null) {
			String message= "RMA record updated";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/rmaapproval")
	public String getRMAApproval(@RequestParam int rmaid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		rmaDAO.approval(rmaid, approve);
		
		return "redirect:"+sourceURL;
	}
	
	@GetMapping(value= "/deleteRMA")
	public String getDeleteRMA(@RequestParam int rmaid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		RMA rma = rmaDAO.getRMA(rmaid);
		Product product = productDAO.findOne(rma.getProductid());
		
		model.addAttribute("rma", rma);		
		model.addAttribute("product", product);	
		
		return "stock/deleteRMA";
	}
	
	@PostMapping(value= "/deleteRMA")
	public String postDeleteRMA(@ModelAttribute RMA rma, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {

		String errorString = rmaDAO.deleteRMA(rma.getRmaid());
			
		if(errorString==null) {
			String message= "RMA record has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** ITF ACTION ***********************/
	@GetMapping(value= "/createITF")
	public String getCreateITF(Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("itf",new ITF());
			
		return "stock/createITF";
	}
	
	@PostMapping(value= "/createITF")
	public String postCreateITF(@RequestParam int productid,
			@RequestParam int mainlocid,
			@RequestParam int sublocid,
			@RequestParam int quantity, 
			Model model,
			@RequestParam String sourceURL, RedirectAttributes ra ) {
		
		String errorString = itfDAO.createITF(null, DateTime.Now(), productid, quantity, mainlocid, sublocid, "pending");		
		
		if(errorString==null) {
			String message= "ITF created successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editITF")
	public String getEditITF(@RequestParam int itfid, Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		ITF itf= itfDAO.getITF(itfid);
		model.addAttribute("itf", itf);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAllByMainlocid(itf.getMainlocid());
		model.addAttribute("sublocs", sublocs);
			
		return "stock/editITF";
	}
	
	@PostMapping(value= "/editITF")
	public String postEditITF( /*@RequestParam (defaultValue="0")int sublocid,*/
			@ModelAttribute ITF itf, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
		
		String errorString = itfDAO.updateITF(itf.getItfid(), itf.getProductid(), itf.getMainlocid(), itf.getSublocid(), itf.getQuantity());
		
		if(errorString==null) {
			String message= "ITF record updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/itfapproval")
	public String getITFApproval(@RequestParam int itfid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		rmaDAO.approval(itfid, approve);
		
		return "redirect:"+sourceURL;
	}
	
	@GetMapping(value= "/deleteITF")
	public String getDeleteITF(@RequestParam int itfid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		ITF itf = itfDAO.getITF(itfid);
		model.addAttribute("itf", itf);
		
		Product product = productDAO.findOne(itf.getProductid());		
		model.addAttribute("product", product);	
		
		return "stock/deleteITF";
	}
	
	@PostMapping(value= "/deleteITF")
	public String postDeleteITF(@ModelAttribute ITF itf, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {

		String errorString = itfDAO.deleteITF(itf.getItfid());
			
		if(errorString==null) {
			String message= "ITF record has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** ASSET REQUISITION ACTION ***********************/
	@GetMapping(value= "/createAssetReqs")
	public String getCreateAssetReqs(Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAll();
		model.addAttribute("sublocs", sublocs);
		
		model.addAttribute("assetreqs",new AssetReqs());
			
		return "stock/createAssetReqs";
	}
	
	@PostMapping(value= "/createAssetReqs")
	public String postCreateAssetReqs(@ModelAttribute AssetReqs assetreqs, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString = assetReqsDAO.createAssetReqs(null, DateTime.Now(), assetreqs.getProductid(), assetreqs.getQuantity(), assetreqs.getMainlocid(), 
				assetreqs.getSublocid(), "pending");		
		
		if(errorString==null) {
			String message= "Asset Requisition created successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editAssetReqs")
	public String getEditAssetReqs(@RequestParam int assetreqsid, Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		AssetReqs assetreq= assetReqsDAO.getAssetReqs(assetreqsid);
		model.addAttribute("assetreq", assetreq);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs", mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAllByMainlocid(assetreq.getMainlocid());
		model.addAttribute("sublocs", sublocs);
			
		return "stock/editAssetReqs";
	}
	
	@PostMapping(value= "/editAssetReqs")
	public String postEditAssetReqs( /*@RequestParam (defaultValue="0")int sublocid,*/
			@ModelAttribute AssetReqs assetreq, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
		
		String errorString = assetReqsDAO.updateAssetReqs(assetreq.getAssetreqsid(), assetreq.getProductid(),assetreq.getMainlocid(), 
				assetreq.getSublocid(), assetreq.getQuantity());
		
		if(errorString==null) {
			String message= "Asset Requisition record updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/arapproval")
	public String getAssetReqsApproval(@RequestParam int assetreqsid, @RequestParam String approve, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		assetReqsDAO.approval(assetreqsid, approve);
		
		return "redirect:"+sourceURL;
	}
	
	@GetMapping(value= "/deleteAssetReqs")
	public String getDeleteAssetReqs(@RequestParam int assetreqsid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		AssetReqs assetreq = assetReqsDAO.getAssetReqs(assetreqsid);
		model.addAttribute("assetreq", assetreq);	
		
		Product product = productDAO.findOne(assetreq.getProductid());	
		model.addAttribute("product", product);	
		
		return "stock/deleteAssetReqs";
	}
	
	@PostMapping(value= "/deleteAssetReqs")
	public String postDeleteAssetReqs(@ModelAttribute AssetReqs assetreq, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {

		String errorString = assetReqsDAO.deleteAssetReqs(assetreq.getAssetreqsid());
			
		if(errorString==null) {
			String message= "Asset Requisition has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
}
