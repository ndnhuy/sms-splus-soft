package vn.com.splussoftware.sms.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.HistoryEntity;
import vn.com.splussoftware.sms.model.repository.HistoryRepository;

@Service
public class HistoryServiceImpl implements HistoryService {
	@Autowired
	private HistoryRepository entityHistoryRepository;

	@Override
	public HistoryEntity createHistory(HistoryEntity history) {
		return entityHistoryRepository.saveAndFlush(history);
	}
}
