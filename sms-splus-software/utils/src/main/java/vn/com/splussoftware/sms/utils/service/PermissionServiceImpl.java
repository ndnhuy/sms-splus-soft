package vn.com.splussoftware.sms.utils.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.auth.PermissionEntity;
import vn.com.splussoftware.sms.model.repository.auth.PermissionRepository;
import vn.com.splussoftware.sms.utils.dto.PermissionDto;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepo;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	public List<PermissionDto> findAll() {
		List<PermissionEntity> entities = permissionRepo.findAll();
		
		List<PermissionDto> dtos = new ArrayList<PermissionDto>();
		for (PermissionEntity e : entities) {
			dtos.add(mapper.map(e, PermissionDto.class));
		}
		
		return dtos;
	}

	@Override
	public void save(PermissionDto permissionDto) {
		PermissionEntity entity = mapper.map(permissionDto, PermissionEntity.class);
		if (entity.getGroup().getId() == null) {
			entity.setGroup(null);
		}
		permissionRepo.saveAndFlush(entity);
	}

}
