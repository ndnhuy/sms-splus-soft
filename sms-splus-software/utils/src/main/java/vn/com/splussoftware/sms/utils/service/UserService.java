package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.utils.dto.UserDto;

public interface UserService {
	List<UserDto> findAll();
	UserDto findByUserkey(String userkey);
	UserDto findByUserkeyAndLoginMethodUrl(String userkey, String loginMethodUrl);
	UserDto add(UserDto userDto);
}
