package vn.com.splussoftware.sms.utils.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.LoginMethodEntity;
import vn.com.splussoftware.sms.model.repository.LoginMethodRepository;
import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;
import vn.com.splussoftware.sms.utils.validator.LoginMethodServiceValidator;

@Service
public class LoginMethodServiceImpl implements LoginMethodService {

	private static final Logger logger = LoggerFactory.getLogger(LoginMethodServiceImpl.class);
	
	@Autowired
	private LoginMethodRepository loginMethodRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Autowired
	private LoginMethodServiceValidator validator;
	
	@Override
	public List<LoginMethodDto> findAll() {
		if (logger.isDebugEnabled())
			logger.debug("Find all login methods");
		
		
		List<LoginMethodEntity> entityLoginMethods = loginMethodRepository.findAll();
		
		List<LoginMethodDto> dtoLoginMethods = new ArrayList<LoginMethodDto>();
		for (LoginMethodEntity loginMethod : entityLoginMethods) {
			dtoLoginMethods.add(mapper.map(loginMethod, LoginMethodDto.class));
		}
		
		return dtoLoginMethods;
	}

	@Override
	public LoginMethodDto addLoginMethod(LoginMethodDto dto, String username, String password) {
		
		validator.validateBeforeAdding(dto, username, password);
		
		LoginMethodEntity entityLoginMethod = mapper.map(dto, LoginMethodEntity.class);
		loginMethodRepository.saveAndFlush(entityLoginMethod);
		
		return mapper.map(entityLoginMethod, LoginMethodDto.class);
	}

	@Override
	public void deleteLoginMethod(Integer id) {
		loginMethodRepository.delete(id);
	}
	
}
