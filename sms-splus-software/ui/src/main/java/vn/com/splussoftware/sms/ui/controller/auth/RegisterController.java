package vn.com.splussoftware.sms.ui.controller.auth;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant.LoginType;
import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;
import vn.com.splussoftware.sms.utils.dto.RegisterFormDto;
import vn.com.splussoftware.sms.utils.dto.UserDto;
import vn.com.splussoftware.sms.utils.service.UserService;

@Controller
public class RegisterController {
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegisterPage(RegisterFormDto registerFormDto) {
		logger.info("Show registration form");
				
		return "auth/register";
	}
	
	@RequestMapping(value="/register-error", method=RequestMethod.GET)
	public String loginError(Model model, @RequestParam("errorMessage") String errorMessage, RegisterFormDto registerFormDto) {
		model.addAttribute("error", errorMessage);
		return "auth/register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@Valid RegisterFormDto registerFormDto, BindingResult bindingResult, Model model) {
		logger.info("Register user with username '{}'", registerFormDto.getUsername());
		
		
		if (bindingResult.hasErrors()) {
			return "auth/register";
		}
		
		UserDto userDto = new UserDto();
		userDto.setUserkey(registerFormDto.getUsername());
		userDto.setPassword(registerFormDto.getPassword());
		userDto.setStatus(AuthenticationConstant.USER_STATUS_ACTIVE);
		userDto.setLoginMethodDto(new LoginMethodDto(LoginType.normal.getId(), null, null, null));
		
		try {
			userService.add(userDto);
			
		} catch (EntityExistsException ex) {
			
			return "redirect:/register-error?errorMessage=" + ex.getMessage();
		}
		
		
		return "redirect:/login";
	}
}
