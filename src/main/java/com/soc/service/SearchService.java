package com.soc.service;

import java.util.List;

import com.soc.model.DemoProject;

public interface SearchService {

  public  List<DemoProject>  search(String searchContent);
  
  public List<String> scanFile(String path);
  
  public String storeGerneralInformation(String path);
  
  public List<Integer> findListIndex(String content);
  
  public void clearDatabase();
  
}
