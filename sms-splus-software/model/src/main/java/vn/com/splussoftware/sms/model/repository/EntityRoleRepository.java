package vn.com.splussoftware.sms.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.EntitiesRoleEntity;


public interface EntityRoleRepository extends JpaRepository<EntitiesRoleEntity, Long> {
}
