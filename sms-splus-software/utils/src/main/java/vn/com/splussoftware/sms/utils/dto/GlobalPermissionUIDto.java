package vn.com.splussoftware.sms.utils.dto;

import lombok.Data;

@Data
public class GlobalPermissionUIDto {
	private Integer id;
	private String permission;
	private Integer userId;
	private String userkey;
}
