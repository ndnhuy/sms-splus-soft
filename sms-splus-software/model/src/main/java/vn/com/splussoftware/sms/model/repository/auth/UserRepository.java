package vn.com.splussoftware.sms.model.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity;

public interface UserRepository extends JpaRepository<SMSUserEntity, Integer> {
	SMSUserEntity findByUserkey(String userkey);
	
	@Query("SELECT u FROM SMSUserEntity u WHERE u.userkey = :userkey AND u.loginMethodEntity.url = :loginMethodUrl")
	SMSUserEntity findByUserkeyAndLoginMethodUrl(@Param("userkey") String userkey, 
												@Param("loginMethodUrl") String loginMethodUrl);
	
	@Query("SELECT u FROM SMSUserEntity u WHERE u.userkey = :userkey AND u.loginMethodEntity.id = :loginMethodId")
	SMSUserEntity findByUserkeyAndLoginMethodId(@Param("userkey") String userkey,
												@Param("loginMethodId") String loginMethodId);
	
	Integer deleteByUserkey(String userkey);
	
}
