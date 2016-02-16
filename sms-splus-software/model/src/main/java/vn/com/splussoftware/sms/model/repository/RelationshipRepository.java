package vn.com.splussoftware.sms.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.RelationshipEntity;


public interface RelationshipRepository extends JpaRepository<RelationshipEntity, Long> {
}