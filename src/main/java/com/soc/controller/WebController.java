package com.soc.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soc.service.UploadService;

@Controller 
public class WebController {
  
  @Autowired
  private UploadService upload;
  
  @RequestMapping(value = { "/", "/index", "/main" }, method = RequestMethod.GET)
  public String homePage(Model model) {
      return "main";
  }
  
  @RequestMapping(value = { "/search"}, method = RequestMethod.GET)
  public String searchPage(Model model, HttpServletRequest request) {
      return "search";
  }
  
  @GetMapping("/upload")
  public String index() {
      return "upload";
  }
  
  @PostMapping("/upload") // //new annotation since 4.3
  public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes, 
                                 @Value("${temp_document_path}") String tempDocumentPath,
                                 @Value("${document_path}") String documentPath) {
    if (file.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
      return "redirect:upload";
    }
    upload.uploadFile(file, redirectAttributes, tempDocumentPath);
    upload.updateDataByUpload(tempDocumentPath, documentPath);
    return "redirect:/upload";
  }

}



