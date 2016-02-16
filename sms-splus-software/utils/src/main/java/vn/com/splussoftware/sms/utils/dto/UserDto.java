package vn.com.splussoftware.sms.utils.dto;

import java.io.Serializable;
import java.util.List;

import org.dozer.Mapping;

import lombok.Data;

@Data
public class UserDto implements Serializable {
	@Mapping("id")
	private Integer id;
	
	@Mapping("userkey")
	private String userkey;
	
	@Mapping("password")
	private String password;
	
	
	@Mapping("status")
	private String status;
	
	@Mapping("loginMethodEntity")
	private LoginMethodDto loginMethodDto;
	
	@Mapping("globalPermissions")
	private List<GlobalPermissionDto> globalPermissions;
}
