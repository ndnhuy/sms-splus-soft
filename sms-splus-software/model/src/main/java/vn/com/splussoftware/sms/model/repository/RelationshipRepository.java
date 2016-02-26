package vn.com.splussoftware.sms.model.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.com.splussoftware.sms.model.entity.RelationshipEntity;


public interface RelationshipRepository extends JpaRepository<RelationshipEntity, Long> {
	@Query("SELECT u FROM RelationshipEntity u WHERE u.FromPhaseID = :id")
	List<RelationshipEntity> findByFromPhaseId(@Param("id") long fromId);
	
	@Query("SELECT u FROM RelationshipEntity u WHERE u.ToPhaseID = :id")
	List<RelationshipEntity> findByToPhaseId(@Param("id") long toId);
}
