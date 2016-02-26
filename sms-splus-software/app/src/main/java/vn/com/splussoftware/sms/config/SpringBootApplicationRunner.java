package vn.com.splussoftware.sms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity;
import vn.com.splussoftware.sms.model.repository.auth.GlobalPermissionRepository;
import vn.com.splussoftware.sms.ui.controller.FileUploadController;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionDto;
import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;
import vn.com.splussoftware.sms.utils.dto.UserDto;
import vn.com.splussoftware.sms.utils.service.GlobalPermissionService;
import vn.com.splussoftware.sms.utils.service.GroupServiceImpl;
import vn.com.splussoftware.sms.utils.service.UserService;

@ComponentScan("vn.com.splussoftware.sms")
@EnableJpaRepositories(basePackages = "vn.com.splussoftware.sms.model.repository")
@EntityScan("vn.com.splussoftware.sms.model.entity")
@EnableAutoConfiguration
@SpringBootApplication
public class SpringBootApplicationRunner extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

		return builder.sources(SpringBootApplicationRunner.class);
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringBootApplicationRunner.class, args);

		// BuuPV: Initiate a background thread for cleaning tmp folder purpose
		Runnable r = new FileUploadController();
		Thread t = new Thread(r);
		t.start();
		
		
		/*
		 * Below is the block of code used to create default user data (admin, mod, user)
		 * 
		 * @author HuyNDN
		 * 
		 */
		
		GlobalPermissionService globalPermissionService = ctx.getBean(GlobalPermissionService.class);
		UserService userService = ctx.getBean(UserService.class);
		// Delete all user data
		Integer adminId = userService.deleteByUserkey("admin");
		Integer modId = userService.deleteByUserkey("mod");
		Integer userId = userService.deleteByUserkey("user");
		Integer k = globalPermissionService.deleteByUserId(adminId);
		k = globalPermissionService.deleteByUserId(modId);
		k = globalPermissionService.deleteByUserId(userId);
		
		// Create user data
		PasswordEncoder passwordEncoder = ctx.getBean(PasswordEncoder.class);
		
		//String encodedPassword = passwordEncoder.encode("123");
		String encodedPassword = "123";
		
		UserDto userDto = new UserDto();
		userDto.setUserkey("admin");
		userDto.setPassword(encodedPassword);
		userDto.setStatus("active");
		userDto.setLoginMethodDto(new LoginMethodDto(1, null, null, null));
		
		userDto = userService.add(userDto);	
		globalPermissionService.save(new GlobalPermissionDto(null, AuthenticationConstant.ROLE_ADMIN, userDto.getId()));
		
		userDto = new UserDto();
		userDto.setUserkey("mod");
		userDto.setPassword(encodedPassword);
		userDto.setStatus("active");
		userDto.setLoginMethodDto(new LoginMethodDto(1, null, null, null));
		
		userDto = userService.add(userDto);
		globalPermissionService.save(new GlobalPermissionDto(null, AuthenticationConstant.ROLE_MOD, userDto.getId()));
		
		userDto = new UserDto();
		userDto.setUserkey("user");
		userDto.setPassword(encodedPassword);
		userDto.setStatus("active");
		userDto.setLoginMethodDto(new LoginMethodDto(1, null, null, null));
		
		userDto = userService.add(userDto);
		globalPermissionService.save(new GlobalPermissionDto(null, AuthenticationConstant.ROLE_USER, userDto.getId()));
		
		
		/*
		 * Below is the drafted code used to testing function
		 */
		
//		GroupServiceImpl groupService = ctx.getBean(GroupServiceImpl.class);
//		groupService.findOne(9999);
		
	}

}
