package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.model.entity.auth.PermissionEntity;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant.Permission;
import vn.com.splussoftware.sms.utils.dto.PermissionDto;

/**
 * The interface which performs business logic for {@link Permission}
 * 
 * @see {@link PermissionEntity}, {@link PermissionDto}
 * 
 * @author HuyNDN
 * created on Feb 21, 2016
 */
public interface PermissionService {
	List<PermissionDto> findAll();
	void save(PermissionDto permissionDto);
	void delete(Integer permissionId);
}
