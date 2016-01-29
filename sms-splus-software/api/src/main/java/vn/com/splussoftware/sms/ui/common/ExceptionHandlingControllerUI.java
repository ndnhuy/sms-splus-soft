package vn.com.splussoftware.sms.ui.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.com.splussoftware.sms.utils.dto.LoginFormDto;

@Controller
@ControllerAdvice
public class ExceptionHandlingControllerUI {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingControllerUI.class);
	
	@ExceptionHandler(UnauthorizedUIClientException.class)
	public String showLoginPage(Model model, UnauthorizedUIClientException ex) {
		logger.error("Unauthorzied exception at UI", ex);
		
		model.addAttribute("loginFormDto", new LoginFormDto());
		
		return "login";
	}
}
