package vn.com.splussoftware.sms.utils.validator;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.core.AuthenticationException;

import vn.com.splussoftware.sms.model.entity.login.SMSUserEntity;
import vn.com.splussoftware.sms.model.exception.LdapAuthenticationException;

/**
 * 
 * Hepler class for working with LDAP.
 * 
 * @author HuyNDN
 * @see CustomUserDetailsService, LoginMethodServiceImpl
 */
public class LDAPHelper {
	
	public static SMSUserEntity authenticateLDAPUser(String ldapUrl, String username, String loginPassword) {
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
    	
        SMSUserEntity user = new SMSUserEntity();
        user = new SMSUserEntity();
        user.setUserkey(username);
        user.setPassword(loginPassword);
        
        //TODO need to grant permission here (default is ROLE_USER)
        //user.setRole("role_user");
        
        return user;
	}
}
