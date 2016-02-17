package vn.com.splussoftware.sms.utils.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;

import vn.com.splussoftware.sms.model.entity.auth.LoginMethodEntity;
import vn.com.splussoftware.sms.model.exception.AppException;
import vn.com.splussoftware.sms.model.repository.auth.LoginMethodRepository;
import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;

@Component
public class LoginMethodServiceValidator {
	
	@Autowired
	private LoginMethodRepository loginMethodRepository;
	
	@Autowired
	private LDAPHelper ldapHelper;
	
	public void validateBeforeAdding(LoginMethodDto loginMethodDto, String username, String password) {
		try {
			ldapHelper.authenticateLDAPUser(loginMethodDto.getUrl(), loginMethodDto.getId(), username, password);
		} catch (InternalAuthenticationServiceException ex) {
			throw new AppException(HttpStatus.UNAUTHORIZED.value(), "The LDAP server " + loginMethodDto.getUrl() + " is unauthorized.", null);
		}
		
		if (loginMethodRepository.findByPriority(loginMethodDto.getPriority()) != null) {
			throw new AppException(HttpStatus.CONFLICT.value(), "The loginMethod.priority " + loginMethodDto.getPriority() + " already exists.", null, null);
		}
		
		LoginMethodEntity loginMethod = loginMethodRepository.findByUrl(loginMethodDto.getUrl());
		
		if (loginMethod != null) {
			throw new AppException(HttpStatus.CONFLICT.value(), "The loginMethod.url " + loginMethodDto.getUrl() + " has already existed.", 
					"The login method [id = " + loginMethod.getId() + "] also has url " + loginMethodDto.getUrl(), 
					null);
		}
	}
}
