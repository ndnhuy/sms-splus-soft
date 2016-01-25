package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.LoginMethodEntity;

public interface LoginMethodRepository extends JpaRepository<LoginMethodEntity, Integer> {
	LoginMethodEntity findByPriority(Integer priority);
	LoginMethodEntity findByUrl(String url);
}
