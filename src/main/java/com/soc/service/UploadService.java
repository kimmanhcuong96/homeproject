package com.soc.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UploadService {
  
  public void uploadFile(MultipartFile file, RedirectAttributes redirectAttributes, String uploadedFolder);
  
  public String updateDataByUpload(String tempDocmentPath, String documentPath);
}
