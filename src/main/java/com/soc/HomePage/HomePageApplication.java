package com.soc.HomePage;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages= {"com.soc.***"})
//@ComponentScan(basePackages="com.soc.***")
@EnableJpaRepositories(basePackages="com.soc.***")
@EntityScan("com.soc.***")
public class HomePageApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HomePageApplication.class, args);
	}
	
	@Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(ApplicationArguments.class);
  }
}
