package vn.com.splussoftware.sms.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactoryConfig {
	
	/**
	 * DozerBeanMapper is bean that copies data from one object to another.
	 * In this project, it's used to do the convertion between Entity and DTO.
	 */
	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper();
	}
}
