package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.model.entity.auth.GlobalPermissionEntity;
import vn.com.splussoftware.sms.model.repository.auth.GlobalPermissionRepository;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionDto;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionUIDto;

/**
 * The interface which performs business logic for {@link GlobalPermissionEntity global permission}.
 * 
 * @see {@link PermissionGrantingController} called this object to represent all {@link GlobalPermissionEntity global permission} retrived from {@link GlobalPermissionRepository} on {@code permission-granting.html} page
 * <p>
 * @see {@link GlobalPermissionEntity}, {@link GlobalPermissionDto}
 * @author HuyNDN
 * created on Feb 21, 2016
 */
public interface GlobalPermissionService {
	List<GlobalPermissionDto> findAll();
	
	/**
	 * Get the list of {@link GlobalPermissionEntity} and convert them to {@link GlobalPermissionDto}
	 * @return the list of {@link GlobalPermissionDto} converted from {@link GlobalPermissionEntity}
	 */
	List<GlobalPermissionUIDto> getListOfUsersWithGlobalPermission();
	void save(GlobalPermissionDto globalPermissionDto);
	void delete(Integer id);
	void deleteAll();
	Integer deleteByUserId(Integer userId);
}
