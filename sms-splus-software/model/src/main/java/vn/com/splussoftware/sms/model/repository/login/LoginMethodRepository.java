package vn.com.splussoftware.sms.model.repository.login;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.login.LoginMethodEntity;

public interface LoginMethodRepository extends JpaRepository<LoginMethodEntity, Integer> {
	List<LoginMethodEntity> findAllByOrderByPriorityDesc();
	LoginMethodEntity findByPriority(Integer priority);
	LoginMethodEntity findByUrl(String url);
}
