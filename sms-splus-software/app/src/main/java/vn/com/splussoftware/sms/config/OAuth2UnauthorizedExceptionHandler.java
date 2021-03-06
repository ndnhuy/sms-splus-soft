package vn.com.splussoftware.sms.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant;

/**
 * 
 * Handling the exception thrown when user login failed.
 * 
 * @author HuyNDN
 * created on Feb 19, 2016
 */
public class OAuth2UnauthorizedExceptionHandler extends OAuth2AuthenticationEntryPoint {
	
	private static final Logger logger = LoggerFactory.getLogger(OAuth2UnauthorizedExceptionHandler.class);

	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		// Get all headers 'Accept' from request
		Enumeration<String> headers = request.getHeaders("Accept");
		while (headers.hasMoreElements()) {
			String value = headers.nextElement();
			
			/*
			 * if user login from browser, remove all the cookies and 
			 * redirect them to login page
			 */
			if (value.contains("text/html")) {
				
				logger.debug("Redirect to login page");
				
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
				
				response.sendRedirect(AuthenticationConstant.SERVER_URL + "/login");
				return;
			}
		}
		
		// user login from ajax, return JSON message.
		super.commence(request, response, authException);

	}
}
