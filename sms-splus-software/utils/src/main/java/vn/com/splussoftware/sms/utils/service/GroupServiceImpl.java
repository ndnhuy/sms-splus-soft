package vn.com.splussoftware.sms.utils.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.auth.SMSGroupEntity;
import vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity;
import vn.com.splussoftware.sms.model.exception.UserAlreadyInGroupException;
import vn.com.splussoftware.sms.model.repository.auth.GroupRepository;
import vn.com.splussoftware.sms.model.repository.auth.UserRepository;
import vn.com.splussoftware.sms.utils.dto.group.GroupDto;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	public List<GroupDto> findAll() {
		
		List<GroupDto> dtoGroups = new ArrayList<GroupDto>();
		for (SMSGroupEntity e : groupRepository.findAll()) {
			dtoGroups.add(mapper.map(e, GroupDto.class));
		}
		
		return dtoGroups;
	}

	@Override
	public GroupDto save(GroupDto groupDto) {
		
		if (groupRepository.findByGroupName(groupDto.getGroupName()) != null) {
			throw new EntityExistsException("This group's name existed");
		}
		
		SMSGroupEntity e = groupRepository.save(mapper.map(groupDto, SMSGroupEntity.class));
		
		return mapper.map(e, GroupDto.class);
	}

	@Override
	public GroupDto findOne(Integer id) {
		SMSGroupEntity e = groupRepository.findOne(id);
		if (e == null) {
			throw new EntityNotFoundException("Group not found with id " + id);
		}
		
		return mapper.map(e, GroupDto.class);
	}

	@Override
	public void addUserToGroup(Integer userId, Integer groupId) {
		SMSGroupEntity eGroup = groupRepository.findOne(groupId);
		if (eGroup == null) {
			throw new EntityNotFoundException("Group not found with id " + groupId); 
		}
		
		SMSUserEntity eUser = userRepository.findOne(userId);
		if (eUser == null) {
			throw new EntityNotFoundException("User not found with id " + userId);
		}
		
		if (eGroup.getUsers().contains(eUser)) {
			throw new UserAlreadyInGroupException(eUser.getUserkey(), eGroup.getGroupName());
		}
		
		eGroup.getUsers().add(eUser);
		eUser.getGroups().add(eGroup);
		
		groupRepository.save(eGroup);
	}

	@Override
	public void removeUserFromGroup(Integer userId, Integer groupId) {
		SMSGroupEntity eGroup = groupRepository.findOne(groupId);
		if (eGroup == null) {
			throw new EntityNotFoundException("Group not found with id " + groupId); 
		}
		
		SMSUserEntity eUser = userRepository.findOne(userId);
		if (eUser == null) {
			throw new EntityNotFoundException("User not found with id " + userId);
		}
		
		eGroup.getUsers().remove(eUser);
		eUser.getGroups().remove(eGroup);
		
		groupRepository.save(eGroup);
	}

	@Override
	public void delete(Integer id) {
		groupRepository.delete(id);
	}



}
