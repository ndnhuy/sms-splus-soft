package vn.com.splussoftware.sms.utils.dto.loginMethod;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;
import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;

@Data
public class LoginMethodForm {
	
	@Valid
	private List<LoginMethodDto> loginMethods;
}
