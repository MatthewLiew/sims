package com.sbinventory.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.sbinventory.fileio.ReadCSVFileExample;
import com.sbinventory.model.UploadForm;
import com.sbinventory.validator.CustomFileValidator;

@Controller
public class FileUploadController {

	@Autowired
	CustomFileValidator customFileValidator;
	
	@RequestMapping(value = "/")
	public String homePage() {
 
		return "index";
	}
 
   // GET: Show upload form page.
   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
   public String uploadOneFileHandler(Model model) {
 
      UploadForm myUploadForm = new UploadForm();
      model.addAttribute("myUploadForm", myUploadForm);
 
      return "uploadOneFile";
   }
 
   // POST: Do Upload
//   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
//   public String uploadOneFileHandlerPOST(HttpServletRequest request, //
//         Model model, //
//         @ModelAttribute("myUploadForm") UploadForm myUploadForm, BindingResult bindingResult) {
// 
//      return this.doUpload(request, model, myUploadForm, bindingResult);
// 
//   }
 
   // GET: Show upload form page.
   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
   public String uploadMultiFileHandler(Model model) {
 
	   UploadForm myUploadForm = new UploadForm();
      model.addAttribute("myUploadForm", myUploadForm);
 
      return "uploadMultiFile";
   }
 
   // POST: Do Upload
//   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
//   public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
//         Model model, //
//         @ModelAttribute("myUploadForm") UploadForm myUploadForm, BindingResult bindingResult) {
// 
//      return this.doUpload(request, model, myUploadForm, bindingResult);
// 
//   }
 
//   private String doUpload(HttpServletRequest request, Model model, //
//		   UploadForm myUploadForm, BindingResult bindingResult) {
// 
//      String description = myUploadForm.getDescription();
//      System.out.println("Description: " + description);
// 
//      // Root Directory.
//      String uploadRootPath = request.getServletContext().getRealPath("upload");
//      System.out.println("uploadRootPath=" + uploadRootPath);
// 
//      File uploadRootDir = new File(uploadRootPath);
//      // Create directory if it not exists.
//      if (!uploadRootDir.exists()) {
//         uploadRootDir.mkdirs();
//      }
//      MultipartFile[] fileDatas = myUploadForm.getFileDatas();
//      
//      customFileValidator.validate(myUploadForm, bindingResult);
//      if (bindingResult.hasErrors()) {
//    	  System.out.println("error");
//          return "uploadOneFile";
//      }
////      
//      //
//      List<File> uploadedFiles = new ArrayList<File>();
//      List<String> failedFiles = new ArrayList<String>();
//      
//      
//      for (MultipartFile fileData : fileDatas) {
// 
//         // Client File Name
//         String name = fileData.getOriginalFilename();
//         System.out.println("Client File Name = " + name);
// 
//         if (name != null && name.length() > 0) {
//            try {
//               // Create the file at server
//               File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
//               
//               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
//               stream.write(fileData.getBytes());
//               stream.close();
//               //
//               uploadedFiles.add(serverFile);
//               System.out.println("Write file: " + serverFile);
//               ReadCSVFileExample.readCSVFile(serverFile);
//            } catch (Exception e) {
//               System.out.println("Error Write file: " + name);
//               failedFiles.add(name);
//            }
//         }
//      }
//      
//      
//       
//      model.addAttribute("description", description);
//      model.addAttribute("uploadedFiles", uploadedFiles);
//      model.addAttribute("failedFiles", failedFiles);
//      return "uploadResult";
//   }
}
