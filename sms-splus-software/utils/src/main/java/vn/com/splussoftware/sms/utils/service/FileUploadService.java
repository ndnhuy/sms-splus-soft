package vn.com.splussoftware.sms.utils.service;

import vn.com.splussoftware.sms.model.entity.FileUploadEntity;

public interface FileUploadService {
	FileUploadEntity createFile(FileUploadEntity file);

	FileUploadEntity getFileById(long id);

}
