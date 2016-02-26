package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.utils.dto.group.GroupDto;

public interface GroupService {
	List<GroupDto> findAll();
	GroupDto findOne(Integer id);
	GroupDto save(GroupDto groupDto);
	void addUserToGroup(Integer userId, Integer groupId);
	void removeUserFromGroup(Integer userId, Integer groupId);
	void delete(Integer id);
}
