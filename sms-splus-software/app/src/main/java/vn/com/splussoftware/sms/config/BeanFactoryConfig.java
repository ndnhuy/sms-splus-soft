package vn.com.splussoftware.sms.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import vn.com.splussoftware.sms.model.commonutils.ApplicationContextProvider;

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
	
	/**
	 * Enable reading message from message/messages.properties.
	 * 
	 * @return
	 * @author HuyNDN created on Feb 26, 2016
	 */
	 @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
      ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
      messageBundle.setBasenames("classpath:message/messages");
      messageBundle.setDefaultEncoding("UTF-8");
      return messageBundle;
    }
	 
	 @Bean
	public ApplicationContextProvider applicationContextProvider() {
		return new ApplicationContextProvider();
	}
}
