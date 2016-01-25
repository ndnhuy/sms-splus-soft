package vn.com.splussoftware.sms.api.common;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import vn.com.splussoftware.sms.model.exception.AppException;
import vn.com.splussoftware.sms.utils.dto.RestErrorDto;

@RestController
@ControllerAdvice
public class ExceptionHandlingControllerApi {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingControllerApi.class);
	
	@ExceptionHandler(RuntimeException.class)
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
}
