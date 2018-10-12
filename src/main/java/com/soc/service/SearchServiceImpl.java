package com.soc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soc.dao.DemoProjectRepository;
import com.soc.model.DemoProject;

@Component
public class SearchServiceImpl implements SearchService{
  
  @Autowired
 DemoProjectRepository demoProjectRepository;

  @Override
  public List<DemoProject> search(String searchContent){
    String content = searchContent.toLowerCase();
    List<DemoProject> results = demoProjectRepository.findByHintName(content);
    if(results.isEmpty()) {
      results = demoProjectRepository.findByHintCode(content);
    }
    return results;
  }
  
  @Override
  public List<String> scanFile(String path) {
    
    File folder = new File(path);
    List<String> result = new ArrayList<>();
    File[] listOfFiles = folder.listFiles();
    if(listOfFiles == null || !folder.exists()) {
      return result;
    }
    int length = listOfFiles.length;
    for(int i = 0; i < length; i++) {
      if(listOfFiles[i].isFile()) {
        String fileName = listOfFiles[i].getName();
        System.out.println("File: " + fileName);
        int dotIndex = fileName.lastIndexOf('.');
        if(dotIndex == -1) {
          break;
        } else {
          String typeFile = fileName.substring(dotIndex+1);
          if(typeFile.equals("pdf")) {
            String contentName = listOfFiles[i].getName().substring(0, dotIndex);
            result.add(contentName);
        }
        }
      } else {
        if(listOfFiles[i].isDirectory()) {
          System.out.println("Directory: " + listOfFiles[i].getName());
        }
      }
    }
    return result;
  }
  
  @Override
  public String storeGerneralInformation(String path) {
    List<String> profiles = scanFile(path);
    try {
      int length = profiles.size();
      for(int i = 0; i < length; i++) {
        String profile = profiles.get(i);
        DemoProject object = new DemoProject();
        List<Integer> indexes = findListIndex(profile);
        String fileName = profile + ".pdf";
        String name = profile.substring(0, indexes.get(0));
        String code = profile.substring(indexes.get(0)+1, indexes.get(1));
        String homeTown = profile.substring(indexes.get(1)+1);
        object.setName(name.trim());
        object.setCode(code.trim());
        object.setHomeTown(homeTown);
        object.setFileName(fileName);
        demoProjectRepository.save(object);
      }
    }catch(PersistenceException e) {
      e.printStackTrace();
      return "error";
    }
    return "ok";
  }
  
  
  @Override
  public List<Integer> findListIndex(String content) {
    int index = content.indexOf("-");
    List<Integer> indexes = new ArrayList<>();
    indexes.add(index);
    while (index >= 0) {
        index = content.indexOf("-", index + 1);
        indexes.add(index);
    }
    return indexes;
  }
  
  @Override
  public void clearDatabase() {
    demoProjectRepository.deleteAll();
  }
  
  
}
