package vn.com.splussoftware.sms.utils.dto;

import org.dozer.Mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginMethodDto {

	@Mapping("id")
	private Integer id;
	
	@Mapping("loginType")
	private String loginType;
	
	@Mapping("url")
	private String url;
	
	@Mapping("priority")
	private Integer priority;
}
