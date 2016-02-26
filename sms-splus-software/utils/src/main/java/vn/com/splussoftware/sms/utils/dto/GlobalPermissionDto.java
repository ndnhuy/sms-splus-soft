package vn.com.splussoftware.sms.utils.dto;

import org.dozer.Mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalPermissionDto {
	
	@Mapping("id")
	private Integer id;
	
	@Mapping("permission")
	private String permission;
	
	@Mapping("userId")
	private Integer userId;
}
