package vn.com.splussoftware.sms.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.com.splussoftware.sms.utils.AuthenticationFacade;
import vn.com.splussoftware.sms.utils.dto.RestSuccessDto;

@RestController
@RequestMapping("/api/me")
public class ProfileController {
	
	@Autowired
	AuthenticationFacade auth;
	
	@RequestMapping(method=RequestMethod.GET)
	public RestSuccessDto getMe() {
		
		return new RestSuccessDto(HttpStatus.OK.value(), auth.getCurrentLoggedInUser());
	}
}
