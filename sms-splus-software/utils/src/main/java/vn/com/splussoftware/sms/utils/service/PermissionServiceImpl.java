package vn.com.splussoftware.sms.utils.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.constant.MessagePropertiesConstant;
import vn.com.splussoftware.sms.model.entity.auth.PermissionEntity;
import vn.com.splussoftware.sms.model.repository.auth.GroupRepository;
import vn.com.splussoftware.sms.model.repository.auth.PermissionRepository;
import vn.com.splussoftware.sms.model.repository.auth.UserRepository;
import vn.com.splussoftware.sms.utils.dto.PermissionDto;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepo;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public List<PermissionDto> findAll() {
		List<PermissionEntity> entities = permissionRepo.findAll(new Sort(Sort.Direction.ASC, "id"));
		
		List<PermissionDto> dtos = new ArrayList<PermissionDto>();
		for (PermissionEntity e : entities) {
			dtos.add(mapper.map(e, PermissionDto.class));
		}
		
		return dtos;
	}

	/*
	 * HuyNDN created on Feb 26, 2016
	 */
	@Override
	public void save(PermissionDto permissionDto) {
		PermissionEntity entity = mapper.map(permissionDto, PermissionEntity.class);
		
		/*
		 * Validate
		 */
		// These 2 lines of code is to avoid the exception:
			// org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - 
			// save the transient instance before flushing : vn.com.splussoftware.sms.model.entity.auth.PermissionEntity.user 
			// -> vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity (or SMSGroupEntity).
		if (entity.getGroup().getId() == null) {
			entity.setGroup(null);
		}
		else {
			// Check if this group exists
			if (!groupRepository.exists(permissionDto.getGroupId())) {
				throw new EntityNotFoundException(applicationContext.getMessage(GroupServiceImpl.class.getName() + MessagePropertiesConstant.KEY_ENTITY_NOT_FOUND, 
						new Object[] {permissionDto.getGroupId()}, null));
			}
		}
		
		
		if (entity.getUser().getId() == null) {
			entity.setUser(null);
		}
		else {
			// Check if this user exists
			if (!userRepository.exists(permissionDto.getUserId())) {
				throw new EntityNotFoundException(applicationContext.getMessage(UserServiceImpl.class.getName() + MessagePropertiesConstant.KEY_ENTITY_NOT_FOUND, 
						new Object[] {permissionDto.getGroupId()}, null));
			}
		}
		
		// Check if there's any same permission exists.
		List<PermissionEntity> ePermissions = permissionRepo.findByUserIdAndGroupIdAndTargetTypeAndTargetIdAndPermission(permissionDto.getUserId(), 
				permissionDto.getGroupId(), 
				permissionDto.getTargetType(), 
				permissionDto.getTargetId(),
				permissionDto.getPermission());
		if (ePermissions != null && !ePermissions.isEmpty()) {
			throw new EntityExistsException(
					applicationContext.getMessage(this.getClass().getName() + MessagePropertiesConstant.KEY_PERMISSION_ALREADY_EXISTS, // Get message from message/messages.properties
					new Object[] {ePermissions.get(0).getId()}, 
					applicationContext.getMessage(MessagePropertiesConstant.KEY_DEFAULT, null, null), // Get default message from message/messages.properties 
																									  // in case the required message would be not found.
					null));
		}
		
		
		
		permissionRepo.saveAndFlush(entity);
	}

	@Override
	public void delete(Integer permissionId) {
		permissionRepo.delete(permissionId);
	}

}
