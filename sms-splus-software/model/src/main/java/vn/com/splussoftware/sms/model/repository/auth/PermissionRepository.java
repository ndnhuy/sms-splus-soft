package vn.com.splussoftware.sms.model.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.auth.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {
	PermissionEntity findByUserIdAndTargetTypeAndTargetIdAndPermission(Integer userId, String targetType, Integer targetId, String permission);
	PermissionEntity findByGroupIdAndTargetTypeAndTargetIdAndPermission(Integer groupId, String targetType, Integer targetId, String permission);
}
