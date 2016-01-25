package vn.com.splussoftware.sms.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactoryConfig {
	
	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper();
	}
}
