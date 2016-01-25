package vn.com.splussoftware.sms.utils.dto;

import java.io.Serializable;

import org.dozer.Mapping;

import lombok.Data;

@Data
public class UserDto implements Serializable {
	@Mapping("id")
	private Integer id;
	
	@Mapping("username")
	private String username;
	
	@Mapping("password")
	private String password;
	
	@Mapping("email")
	private String email;
	
	@Mapping("role")
	private String role;
	
	@Mapping("userLoginType")
	private String userLoginType;
}
