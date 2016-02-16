package vn.com.splussoftware.sms.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import vn.com.splussoftware.sms.model.repository.login.GlobalPermissionRepository;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant;


@ComponentScan("vn.com.splussoftware.sms")
@EnableJpaRepositories(basePackages="vn.com.splussoftware.sms.model.repository" )
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
	     
	     GlobalPermissionRepository repo = ctx.getBean(GlobalPermissionRepository.class);
	     
//	     System.out.println("1 - admin - " + repo.existsByUserIdAndPermission(1, AuthenticationConstant.ROLE_ADMIN));
//	     System.out.println("1 - mod - " + repo.existsByUserIdAndPermission(1, AuthenticationConstant.ROLE_MOD));
//	     System.out.println("3 - admin - " + repo.existsByUserIdAndPermission(3, AuthenticationConstant.ROLE_ADMIN));
//	     System.out.println("3 - mod - " + repo.existsByUserIdAndPermission(3, AuthenticationConstant.ROLE_MOD));

	 }

}
