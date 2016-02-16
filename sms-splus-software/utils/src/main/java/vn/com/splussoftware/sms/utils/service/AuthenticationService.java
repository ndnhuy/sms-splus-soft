package vn.com.splussoftware.sms.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.splussoftware.sms.model.repository.login.GlobalPermissionRepository;
import vn.com.splussoftware.sms.utils.AuthenticationFacade;

@Component
public class AuthenticationService {
	
	@Autowired
	private AuthenticationFacade auth;
	
	@Autowired
	private GlobalPermissionRepository globalPermissionRepo;
	
	public boolean checkGlobalPermission(Integer userId, String permission) {
		return globalPermissionRepo.existsByUserIdAndPermission(userId, permission);
	}
	
	public boolean checkGlobalPermissionForCurrentUser(String permission) {
		return globalPermissionRepo.existsByUserIdAndPermission(auth.getCurrentLoggedInUser().getId(), permission);
	}
}
