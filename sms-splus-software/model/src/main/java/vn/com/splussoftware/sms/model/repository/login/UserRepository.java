package vn.com.splussoftware.sms.model.repository.login;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.login.SMSUserEntity;

public interface UserRepository extends JpaRepository<SMSUserEntity, Integer> {
	SMSUserEntity findByUserkey(String userkey);
}
