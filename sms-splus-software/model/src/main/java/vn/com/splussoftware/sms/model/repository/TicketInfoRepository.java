package vn.com.splussoftware.sms.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.TicketInfoEntity;


public interface TicketInfoRepository extends JpaRepository<TicketInfoEntity, Long> {
}
