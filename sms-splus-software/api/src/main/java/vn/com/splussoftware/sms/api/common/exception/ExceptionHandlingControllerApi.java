package vn.com.splussoftware.sms.api.common.exception;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

import vn.com.splussoftware.sms.model.exception.AppException;
import vn.com.splussoftware.sms.utils.dto.LoginFormDto;
import vn.com.splussoftware.sms.utils.dto.RestErrorDto;

@Controller
@ControllerAdvice
public class ExceptionHandlingControllerApi {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingControllerApi.class);
	
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RestErrorDto handleException(RuntimeException e) {
		
		logger.error(e.getMessage(), e);
		
		RestErrorDto error = new RestErrorDto();
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setDeveloperMessage(e.getMessage());
		error.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		return error;
	}
	
	@ExceptionHandler(AppException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public RestErrorDto handleAppException(AppException e) {
		
		logger.error(e.getMessage(), e);
		
		RestErrorDto error = new RestErrorDto();
		error.setStatus(e.getStatus());
		error.setCode(e.getStatus());
		error.setDeveloperMessage(e.getDeveloperMessage());
		error.setMessage(e.getUserMessage());
		error.setError(ExceptionUtils.getRootCauseMessage(e.getCause()));
		return error;
	}
	
	@ExceptionHandler({ResourceAccessException.class, UnauthorizedClientException.class, AccessDeniedException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String showLoginPage(Model model, HttpServletRequest request, Exception ex) {
		
		Enumeration<String> headers = request.getHeaders("Accept");
		while (headers.hasMoreElements()) {
			String value = headers.nextElement();
			if (value.contains("text/html")) {
				logger.error("Show login page due to unauthorized exception");
				
				model.addAttribute("loginFormDto", new LoginFormDto());
				
				return "login";
			}
		}
			
		throw new AppException(HttpStatus.UNAUTHORIZED.value(), "Access is denied", "Access is denied");
	}
	
//	@ExceptionHandler(UnauthorizedUIClientException.class)
//	public String showLoginPage(Model model, UnauthorizedUIClientException ex) {
//		logger.error("Unauthorzied exception at UI", ex);
//		
//		model.addAttribute("loginFormDto", new LoginFormDto());
//		
//		return "login";
//	}

}
