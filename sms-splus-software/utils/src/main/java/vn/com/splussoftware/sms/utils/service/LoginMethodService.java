package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.model.entity.auth.LoginMethodEntity;
import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;

/**
 * The interface which performs business logic for {@link LoginMethodEntity login method}
 * 
 * @see {@link LoginMethodEntity}, {@link LoginMethodDto}
 * 
 * @author HuyNDN
 * created on Feb 21, 2016
 */
public interface LoginMethodService {
	List<LoginMethodDto> findAll();
	List<LoginMethodDto> findAllByOrderByPriorityDesc();
	
	LoginMethodDto addLoginMethod(LoginMethodDto dto, String username, String password);
	void deleteLoginMethod(Integer id);
}
