package vn.com.splussoftware.sms.api.service;

import java.util.HashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.com.splussoftware.sms.utils.dto.RestSuccessDto;
import vn.com.splussoftware.sms.utils.dto.UserDto;
import vn.com.splussoftware.sms.utils.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApiService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserApiService.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@RequestMapping(method=RequestMethod.GET)
	public RestSuccessDto getAllUsers() {
		logger.info("Get all users");
		
		return new RestSuccessDto(HttpStatus.OK.value(), userService.findAll());
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public RestSuccessDto addUser(@RequestBody UserDto userDto) {
		logger.info("Add user [username: {}]", userDto.getUserkey());
		
		return new RestSuccessDto(HttpStatus.CREATED.value(), userService.add(userDto));
	}
	
	@RequestMapping(value="/test", method=RequestMethod.POST)
	public RestSuccessDto test(@RequestBody HashMap<String, String> map) {
		
		
		
		return new RestSuccessDto(HttpStatus.CREATED.value(), map);

	}
	
}
