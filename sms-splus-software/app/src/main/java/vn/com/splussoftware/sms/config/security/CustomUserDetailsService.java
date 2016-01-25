package vn.com.splussoftware.sms.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.WebUserEntity;
import vn.com.splussoftware.sms.model.repository.UserRepository;
import vn.com.splussoftware.sms.utils.LDAPHelper;

/**
 * 
 * @author HuyNDN
 * 
 * @see src/main/resources/application.properties
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	public static String loginPassword = null;

	@Autowired
	private UserRepository userRepository;

	@Value("${ldap-url}")
	private String ldapUrl;

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
		WebUserEntity user = userRepository.findByUsername(username);
		
		if (user == null) {
			user = LDAPHelper.authenticateLDAPUser(ldapUrl, username, loginPassword);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().toUpperCase()));
		
		
		return new UserRepositoryUserDetails(user);
	}

//	private WebUser authenticateLDAPUser(String username) {
//		LdapContextSource contextSource = new LdapContextSource();
//        contextSource.setUrl(ldapUrl);
//        contextSource.setBase(ldapBase);
//        contextSource.setUserDn(username);
//        contextSource.setPassword(loginPassword);
//        contextSource.afterPropertiesSet();
//
//        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
//        try {
//			ldapTemplate.afterPropertiesSet();
//		} catch (Exception e) {
//		}
//
//        // Perform the authentication.
//        Filter filter = new EqualsFilter("sAMAccountName", "huyndn");
//        
//        try {
//        	boolean authed = ldapTemplate.authenticate("CN=Users",
//    	            filter.encode(),
//    	            loginPassword);
//        } catch (Exception e) {
//        	throw new RuntimeException("Access to AD fail caused by wrong username or password");
//        }
//    	
//        WebUser user = new WebUser();
//        user = new WebUser();
//        user.setUsername(username);
//        user.setPassword(loginPassword);
//        user.setRole("role_user");
//        
//        return user;
//	}

	private final static class UserRepositoryUserDetails extends WebUserEntity implements UserDetails {

		private static final long serialVersionUID = 1L;

		private UserRepositoryUserDetails(WebUserEntity user) {
			super(user);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(getRole().toUpperCase()));
			return authorities;
		}

		@Override
		public String getUsername() {
			return super.getUsername();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

	}

}
