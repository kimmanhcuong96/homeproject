package com.soc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class UploadServiceImpl implements UploadService{
  
  @Autowired
  private SearchService searchService;
  
  //Save the uploaded file to this folder
 
  
  @Override
  public void uploadFile(MultipartFile file, RedirectAttributes redirectAttributes, String uploadedFolder) {
    try {
  
        // Get the file and save it somewhere
        File folder = new File(uploadedFolder);
        if(!folder.exists() || folder == null) {
          folder.mkdir();
        }
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadedFolder + file.getOriginalFilename());
        Files.write(path, bytes);
  
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + file.getOriginalFilename() + "'");
  
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
  
  @Override
  public String updateDataByUpload(String tempDocumentPath, String documentPath) {
    searchService.storeGerneralInformation(tempDocumentPath);
    File tempFolder = new File(tempDocumentPath);
    File documentFolder = new File(documentPath);
    if(!documentFolder.exists() || documentFolder == null) {
      documentFolder.mkdir();
    }
    File[] listOfFiles = tempFolder.listFiles();
    int length = listOfFiles.length;
    for(int i = 0; i < length; i++) {
      try {
        File newFile  = new File(documentFolder.toString() + "\\" + listOfFiles[i].getName());
        if(!newFile.exists()) {
          Files.copy(listOfFiles[i].toPath(), newFile.toPath());
        }
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    
    try {
      FileUtils.cleanDirectory(tempFolder);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "ok";
  }
}
