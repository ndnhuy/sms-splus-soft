package vn.com.splussoftware.sms.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan("vn.com.splussoftware.sms")
@EnableJpaRepositories(basePackages="vn.com.splussoftware.sms.model.repository")
@EntityScan("vn.com.splussoftware.sms.model.entity")
@EnableAutoConfiguration
@SpringBootApplication
public class SpringBootApplicationRunner extends SpringBootServletInitializer  {
  
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(SpringBootApplicationRunner.class);
	} 
	
	 public static void main(String[] args) {
	     ApplicationContext ctx = SpringApplication.run(SpringBootApplicationRunner.class, args);     
	 }

}
