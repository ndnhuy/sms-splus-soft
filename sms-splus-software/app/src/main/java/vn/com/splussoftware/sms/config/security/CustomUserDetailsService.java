package vn.com.splussoftware.sms.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.api.service.UserApiService;
import vn.com.splussoftware.sms.model.entity.auth.GlobalPermissionEntity;
import vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity;
import vn.com.splussoftware.sms.model.exception.LdapAuthenticationException;
import vn.com.splussoftware.sms.model.repository.auth.UserRepository;
import vn.com.splussoftware.sms.utils.constant.LoginMethodTypeConstant;
import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;
import vn.com.splussoftware.sms.utils.service.LoginMethodService;
import vn.com.splussoftware.sms.utils.validator.LDAPHelper;


/**
 * Loading user-specific data for authenticating
 * <p>
 * By far, the user data is from custom database and LDAP.
 * Google, Facebook, Twitter OAuth2 will be implemented in the future.
 * 
 * @author HuyNDN
 * created on Feb 19, 2016
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	
	public static String loginPassword = null;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginMethodService loginMethodService;
	
	@Autowired
	private LDAPHelper ldapHelper;


	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Authentication p = SecurityContextHolder.getContext().getAuthentication();
		
		logger.debug("Load user by username for authenticating");
		
		// The user is authenticated by pre-defined login methods in database
		List<LoginMethodDto> dtoLoginMethods = loginMethodService.findAllByOrderByPriorityDesc();
		if (dtoLoginMethods == null || dtoLoginMethods.isEmpty()) {
			
			UsernameNotFoundException ex = new UsernameNotFoundException("User '" + username + "' not found");
			
			logger.error("Not found any authentication source", ex);
			throw ex;
		}
		
		UserDetails user = authenticateUserForEachOfLoginMethodsOrderdByDecreasingPriority(username, dtoLoginMethods);
		if (user == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}
		
		return user;
	}
	
	/**
	 * The user will be authenticated by using list of login methods sorted by priority in decreasing order.
	 * 
	 * @param username the username of user wished to be authenticated
	 * @param dtoLoginMethods a list of login methods sorted by priority in decreasing order.
	 * @return UserDetails
	 */
	private UserDetails authenticateUserForEachOfLoginMethodsOrderdByDecreasingPriority(String username,
			List<LoginMethodDto> dtoLoginMethods) {
		
		SMSUserEntity user = null;
		for (LoginMethodDto dto : dtoLoginMethods) {
			/*
			 *  Using username to authenticate user for each of login methods
			 */
			switch (dto.getLoginType()) {
			case LoginMethodTypeConstant.LOGIN_METHOD_TYPE_NORMAL:
				
				logger.debug("Authenticating username/password for login-method-type {}", 
						LoginMethodTypeConstant.LOGIN_METHOD_TYPE_NORMAL);
				
				user = userRepository.findByUserkey(username);
				if (user == null) {
					logger.error("Authentication fail", new UsernameNotFoundException("User '" + username + "' not found"));
				}
				break;
			case LoginMethodTypeConstant.LOGIN_METHOD_TYPE_LDAP:
				
				logger.debug("Authenticating username/password for login-method-type '{}' '{}'", 
						LoginMethodTypeConstant.LOGIN_METHOD_TYPE_LDAP, dto.getUrl());
				
				try {
					user = ldapHelper.authenticateLDAPUser(dto.getUrl(), dto.getId(), username, loginPassword);
				} catch (LdapAuthenticationException ex) {
					logger.error("Authentication fail from '{}'", dto.getUrl());
				}
				
				break;
			}
			
			
			if (user != null) {
				/* 
				 * If there's any login-method get successful authentication, then grant authority for that user. 
				 */
				logger.info("Get successful authentication from " + dto.getLoginType() + " for username '{}'", username);
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				
				List<GlobalPermissionEntity> entityGlobalPermissions = user.getGlobalPermissions();
				if (entityGlobalPermissions == null || entityGlobalPermissions.isEmpty()) {
					// There's no any global permissions granted for this user, 
					// then we treat it like a normal user.
					authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
					return new UserRepositoryUserDetails(user, authorities);
				}
				
				
				for (GlobalPermissionEntity e : entityGlobalPermissions) {
					String permission = e.getPermission();
					
					// Make sure the permission/authority in right format
					// (the prefix must be 'ROLE_')
					if (!permission.startsWith("ROLE_")) {
						permission = "ROLE_" + permission;
					}

					authorities.add(new SimpleGrantedAuthority(permission.toUpperCase()));
				}
				
				return new UserRepositoryUserDetails(user, authorities);
				
			}
		}
		
		return null;
		
	}


	private final static class UserRepositoryUserDetails extends SMSUserEntity implements UserDetails {

		private static final long serialVersionUID = 1L;
		
		private List<GrantedAuthority> authorities = null;
		
		private UserRepositoryUserDetails(SMSUserEntity user, List<GrantedAuthority> authorities) {
			super(user);
			this.authorities = authorities;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		@Override
		public String getUsername() {
			return super.getUserkey();
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
