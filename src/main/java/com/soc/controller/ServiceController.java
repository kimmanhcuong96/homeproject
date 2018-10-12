package com.soc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.soc.dto.ContentPost;
import com.soc.model.DemoProject;
import com.soc.service.SearchService;
import com.soc.service.ViewService;

@Controller 
public class ServiceController {

  @Autowired
  private ViewService viewService;
  
  @Autowired
  private SearchService searchService;
  
  @RequestMapping(value = {"/searching" }, method = RequestMethod.POST)
  @ResponseBody
  public List<DemoProject> searching(@RequestBody ContentPost request){
    List<DemoProject> response = new ArrayList<>();
    if (request == null || request.getContent().isEmpty()) {
      response.clear();
      return response;
    }
    List<DemoProject> result = searchService.search(request.getContent());
    return result;
  }
  
  @RequestMapping(value = {"/scanFile"}, method = RequestMethod.GET)
  @ResponseBody
  public List<String> scanFile(@Value("${document_path}") String documentPath) {
    return searchService.scanFile(documentPath);
  }
  
  @RequestMapping(value = {"/storeInformation"}, method = RequestMethod.GET)
  @ResponseBody
  public String storeGeneralInformation(@Value("${document_path}") String documentPath) {
    return searchService.storeGerneralInformation(documentPath);
  }
  
  @RequestMapping(value = {"/refreshDataBase"}, method = RequestMethod.GET)
  @ResponseBody
  public String refreshDataBase(@Value("${document_path}") String documentPath) {
    searchService.clearDatabase();
    return searchService.storeGerneralInformation(documentPath);
  }
  
  
  @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
  public StreamingResponseBody getSteamingFile(HttpServletResponse response,
                                               @RequestParam String document,
                                               @Value("${document_path}") String documentPath) throws IOException {
    String standardedCode = document.toLowerCase();
    String fileName = viewService.getFileNameFromDB(standardedCode);
    String filePath = documentPath + fileName;
    File downloadedFile = new File(filePath);
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition: inline", "attachment; filename=" + fileName );
    InputStream inputStream = new FileInputStream(new File(filePath));
    if(downloadedFile.exists()) {
      return outputStream -> {
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
          outputStream.write(data, 0, nRead);
        }
        inputStream.close();
      };     
    }
    
    return outputStream -> {
      outputStream.write(0);
    };
    
  }
  

}
