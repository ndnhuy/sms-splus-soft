package vn.com.splussoftware.sms.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.splussoftware.sms.model.entity.auth.SMSGroupEntity;
import vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity;
import vn.com.splussoftware.sms.model.repository.auth.GlobalPermissionRepository;
import vn.com.splussoftware.sms.model.repository.auth.PermissionRepository;
import vn.com.splussoftware.sms.model.repository.auth.UserRepository;
import vn.com.splussoftware.sms.utils.AuthenticationFacade;


/**
 *
 * This object take responsible for checking permission of specific user.
 *
 * @author HuyNDN
 *
 */
@Component
public class AuthenticationService {
	
	@Autowired
	private AuthenticationFacade auth;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private GlobalPermissionRepository globalPermissionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean checkGlobalPermission(Integer userId, String permission) {
		return globalPermissionRepository.existsByUserIdAndPermission(userId, permission);
	}
	
	public boolean checkGlobalPermissionForCurrentUser(String permission) {
		return globalPermissionRepository.existsByUserIdAndPermission(auth.getCurrentLoggedInUser().getId(), permission);
	}
	
	public boolean checkPermission(Integer userId, String targetType, Integer targetId, String permission) {		
		// Start filtering permission
		if (permissionRepository.findByUserIdAndTargetTypeAndTargetIdAndPermission(userId, targetType, targetId, permission) != null) {
			return true;
		}
		else if (permissionRepository.findByUserIdAndTargetTypeAndTargetIdAndPermission(userId, null, targetId, permission) != null) {
			return true;
		}
		else if (permissionRepository.findByUserIdAndTargetTypeAndTargetIdAndPermission(userId, null, null, permission) != null) {
			return true;
		}
		else if (permissionRepository.findByUserIdAndTargetTypeAndTargetIdAndPermission(userId, targetType, null, permission) != null) {
			return true;
		}
		
		// There's no any matches, check each group this user has belonged to.
		SMSUserEntity user = userRepository.findOne(userId);
		if (user == null) {
			return false;
		}
		
		if (user.getGroups() == null || user.getGroups().isEmpty()) {
			return false;
		}
		
		
		for (SMSGroupEntity g : user.getGroups()) {
			if (checkGroupPermission(g.getId(), targetType, targetId, permission)) {
				return true;
			}
		}
		
		return false;
	
	}
	
	public boolean checkPermissionForCurrentUser(String targetType, Integer targetId, String permission) {
		return checkPermission(auth.getCurrentLoggedInUser().getId(), targetType, targetId, permission);
	}
	
	public boolean checkGroupPermission(Integer groupId, String targetType, Integer targetId, String permission) {
		if (permissionRepository.findByGroupIdAndTargetTypeAndTargetIdAndPermission(groupId, targetType, targetId, permission) != null) {
			return true;
		}
		else if (permissionRepository.findByGroupIdAndTargetTypeAndTargetIdAndPermission(groupId, null, targetId, permission) != null) {
			return true;
		}
		else if (permissionRepository.findByGroupIdAndTargetTypeAndTargetIdAndPermission(groupId, null, null, permission) != null) {
			return true;
		}
		else if (permissionRepository.findByGroupIdAndTargetTypeAndTargetIdAndPermission(groupId, targetType, null, permission) != null) {
			return true;
		}
		
		return false;
	}
	
}
