package vn.com.splussoftware.sms.utils;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import vn.com.splussoftware.sms.utils.dto.UserDto;
import vn.com.splussoftware.sms.utils.service.UserService;

@Component
public class AuthenticationFacade {
	
	@Autowired
	private UserService userService;
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public UserDto getCurrentLoggedInUser() {
		UserDto userDto = null;
		try {
			userDto = userService.findByUsername(getAuthentication().getName());
		} catch (EntityNotFoundException ex) {
			userDto = new UserDto();
			userDto.setUsername(getAuthentication().getName());
		}
		return userDto;
	}
}
