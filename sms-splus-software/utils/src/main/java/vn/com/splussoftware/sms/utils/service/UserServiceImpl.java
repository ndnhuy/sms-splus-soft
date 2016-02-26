package vn.com.splussoftware.sms.utils.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity;
import vn.com.splussoftware.sms.model.repository.auth.UserRepository;
import vn.com.splussoftware.sms.utils.dto.UserDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		
		if (userRepository.findByUserkey(userDto.getUserkey()) != null) {
			EntityExistsException ex = new EntityExistsException("The username '" + userDto.getUserkey() + "' existed");
			logger.error("Username exsited", ex); 
			throw ex;
		}
		
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
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

	@Override
	public UserDto findByUserkeyAndLoginMethodUrl(String userkey, String loginMethodUrl) {
		SMSUserEntity entityUser = userRepository.findByUserkeyAndLoginMethodUrl(userkey, loginMethodUrl);
		if (entityUser == null) {
			throw new EntityNotFoundException(String.format("User not found with userkey '%s' and login-method-url '%s'", userkey, loginMethodUrl));
		}
		
		return mapper.map(userRepository.findByUserkeyAndLoginMethodUrl(userkey, loginMethodUrl), UserDto.class);
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}


	/**
	 * Delete user by userkey
	 * 
	 * @param userkey
	 * @return id of user has been deleted. return 0 if delete fail
	 */
	@Override
	public Integer deleteByUserkey(String userkey) {
		SMSUserEntity user = userRepository.findByUserkey(userkey);
		if (user != null) {
			userRepository.deleteByUserkey(userkey);
			return user.getId();
		}
		return 0;
	}

}
