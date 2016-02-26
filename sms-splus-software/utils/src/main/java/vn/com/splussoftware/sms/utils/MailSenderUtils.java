package vn.com.splussoftware.sms.utils;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderUtils {

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties mailProperties = new Properties();

		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailSender.setJavaMailProperties(mailProperties);
		mailSender.setHost("smtp04.mailhostingbox.com");
		mailSender.setPort(25);
		mailSender.setProtocol("smtp");
		mailSender.setUsername("buupv@splus-software.com.vn");
		mailSender.setPassword("buupv12345");

		return mailSender;
	}
}
