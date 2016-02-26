package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.HistoryEntity;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

}
