package vn.com.splussoftware.sms.model.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.com.splussoftware.sms.model.entity.auth.GlobalPermissionEntity;

public interface GlobalPermissionRepository extends JpaRepository<GlobalPermissionEntity, Integer> {

	@Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM GlobalPermissionEntity g "
			+ "WHERE g.userId = :userId AND LOWER(g.permission) = LOWER(:permission)")
	boolean existsByUserIdAndPermission(@Param("userId") Integer userId, 
										@Param("permission") String permission);
	
	Integer deleteByUserId(Integer userId);
}
