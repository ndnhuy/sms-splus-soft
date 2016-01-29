package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.utils.dto.UserDto;

public interface UserService {
	List<UserDto> findAll();
	UserDto findByUsername(String username);
	UserDto add(UserDto userDto);
}
