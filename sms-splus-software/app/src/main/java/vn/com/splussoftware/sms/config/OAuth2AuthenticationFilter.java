package vn.com.splussoftware.sms.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 
 * Implementing {@code Remember me} function for OAuth2.
 * This filter is to intercept the incoming HTTP request and modify the headers.
 * 
 * @author HuyNDN
 * 
 * created on Feb 19, 2016
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OAuth2AuthenticationFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if (logger.isDebugEnabled())
			logger.debug("Firing filter to check cookie in request's header for authenticating");
		
		CustomHttpServletRequest customRequest = new CustomHttpServletRequest((HttpServletRequest) request);
		
		if (logger.isDebugEnabled())
			logger.debug("Find cookie named 'access_token' in request's header");
		
		/*
		 * Find the 'access_token' in cookies then put it into header 
		 * to pass the authentication (OAuth2)
		 */
		Cookie[] cookies = customRequest.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("access_token")) {
					logger.debug("Put the found 'access_token'" + cookie.getValue() + " into the request's header with key is 'Authorization'");
					
					customRequest.addHeader("Authorization", "bearer " + cookie.getValue());
					
					chain.doFilter(customRequest, response);
					return;
				}
			}
		}
		
		logger.debug("None found any 'access_token' in cookies");
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		
	}
	
}
