package vn.com.splussoftware.sms.utils.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterFormDto {
	
	@Size(min=2, max=10)
	@NotNull
	private String username;
	
	@NotNull
	private String password;
}
