package vn.com.splussoftware.sms.utils.validator;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Component;

import vn.com.splussoftware.sms.model.entity.auth.LoginMethodEntity;
import vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity;
import vn.com.splussoftware.sms.model.exception.InvalidUserException;
import vn.com.splussoftware.sms.model.exception.LdapAuthenticationException;
import vn.com.splussoftware.sms.model.repository.auth.UserRepository;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant;
import vn.com.splussoftware.sms.utils.dto.UserDto;
import vn.com.splussoftware.sms.utils.service.UserService;

/**
 * 
 * Hepler class for comunicating with LDAP.
 * 
 * @author HuyNDN
 * @see CustomUserDetailsService, LoginMethodServiceImpl
 */

@Component
public class LDAPHelper {
	
	@Autowired
	private UserRepository userRepository;
	
	public SMSUserEntity authenticateLDAPUser(String ldapUrl, Integer loginMethodId, String username, String loginPassword) {
		LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setUserDn(username);
        contextSource.setPassword(loginPassword);
        contextSource.afterPropertiesSet();

        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
        try {
			ldapTemplate.afterPropertiesSet();
		} catch (Exception e) {
		}

        // Perform the authentication.
        Filter filter = new EqualsFilter("sAMAccountName", username);
        
        try {
        	boolean authed = ldapTemplate.authenticate(LdapUtils.emptyLdapName(),
    	            filter.encode(),
    	            loginPassword);
        } catch (Exception e) {
        	throw new LdapAuthenticationException("Access to AD fail caused by wrong username or password from '" + ldapUrl + "'");
        }
    	
        SMSUserEntity user = null;
        user = userRepository.findByUserkeyAndLoginMethodUrl(username, ldapUrl);
        if (user == null) {
        	
        	LoginMethodEntity loginMethodEntity = new LoginMethodEntity();
        	loginMethodEntity.setId(loginMethodId);
        	
        	user = new SMSUserEntity();
            user.setUserkey(username);
            user.setPassword(loginPassword);
            user.setStatus(AuthenticationConstant.USER_STATUS_ACTIVE);
            user.setLoginMethodEntity(loginMethodEntity);
            
            userRepository.saveAndFlush(user);
            return user;
        }
        
        switch (user.getStatus()) {
        case AuthenticationConstant.USER_STATUS_ACTIVE:
        	return user;
        case AuthenticationConstant.USER_STATUS_BANNED:
        	throw new InvalidUserException("This user has been banned.");
        }
        
        return null;
	}
}
