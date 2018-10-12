package com.soc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soc.dao.DemoProjectRepository;

@Component
public class ViewServiceImpl implements ViewService {
  
  @Autowired
  DemoProjectRepository demoProjectRepository;
  
  @Override
  public String getFileNameFromDB(String code) {
    String result = demoProjectRepository.findFileNameByCode(code);
    System.out.println("file name + cocde: " + result + " + " + code );
    if(result == null) {
      return "fail";
    }
    return result;
  }
}
