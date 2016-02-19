package vn.com.splussoftware.sms.utils.dto;

import org.dozer.Mapping;

import lombok.Data;

@Data
public class PermissionDto {
	@Mapping("id")
	private Integer id;
	
	@Mapping("targetType")
	private String targetType;
	
	@Mapping("targetId")
	private Integer targetId;
	
	@Mapping("permission")
	private String permission;
	
	@Mapping("user.userkey")
	private String userkey;
	
	@Mapping("user.id")
	private Integer userId;
	
	@Mapping("group.groupName")
	private String groupName;
	
	@Mapping("group.id")
	private Integer groupId;
}	
