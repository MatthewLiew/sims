package com.sims.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.sims.model.UploadForm;

@Component
public class CustomFileValidator implements Validator{
public static final String PNG_MIME_TYPE="image/png";
public static final long TEN_MB_IN_BYTES = 10485760;
    @Override
    public boolean supports(Class<?> clazz) {
        return UploadForm.class.isAssignableFrom(clazz);
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	UploadForm myUploadForm = (UploadForm)target;
        MultipartFile[] fileDatas = myUploadForm.getFileDatas();
        
        for (MultipartFile fileData : fileDatas) {
	        if(fileData.isEmpty()) {
	        	errors.reject("upload.file.required");
//	            errors.rejectValue("file", "upload.file.required");
	        }
	        else if(!PNG_MIME_TYPE.equalsIgnoreCase(fileData.getContentType())){
//	            errors.rejectValue("file", "upload.invalid.file.type");
	        }
	       
	        else if(fileData.getSize() > TEN_MB_IN_BYTES){
//	            errors.rejectValue("file", "upload.exceeded.file.size");
	        }
        }
    }
}