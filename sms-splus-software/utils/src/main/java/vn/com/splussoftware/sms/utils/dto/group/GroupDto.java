package vn.com.splussoftware.sms.utils.dto.group;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.dozer.Mapping;

import lombok.Data;
import vn.com.splussoftware.sms.utils.dto.UserDto;

@Data
public class GroupDto {
	
	@Mapping("id")
	private Integer id;
	
	@Size(min=2, max=10)
	@NotNull
	@Mapping("groupName")
	private String groupName;
	
	@Size(min=5, max = 100)
	@NotNull
	@Mapping("description")
	private String description;
	
	@Mapping("users")
	private List<UserDto> users;
}
