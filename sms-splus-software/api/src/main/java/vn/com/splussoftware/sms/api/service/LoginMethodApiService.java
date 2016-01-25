package vn.com.splussoftware.sms.api.service;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;
import vn.com.splussoftware.sms.utils.dto.RestSuccessDto;
import vn.com.splussoftware.sms.utils.service.LoginMethodService;

@RestController
@RequestMapping("/api/loginMethods")
public class LoginMethodApiService {
	private static final Logger logger = LoggerFactory.getLogger(LoginMethodApiService.class);

	
	@Autowired
	private LoginMethodService loginMethodService;
	
	@RequestMapping(method=RequestMethod.GET)
	public RestSuccessDto getAllLoginMethods() {
		logger.info("Get all login methods");
		
		return new RestSuccessDto(HttpStatus.OK.value(), loginMethodService.findAll());
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public RestSuccessDto addLoginMethods(@RequestBody LoginMethodDto loginMethodDto, 
											@RequestParam("username") String username, 
											@RequestParam("password") String password) {
		logger.info("Add login method {}", ToStringBuilder.reflectionToString(loginMethodDto));
		
		return new RestSuccessDto(HttpStatus.CREATED.value(), loginMethodService.addLoginMethod(loginMethodDto, username, password));
	}
	
	@RequestMapping(value="/{loginMethodId}", method=RequestMethod.DELETE)
	public RestSuccessDto deleteLoginMethod(@PathVariable("loginMethodId") Integer loginMethodId) {
		loginMethodService.deleteLoginMethod(loginMethodId);
		
		return new RestSuccessDto(HttpStatus.OK.value(), null, "The loginMethod [id=" + loginMethodId + "] has been deleted.");
	}
}
