package vn.com.splussoftware.sms.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
	@RequestMapping("/mail")
	// @ResponseStatus(HttpStatus.CREATED)
	SimpleMailMessage send() {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("phungvan_buu@yahoo.com.vn");
		// mailMessage.setReplyTo("someone@localhost");
		mailMessage.setFrom("tuanhma@splus-software.com.vn");
		mailMessage.setSubject("Lorem ipsum");
		mailMessage.setText("Lorem ipsum dolor sit amet [...]");
		MailSenderUtils mailconfiguration = new MailSenderUtils();
		JavaMailSender javaMailSender = mailconfiguration.javaMailSender();
		javaMailSender.send(mailMessage);
		return mailMessage;
	}
}
