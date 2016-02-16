package vn.com.splussoftware.sms.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.DataTemplateEntity;


public interface DataTemplateRepository extends JpaRepository<DataTemplateEntity, Long> {
}
