package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.utils.dto.PermissionDto;

public interface PermissionService {
	List<PermissionDto> findAll();
	void save(PermissionDto permissionDto);
}
