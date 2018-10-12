package com.soc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soc.model.DemoProject;

@Repository
public interface DemoProjectRepository extends CrudRepository<DemoProject, Integer> {
  @Query("SELECT u FROM DemoProject u Where lower(u.name) LIKE CONCAT('%',:content,'%')")
  public List<DemoProject> findByHintName(@Param("content") String content);
  
  @Query("SELECT u FROM DemoProject u Where lower(u.code) LIKE CONCAT('%',:content,'%')")
  public List<DemoProject> findByHintCode(@Param("content") String content);
  
  @Query("SELECT u.fileName FROM DemoProject u Where lower(u.code) LIKE CONCAT('%',:content,'%')")
  public String findFileNameByCode(@Param("content") String content);
  
  
//  @Query(value = "INSERT INTO demo_project(name, code, home_town) VALUES(:name, :code, :homeTown)", nativeQuery = true)
//  public void saveNewData(@Param("name") String name,@Param("code") String code, @Param("homeTown") String homeTown);
}
