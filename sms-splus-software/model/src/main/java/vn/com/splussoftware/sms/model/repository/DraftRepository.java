package vn.com.splussoftware.sms.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.DraftEntity;

public interface DraftRepository extends JpaRepository<DraftEntity, Integer> {
}
