package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;

public interface LoginMethodService {
	List<LoginMethodDto> findAll();
	List<LoginMethodDto> findAllByOrderByPriorityDesc();
	
	LoginMethodDto addLoginMethod(LoginMethodDto dto, String username, String password);
	void deleteLoginMethod(Integer id);
}
