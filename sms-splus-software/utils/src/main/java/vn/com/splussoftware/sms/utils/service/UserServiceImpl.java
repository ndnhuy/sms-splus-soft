package vn.com.splussoftware.sms.utils.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.WebUserEntity;
import vn.com.splussoftware.sms.model.repository.UserRepository;
import vn.com.splussoftware.sms.utils.dto.UserDto;

@Service
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
		
		
		List<WebUserEntity> entityUsers = userRepository.findAll();
		
		List<UserDto> dtoUsers = new ArrayList<UserDto>();
		for (WebUserEntity user : entityUsers) {
			dtoUsers.add(mapper.map(user, UserDto.class));
		}
		
		return dtoUsers;
	}

	@Override
	public UserDto add(UserDto userDto) {
		if (logger.isDebugEnabled())
			logger.debug("Add user [username: {}]", userDto.getUsername());
		
		WebUserEntity savedWebUser = userRepository.save(mapper.map(userDto, WebUserEntity.class));
		
		return mapper.map(savedWebUser, UserDto.class);
	}

}
