package vn.com.splussoftware.sms.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.FileUploadEntity;
import vn.com.splussoftware.sms.model.repository.FileUploadRepository;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	@Autowired
	private FileUploadRepository entityFileUploadRepository;

	@Override
	public FileUploadEntity createFile(FileUploadEntity file) {
		return entityFileUploadRepository.saveAndFlush(file);
	}

	@Override
	public FileUploadEntity getFileById(long id) {
		return entityFileUploadRepository.getOne(id);
	}
}
