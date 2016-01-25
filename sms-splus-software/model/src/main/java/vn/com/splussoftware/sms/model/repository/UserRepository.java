package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.WebUserEntity;

public interface UserRepository extends JpaRepository<WebUserEntity, Integer> {
	WebUserEntity findByUsername(String username);
}
