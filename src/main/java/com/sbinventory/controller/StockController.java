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

import com.sbinventory.dao.AppRoleDAO;
import com.sbinventory.dao.AssetReqsDAO;
import com.sbinventory.dao.BrandDAO;
import com.sbinventory.dao.DeptDAO;
import com.sbinventory.dao.DisposalHistoryDAO;
import com.sbinventory.dao.HardwareDAO;
import com.sbinventory.dao.ITFDAO;
import com.sbinventory.dao.MainLocDAO;
import com.sbinventory.dao.OrganizationDAO;
import com.sbinventory.dao.PartNoDAO;
import com.sbinventory.dao.ProductDAO;
import com.sbinventory.dao.RMADAO;
import com.sbinventory.dao.ReasonDAO;
import com.sbinventory.dao.StockHistoryDAO;
import com.sbinventory.dao.StockTypeDAO;
import com.sbinventory.dao.StorageDAO;
import com.sbinventory.dao.SubDeptDAO;
import com.sbinventory.dao.SubLocDAO;
import com.sbinventory.dao.TransferHistoryDAO;
import com.sbinventory.dao.TransferTypeDAO;
import com.sbinventory.dao.UserAccountDAO;
import com.sbinventory.dao.UserCapDAO;
import com.sbinventory.fileio.ReadCSVFileExample;
import com.sbinventory.model.AppRole;
import com.sbinventory.model.AssetReqs;
import com.sbinventory.model.Brand;
import com.sbinventory.model.Dept;
import com.sbinventory.model.DisposalHistory;
import com.sbinventory.model.Hardware;
import com.sbinventory.model.ITF;
import com.sbinventory.model.MainLoc;
import com.sbinventory.model.Organization;
import com.sbinventory.model.PartNo;
import com.sbinventory.model.Product;
import com.sbinventory.model.RMA;
import com.sbinventory.model.Reason;
import com.sbinventory.model.StockHistory;
import com.sbinventory.model.StockType;
import com.sbinventory.model.Storage;
import com.sbinventory.model.SubDept;
import com.sbinventory.model.SubLoc;
import com.sbinventory.model.TransferHistory;
import com.sbinventory.model.TransferType;
import com.sbinventory.model.UploadForm;
import com.sbinventory.model.UserAccount;
import com.sbinventory.model.UserCap;
import com.sbinventory.utils.DateTime;
import com.sbinventory.utils.StockQuantity;
import com.sbinventory.validator.CustomFileValidator;

@Controller
public class StockController {
	
	@Autowired
	private StockQuantity stockquantity;
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Autowired
	private SubDeptDAO subdeptDAO;
	
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
	private TransferTypeDAO transferTypeDAO;
	
	@Autowired
	private DisposalHistoryDAO disposalHistoryDAO;
	
	@Autowired
	private RMADAO rmaDAO;
	
	@Autowired
	private ITFDAO itfDAO;
	
	@Autowired
	private AssetReqsDAO assetReqsDAO;
	
	@Autowired
	private UserCapDAO userCapDAO;
	
	@Autowired
	private AppRoleDAO appRoleDAO;
	
	@Autowired
	private UserAccountDAO userAccountDAO;
	
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
			@RequestParam (required=false) Integer reasonid, Principal principal) {
		
		if(principal!=null) {
			UserAccount user = userAccountDAO.findOneByUsername(principal.getName(), 0);
//			AppRole approle = appRoleDAO.findRoleNameByRoleid(user.getRoleid());
//			model.addAttribute("role", approle.getRolename());
			
			UserCap usercap = userCapDAO.findOneByApprole(user.getRoleid());
			model.addAttribute("usercap", usercap);
		}
		
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
		
		List<StockType> stocktypes = stockTypeDAO.findAll();
		model.addAttribute("stocktypes", stocktypes);
		
		/*List<Organization> orgs = organizationDAO.findAll();
		model.addAttribute("orgs",orgs);*/
		
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
	public String getSerialManagement(Model model, Principal principal) {

		if(principal!=null) {
			UserAccount user = userAccountDAO.findOneByUsername(principal.getName(), 0);
//			AppRole approle = appRoleDAO.findRoleNameByRoleid(user.getRoleid());
//			model.addAttribute("role", approle.getRolename());
			
			UserCap usercap = userCapDAO.findOneByApprole(user.getRoleid());
			model.addAttribute("usercap", usercap);
		}
		
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
		
		List<Organization> organizations = organizationDAO.findAll();
		model.addAttribute("organizations", organizations);
		
		List<Dept> depts = deptDAO.findAll();
		model.addAttribute("depts", depts);
		
		List<SubDept> subdepts = subdeptDAO.findAll();
		model.addAttribute("subdepts", subdepts);
		
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
	public String getSettings(Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Reason> inreasons = reasonDAO.findAllByStocktype(1);
		model.addAttribute("inreasons", inreasons);
		
		List<Reason> outreasons = reasonDAO.findAllByStocktype(2);
		model.addAttribute("outreasons", outreasons);
		
		StockType stockin = stockTypeDAO.findOne(1);
		model.addAttribute("stockin", stockin);
		
		StockType stockout = stockTypeDAO.findOne(2);
		model.addAttribute("stockout", stockout);
		
		List<AppRole> approles = appRoleDAO.getAllRoleNames();
		model.addAttribute("approles", approles);
		
		List<UserCap> usercaps = userCapDAO.findAll();
		model.addAttribute("usercaps", usercaps);
//		for(int i=0;i<usercaps.size();i++) {
//			System.out.println(usercaps.get(i));
//		}
		String message = (String)model.asMap().get("message");
		model.addAttribute("message", message);
		
		return "stock/settings";
	}
	
	@GetMapping(value= "/storage")
	public String getStorage(Model model, Principal principal) {
		
		UserAccount user = null;
		if(principal!=null) {
			user = userAccountDAO.findOneByUsername(principal.getName(), 0);
			UserCap usercap = userCapDAO.findOneByApprole(user.getRoleid());
			model.addAttribute("usercap", usercap);
		} 
		
		stockquantity.update();
		
		if(principal!=null) {
			if(user.getRoleid()==1) {
				List<Storage> storages = storageDAO.getAllStorage();
				model.addAttribute("storages", storages);
			}
			if(user.getRoleid()==2) {
				List<Storage> storages = storageDAO.findAllBySubdeptid(user.getSubdeptid());
				model.addAttribute("storages", storages);
			}
			if(user.getRoleid()==3) {
				List<Storage> storages = storageDAO.findAllByDeptid(user.getDeptid());
				model.addAttribute("storages", storages);
			}
			if(user.getRoleid()==4) {
				List<Storage> storages = storageDAO.findAllByOrgid(user.getOrgid());
				model.addAttribute("storages", storages);
			}
			
		} else {
			List<Storage> storages = storageDAO.getAllStorage();
			model.addAttribute("storages", storages);
		}
		
		List<Organization> orgs = organizationDAO.findAll();
		model.addAttribute("orgs", orgs);
		
		List<Dept> depts = deptDAO.findAll();
		model.addAttribute("depts", depts);
		
		List<SubDept> subdepts = subdeptDAO.findAll();
		model.addAttribute("subdepts", subdepts);
		
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

		List<DisposalHistory> disposalhistories = disposalHistoryDAO.findAll();
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

		List<RMA> rmas = rmaDAO.findAll();
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

		List<ITF> itfs = itfDAO.findAll();
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

		List<AssetReqs> assetreqs = assetReqsDAO.findAll();
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
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<StockType> stocktypes = stockTypeDAO.findAll();
		model.addAttribute("stocktypes",stocktypes);
		
		model.addAttribute("reason",new Reason());
			
		return "stock/createReason";
	}
		
	@PostMapping(value= "/createReason")
	public String postCreateReason(@ModelAttribute Reason reason, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
		String errorString= reasonDAO.createReason(reason.getReason(), reason.getStocktypeid());

		if(errorString==null) {
			String message= "Reason - "+reason.getReason() +" created successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editReason")
	public String getEditReason(@RequestParam int reasonid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<StockType> stocktypes = stockTypeDAO.findAll();
		model.addAttribute("stocktypes",stocktypes);
		
		Reason reason = reasonDAO.getReason(reasonid);
		model.addAttribute("reason",reason);

		return "stock/editReason";
	}
		
	@PostMapping(value= "/editReason")
	public String postEditReason(@ModelAttribute Reason reason, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {

		String errorString=reasonDAO.updateReason(reason.getReasonid(), reason.getReason(), reason.getStocktypeid());
		if(errorString==null) {
			String message= "Reason - "+ reason.getReason()+" updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/deleteReason")
	public String getDeleteReason(@RequestParam int reasonid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Reason reason = reasonDAO.getReason(reasonid);
		model.addAttribute("reason", reason);
		
		StockType stocktype = stockTypeDAO.findOne(reason.getStocktypeid());
		model.addAttribute("stocktype", stocktype);
		
		return "stock/deleteReason";
	}
	
	@PostMapping(value= "/deleteReason")
	public String postDeleteReason(@ModelAttribute Reason reason, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
		
		String errorString = reasonDAO.deleteReason(reason.getReasonid());
			
		if(errorString==null) {
			String message="Reason - "+ reason.getReason()+" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** STOCK ACTION ***********************/
	@GetMapping(value= "/stockIn")
	public String getStockIn(@RequestParam(defaultValue="0") int productid, @RequestParam(defaultValue="1") int stocktypeid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("index", productid);
		model.addAttribute("products",products);
		
		List<Reason> reasons = reasonDAO.findAllByStocktype(stocktypeid);
		model.addAttribute("reasons",reasons);
		
		List<Organization> orgs = organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
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
		String errorString= stockHistoryDAO.create(sh.getProductid(), sh.getOrgid(), sh.getDeptid(), sh.getSubdeptid(), sh.getMainlocid(), 
				sh.getSublocid(), sh.getQuantity(), sh.getHistorydate(), sh.getHistorytime(), sh.getStocktypeid(), sh.getReasonid(), 
				sh.getRemark(), logdatetime, null, "pending");
		
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
		
		List<Reason> reasons = reasonDAO.findAllByStocktype(stocktypeid);
		model.addAttribute("reasons",reasons);
		
		List<Organization> orgs = organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
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
		String errorString= stockHistoryDAO.create(sh.getProductid(), sh.getOrgid(), sh.getDeptid(), sh.getSubdeptid(), sh.getMainlocid(), 
				sh.getSublocid(), sh.getQuantity(), sh.getHistorydate(), sh.getHistorytime(), sh.getStocktypeid(), sh.getReasonid(),
				sh.getRemark(), logdatetime, null, "pending");

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
		
		List<StockType> stocktypes = stockTypeDAO.findAll();
		model.addAttribute("stocktypes",stocktypes);
		
		List<Organization> orgs= organizationDAO.findAll();
		model.addAttribute("orgs",orgs);
		
		List<Dept> depts= deptDAO.findAllByOrgid(stockhistory.getOrgid());
		model.addAttribute("depts",depts);
		
		List<SubDept> subdepts = subdeptDAO.findAllByDeptid(stockhistory.getDeptid());
		model.addAttribute("subdepts",subdepts);
		
		List<MainLoc> mainlocs = mainLocDAO.findAll();
		model.addAttribute("mainlocs",mainlocs);
		
		List<SubLoc> sublocs = subLocDAO.findAllByMainlocid(stockhistory.getMainlocid());
		model.addAttribute("sublocs",sublocs);
		
		return "stock/editStockHistory";
	}
	
	@PostMapping(value= "/editStockHistory")
	public String postEditStockHistory(@ModelAttribute StockHistory sh, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
		String errorString= stockHistoryDAO.update(sh.getStockhistoryid(), sh.getProductid(), sh.getQuantity(), sh.getHistorydate(), 
				sh.getHistorytime(), sh.getStocktypeid(), sh.getReasonid(), sh.getRemark(), sh.getMainlocid(), sh.getSublocid(), 
				sh.getOrgid(), sh.getDeptid(), sh.getSubdeptid());
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
	public String getTransferStock(Model model, HttpServletRequest request, Principal principal) {
	
		UserAccount user = null;
		if(principal!=null) {
			user = userAccountDAO.findOneByUsername(principal.getName(), 0);
			model.addAttribute("user", user);
		} 
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<Product> products = productDAO.findAll();
		model.addAttribute("products", products);
		
		List<TransferType> transfertypes = transferTypeDAO.findAll();
		model.addAttribute("transfertypes", transfertypes);
		
		List<Organization> organizations = organizationDAO.findAll(user.getOrgid());
		model.addAttribute("organizations", organizations);
		
		List<Dept> depts = deptDAO.findAllByOrgid(user.getOrgid(), user.getDeptid());
		model.addAttribute("depts", depts);
		
		List<SubDept> subdepts = subdeptDAO.findAllByDeptid(user.getDeptid(), user.getSubdeptid());
		model.addAttribute("subdepts", subdepts);
		
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
	public String postTransferStock(@ModelAttribute TransferHistory th, @RequestParam Integer[] destination, Model model, @RequestParam String sourceURL, RedirectAttributes ra, Principal pricipal) {
		
		//INSERT INTO TRANSFER HISTORY
		System.out.println(th.getCode());
		System.out.println(th.getProductid());
		System.out.println(th.getQuantity());
		System.out.println(th.getSerialno());
		int transfertype = th.getTransfertype();
		System.out.println(th.getTransfertype());
		String[] token = th.getSerialno().split("\\W+");
		PartNo partno =partNoDAO.findOneBySerialNo(token[0]);
		
		String source = null, dest = null;
		if(transfertype == 1) {
			Organization src = organizationDAO.findOne(partno.getOrgid());
			Organization des = organizationDAO.findOne(destination[th.getTransfertype()-1]);
			source = src.getOrgname();
			dest = des.getOrgname();
			
		} else if(transfertype == 2) {
			Dept src = deptDAO.findOne(partno.getDeptid());
			Dept des = deptDAO.findOne(destination[th.getTransfertype()-1]);
			source = src.getDeptname();
			dest = des.getDeptname();
			
		} else if(transfertype == 3) {
			SubDept src = subdeptDAO.findOne(partno.getSubdeptid());
			SubDept des = subdeptDAO.findOne(destination[th.getTransfertype()-1]);
			source = src.getSubdeptname();
			dest = des.getSubdeptname();
			
		} else if(transfertype == 4) {
			MainLoc src = mainLocDAO.findOne(partno.getMainlocid());
			MainLoc des = mainLocDAO.findOne(destination[th.getTransfertype()-1]);
			source = src.getMainlocname();
			dest = des.getMainlocname();
			
		} else if(transfertype == 5) {
			SubLoc src = subLocDAO.findOne(partno.getSublocid());
			SubLoc des = subLocDAO.findOne(destination[th.getTransfertype()-1]);
			source = src.getSublocname();
			dest = des.getSublocname();
		}
		
		System.out.println("Source: " + source);
		System.out.println("Destination: "+ dest);
		
		//END INSERT INTO TRANSFER HISTORY
		
		TransferHistory thistory = transferHistoryDAO.findOneByCode(th.getCode());
		transferHistoryDAO.createOutBound(partno.getOrgid(), partno.getDeptid(), partno.getSubdeptid(), partno.getMainlocid(), 
				partno.getSublocid(), "pending", null, null, thistory.getTransferhistoryid());
			
		//INSERT INTO OUTBOUND
		
		//END INSERT INTO OUTBOUND
		
		//INSERT INTO INBOUND
		//END INSERT INTO INBOUND
		
		String errorString =null;
		/*String errorString = transferHistoryDAO.create(null, DateTime.Now(),th.getProductid() , th.getQuantity(), 
				th.getOrimainlocid(), th.getOrisublocid(), th.getDesmainlocid(), th.getDessublocid(), "pending");*/		
		
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
		
//		List<SubLoc> orisublocs = subLocDAO.findAllByMainlocid(transferhistory.getOrimainlocid());
//		model.addAttribute("orisublocs", orisublocs);
		
//		List<SubLoc> dessublocs = subLocDAO.findAllByMainlocid(transferhistory.getDesmainlocid());
//		model.addAttribute("dessublocs", dessublocs);
			
		return "stock/editTransferHistory";
	}
	
	@PostMapping(value= "/editTransferHistory")
	public String postEditTransferHistory(@ModelAttribute TransferHistory th, Model model, @RequestParam String sourceURL, RedirectAttributes ra) {
		
//		String errorString = null;
		String errorString = transferHistoryDAO.update(th.getTransferhistoryid(), th.getProductid(), /*th.getOrimainlocid(), 
				th.getOrisublocid(), th.getDesmainlocid(), th.getDessublocid(),*/ th.getQuantity());
		
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
		
		String errorString = disposalHistoryDAO.create(null, DateTime.Now(), dh.getProductid(), dh.getQuantity(), 
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
		
		DisposalHistory disposalhistory= disposalHistoryDAO.findOne(disposalhistoryid);
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
		
		String errorString = disposalHistoryDAO.update(dh.getDisposalhistoryid(), dh.getProductid(), dh.getMainlocid(), 
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
		
		DisposalHistory disposalhistory = disposalHistoryDAO.findOne(disposalhistoryid);
		model.addAttribute("disposalhistory", disposalhistory);	
		
		Product product = productDAO.findOne(disposalhistory.getProductid());	
		model.addAttribute("product", product);	
		
		return "stock/deleteDisposalHistory";
	}
	
	@PostMapping(value= "/deleteDisposalHistory")
	public String postDeleteDisposalHistory(@RequestParam int disposalhistoryid, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {

		String errorString = disposalHistoryDAO.delete(disposalhistoryid);
			
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
		
		String errorString = rmaDAO.create(null, DateTime.Now(), rma.getProductid(), rma.getQuantity(), rma.getMainlocid(), rma.getSublocid(),
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
		
		RMA rma= rmaDAO.findOne(rmaid);
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
		
		String errorString = rmaDAO.update(rma.getRmaid(), rma.getProductid(), rma.getMainlocid(), rma.getSublocid(), rma.getQuantity(), 
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
		
		RMA rma = rmaDAO.findOne(rmaid);
		Product product = productDAO.findOne(rma.getProductid());
		
		model.addAttribute("rma", rma);		
		model.addAttribute("product", product);	
		
		return "stock/deleteRMA";
	}
	
	@PostMapping(value= "/deleteRMA")
	public String postDeleteRMA(@ModelAttribute RMA rma, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {

		String errorString = rmaDAO.delete(rma.getRmaid());
			
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
		
		String errorString = itfDAO.create(null, DateTime.Now(), productid, quantity, mainlocid, sublocid, "pending");		
		
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
		
		ITF itf= itfDAO.findOne(itfid);
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
		
		String errorString = itfDAO.update(itf.getItfid(), itf.getProductid(), itf.getMainlocid(), itf.getSublocid(), itf.getQuantity());
		
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
		
		ITF itf = itfDAO.findOne(itfid);
		model.addAttribute("itf", itf);
		
		Product product = productDAO.findOne(itf.getProductid());		
		model.addAttribute("product", product);	
		
		return "stock/deleteITF";
	}
	
	@PostMapping(value= "/deleteITF")
	public String postDeleteITF(@ModelAttribute ITF itf, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {

		String errorString = itfDAO.delete(itf.getItfid());
			
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
		
		String errorString = assetReqsDAO.create(null, DateTime.Now(), assetreqs.getProductid(), assetreqs.getQuantity(), assetreqs.getMainlocid(), 
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
		
		AssetReqs assetreq= assetReqsDAO.findOne(assetreqsid);
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
		
		String errorString = assetReqsDAO.update(assetreq.getAssetreqsid(), assetreq.getProductid(),assetreq.getMainlocid(), 
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
		
		AssetReqs assetreq = assetReqsDAO.findOne(assetreqsid);
		model.addAttribute("assetreq", assetreq);	
		
		Product product = productDAO.findOne(assetreq.getProductid());	
		model.addAttribute("product", product);	
		
		return "stock/deleteAssetReqs";
	}
	
	@PostMapping(value= "/deleteAssetReqs")
	public String postDeleteAssetReqs(@ModelAttribute AssetReqs assetreq, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {

		String errorString = assetReqsDAO.delete(assetreq.getAssetreqsid());
			
		if(errorString==null) {
			String message= "Asset Requisition has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	/**************** SETTINGS ACTION ***********************/
	@GetMapping(value= "/createSettings")
	public String getCreateSettings(Model model, HttpServletRequest request) {
	
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<StockType> stocktypes = stockTypeDAO.findAll();
		model.addAttribute("stocktypes",stocktypes);
		
		model.addAttribute("reason",new Reason());
			
		return "stock/createReason";
	}
		
	@PostMapping(value= "/createSettings")
	public String postCreateSettings(@ModelAttribute Reason reason, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
		String errorString= reasonDAO.createReason(reason.getReason(), reason.getStocktypeid());

		if(errorString==null) {
			String message= "Reason - "+reason.getReason() +" created successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
	
	@GetMapping(value= "/editSettings")
	public String getEditSettings(@RequestParam int reasonid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		List<StockType> stocktypes = stockTypeDAO.findAll();
		model.addAttribute("stocktypes",stocktypes);
		
		Reason reason = reasonDAO.getReason(reasonid);
		model.addAttribute("reason",reason);

		return "stock/editReason";
	}
		
	@PostMapping(value= "/usercap")
	public String postUserCap(/*@ModelAttribute List<UserCap> usercaps*/int[] usercapid, @RequestParam int[] accessright, @RequestParam int[] stockapprove,
			@RequestParam  int[] stockadd, @RequestParam  int[] stockedit, @RequestParam int[] stockdelete, @RequestParam  int[] serialadd, 
			@RequestParam  int[] serialedit, @RequestParam int[] serialdelete, Model model, @RequestParam String sourceURL,
			RedirectAttributes ra) {
		
		String errorString=null;
		for(int j=0;j<usercapid.length;j++) {
//			System.out.println("Approleid"+j+": "+usercapid[j]);
//			
//			System.out.println("Accessright"+j+": "+accessright[j]);
//			System.out.println("Approve"+j+": "+approve[j]);
//			System.out.println("Add"+j+": "+add[j]);
//			System.out.println("Edit"+j+": "+edit[j]);
//			System.out.println("Delete"+j+": "+delete[j]);
			errorString=userCapDAO.update(usercapid[j], accessright[j], stockapprove[j], stockadd[j], stockedit[j], stockdelete[j], serialadd[j], serialedit[j], serialdelete[j]);
		}

		
		if(errorString==null) {
			String message= "User Capabilities has updated successfully";
			ra.addFlashAttribute("message", message);
			return "redirect:/settings";
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:/settings";
		}
	}
	
	@GetMapping(value= "/deleteSettings")
	public String getDeleteSettings(@RequestParam int reasonid, Model model, HttpServletRequest request) {
		
		String sourceURL = request.getHeader("Referer");
		model.addAttribute("sourceURL", sourceURL);
		
		Reason reason = reasonDAO.getReason(reasonid);
		model.addAttribute("reason", reason);
		
		StockType stocktype = stockTypeDAO.findOne(reason.getStocktypeid());
		model.addAttribute("stocktype", stocktype);
		
		return "stock/deleteReason";
	}
	
	@PostMapping(value= "/deleteSettings")
	public String postDeleteSettings(@ModelAttribute Reason reason, Model model, @RequestParam String sourceURL, RedirectAttributes ra ) {
		
		String errorString = reasonDAO.deleteReason(reason.getReasonid());
			
		if(errorString==null) {
			String message="Reason - "+ reason.getReason()+" has deleted";
			ra.addFlashAttribute("message", message);
			return "redirect:"+sourceURL;
		} else {
			model.addAttribute("errorString",errorString);
			return "redirect:"+sourceURL;
		}
	}
}
