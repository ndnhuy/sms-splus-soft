package vn.com.splussoftware.sms.utils.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	
	@Size(min=1, max=30)
	@NotNull
	@Mapping("loginType")
	private String loginType;
	
	@Size(min=1, max=30)
	@NotNull
	@Mapping("url")
	private String url;
	
	@NotNull
	@Mapping("priority")
	private Integer priority;
}
