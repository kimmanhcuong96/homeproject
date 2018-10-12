package com.soc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "demo_project")
public class DemoProject {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer iD ;
  
  @Column(name = "name")
  private String name;
  
  @Column(name = "code")
  private String code;
  
  @Column(name = "home_town")
  private String homeTown;
  
  @Column(name = "file_name")
  private String fileName;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public DemoProject(Integer iD, String name, String code, String homeTown, String fileName) {
    this.iD = iD;
    this.name = name;
    this.code = code;
    this.homeTown = homeTown;
    this.fileName = fileName;
  }
  
  public DemoProject(String name, String code, String homeTown, String fileName) {
    this.name = name;
    this.code = code;
    this.homeTown = homeTown;
    this.fileName = fileName;
  }
  
  public DemoProject() {
  }

  public Integer getID() {
    return iD;
  }

  public void setID(Integer iD) {
    this.iD = iD;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getHomeTown() {
    return homeTown;
  }

  public void setHomeTown(String homeTown) {
    this.homeTown = homeTown;
  }
  
  
  
  
}
