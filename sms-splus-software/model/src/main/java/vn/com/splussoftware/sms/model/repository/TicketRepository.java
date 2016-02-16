package vn.com.splussoftware.sms.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.TicketEntity;


public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}
