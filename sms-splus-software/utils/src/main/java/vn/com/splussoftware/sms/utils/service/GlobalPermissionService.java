package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.utils.dto.GlobalPermissionDto;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionUIDto;

public interface GlobalPermissionService {
	List<GlobalPermissionDto> findAll();
	List<GlobalPermissionUIDto> getListOfUsersWithGlobalPermission();
	void save(GlobalPermissionDto globalPermissionDto);
}
