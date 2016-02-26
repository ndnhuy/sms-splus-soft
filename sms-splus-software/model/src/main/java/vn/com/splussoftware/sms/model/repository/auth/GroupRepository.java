package vn.com.splussoftware.sms.model.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.auth.SMSGroupEntity;

public interface GroupRepository extends JpaRepository<SMSGroupEntity, Integer> {
	SMSGroupEntity findByGroupName(String groupName);
}
