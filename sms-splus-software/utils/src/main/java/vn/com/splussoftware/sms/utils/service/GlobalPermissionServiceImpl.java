package vn.com.splussoftware.sms.utils.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.auth.GlobalPermissionEntity;
import vn.com.splussoftware.sms.model.repository.auth.GlobalPermissionRepository;
import vn.com.splussoftware.sms.model.repository.auth.UserRepository;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionDto;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionUIDto;

@Service
public class GlobalPermissionServiceImpl implements GlobalPermissionService {

	@Autowired
	private GlobalPermissionRepository globalPermissionRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	public List<GlobalPermissionDto> findAll() {
		List<GlobalPermissionEntity> entities = globalPermissionRepo.findAll();
		
		List<GlobalPermissionDto> globalPermissionDtos = new ArrayList<GlobalPermissionDto>();
		for (GlobalPermissionEntity e : entities) {
			globalPermissionDtos.add(mapper.map(e, GlobalPermissionDto.class));
		}
		
		return globalPermissionDtos;
	}

	@Override
	public List<GlobalPermissionUIDto> getListOfUsersWithGlobalPermission() {
		List<GlobalPermissionEntity> entities = globalPermissionRepo.findAll(new Sort(Sort.Direction.ASC, "id"));
		
		List<GlobalPermissionUIDto> globalPermissionUIDtoList = new ArrayList<GlobalPermissionUIDto>();
		for (GlobalPermissionEntity e : entities) {
			GlobalPermissionUIDto dto = new GlobalPermissionUIDto();
			dto.setId(e.getId());
			dto.setPermission(e.getPermission());
			dto.setUserkey(userRepository
							.findOne(e.getUserId())
							.getUserkey());
			dto.setUserId(e.getUserId());
			
			globalPermissionUIDtoList.add(dto);
		}
		
		return globalPermissionUIDtoList;
	}

	@Override
	public void save(GlobalPermissionDto globalPermissionDto) {
		GlobalPermissionEntity entity = mapper.map(globalPermissionDto, GlobalPermissionEntity.class);
		globalPermissionRepo.saveAndFlush(entity);
	}

	@Override
	public void deleteAll() {
		globalPermissionRepo.deleteAll();
	}

	@Override
	public Integer deleteByUserId(Integer userId) {
		return globalPermissionRepo.deleteByUserId(userId);
	}

	@Override
	public void delete(Integer id) {
		globalPermissionRepo.delete(id);
	}
	
}
