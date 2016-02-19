package vn.com.splussoftware.sms.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.splussoftware.sms.model.entity.auth.SMSGroupEntity;
import vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity;
import vn.com.splussoftware.sms.model.repository.auth.GlobalPermissionRepository;
import vn.com.splussoftware.sms.model.repository.auth.PermissionRepository;
import vn.com.splussoftware.sms.model.repository.auth.UserRepository;
import vn.com.splussoftware.sms.utils.AuthenticationFacade;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant.Permission;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant.PermissionTargetType;


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
	
	public boolean checkPermission(Integer userId, PermissionTargetType targetType, Integer targetId, Permission permission) {		
		// Start filtering permission
		if (permissionRepository.findByUserIdAndTargetTypeAndTargetIdAndPermission(userId, targetType.toString(), targetId, permission.toString()) != null) {
			return true;
		}
		else if (permissionRepository.findByUserIdAndTargetTypeAndTargetIdAndPermission(userId, null, targetId, permission.toString()) != null) {
			return true;
		}
		else if (permissionRepository.findByUserIdAndTargetTypeAndTargetIdAndPermission(userId, null, null, permission.toString()) != null) {
			return true;
		}
		else if (permissionRepository.findByUserIdAndTargetTypeAndTargetIdAndPermission(userId, targetType.toString(), null, permission.toString()) != null) {
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
	
	public boolean checkPermissionForCurrentUser(PermissionTargetType targetType, Integer targetId, Permission permission) {
		return checkPermission(auth.getCurrentLoggedInUser().getId(), targetType, targetId, permission);
	}
	
	public boolean checkGroupPermission(Integer groupId, PermissionTargetType targetType, Integer targetId, Permission permission) {
		if (permissionRepository.findByGroupIdAndTargetTypeAndTargetIdAndPermission(groupId, targetType.toString(), targetId, permission.toString()) != null) {
			return true;
		}
		else if (permissionRepository.findByGroupIdAndTargetTypeAndTargetIdAndPermission(groupId, null, targetId, permission.toString()) != null) {
			return true;
		}
		else if (permissionRepository.findByGroupIdAndTargetTypeAndTargetIdAndPermission(groupId, null, null, permission.toString()) != null) {
			return true;
		}
		else if (permissionRepository.findByGroupIdAndTargetTypeAndTargetIdAndPermission(groupId, targetType.toString(), null, permission.toString()) != null) {
			return true;
		}
		
		return false;
	}
	
}
