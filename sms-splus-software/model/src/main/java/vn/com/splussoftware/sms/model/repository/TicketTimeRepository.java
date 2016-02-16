package vn.com.splussoftware.sms.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.splussoftware.sms.model.entity.TicketTimeEntity;


public interface TicketTimeRepository extends JpaRepository<TicketTimeEntity, Long> {
}
