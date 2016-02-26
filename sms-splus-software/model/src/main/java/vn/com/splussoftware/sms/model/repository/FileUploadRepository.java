package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.FileUploadEntity;

public interface FileUploadRepository extends JpaRepository<FileUploadEntity, Long> {

}
