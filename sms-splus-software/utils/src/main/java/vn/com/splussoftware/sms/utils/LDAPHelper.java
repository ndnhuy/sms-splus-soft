package vn.com.splussoftware.sms.utils;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.support.LdapUtils;

import vn.com.splussoftware.sms.model.entity.WebUserEntity;

/**
 * 
 * Hepler class for working with LDAP.
 * 
 * @author HuyNDN
 * @see CustomUserDetailsService, LoginMethodServiceImpl
 */
public class LDAPHelper {
	
	public static WebUserEntity authenticateLDAPUser(String ldapUrl, String username, String loginPassword) {
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
        	throw new RuntimeException("Access to AD fail caused by wrong username or password");
        }
    	
        WebUserEntity user = new WebUserEntity();
        user = new WebUserEntity();
        user.setUsername(username);
        user.setPassword(loginPassword);
        user.setRole("role_user");
        
        return user;
	}
}
