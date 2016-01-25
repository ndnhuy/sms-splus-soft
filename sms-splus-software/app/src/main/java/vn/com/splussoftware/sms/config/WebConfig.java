package vn.com.splussoftware.sms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import vn.com.splussoftware.sms.config.security.OAuth2Interceptor;

public class WebConfig extends WebMvcConfigurerAdapter {
	
	
	@Autowired
	private OAuth2Interceptor initialSampleInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		
//		registry.addInterceptor(initialSampleInterceptor);
	}
}
