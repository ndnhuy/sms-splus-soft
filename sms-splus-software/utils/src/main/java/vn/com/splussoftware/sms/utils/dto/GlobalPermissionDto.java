package vn.com.splussoftware.sms.utils.dto;

import org.dozer.Mapping;

import lombok.Data;

@Data
public class GlobalPermissionDto {
	
	@Mapping("id")
	private Integer id;
	
	@Mapping("permission")
	private String permission;
}
