package vn.com.splussoftware.sms.utils.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.login.SMSUserEntity;
import vn.com.splussoftware.sms.model.repository.login.UserRepository;
import vn.com.splussoftware.sms.utils.dto.UserDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	public List<UserDto> findAll() {
		
		
		
		
		if (logger.isDebugEnabled())
			logger.debug("Find all users");
		
		
		List<SMSUserEntity> entityUsers = userRepository.findAll();
		
		List<UserDto> dtoUsers = new ArrayList<UserDto>();
		for (SMSUserEntity user : entityUsers) {
			dtoUsers.add(mapper.map(user, UserDto.class));
		}
		
		return dtoUsers;
	}

	@Override
	public UserDto add(UserDto userDto) {
		if (logger.isDebugEnabled())
			logger.debug("Add user [username: {}]", userDto.getUserkey());
		
		SMSUserEntity savedWebUser = userRepository.save(mapper.map(userDto, SMSUserEntity.class));
		
		return mapper.map(savedWebUser, UserDto.class);
	}

	@Override
	public UserDto findByUserkey(String username) {
		
		if (userRepository.findByUserkey(username) == null) {
			throw new EntityNotFoundException("User not found with username " + username);
		}
		
		
		return mapper.map(userRepository.findByUserkey(username), UserDto.class);
	}

}
