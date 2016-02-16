package vn.com.splussoftware.sms.utils.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.DraftEntity;
import vn.com.splussoftware.sms.model.repository.DraftRepository;

@Service
public class DraftingServiceUtils {
	@Autowired
	DraftRepository repository ;
	public int saveJsonDB(int id, String value){
		DraftEntity draft = new DraftEntity();
		draft.setDraftcontent(value);
		draft.setTime(new Date().getTime());
		if (id != 0) draft.setDraftid(id);
		System.out.println(draft.getDraftcontent());
		draft = repository.saveAndFlush(draft);
		return draft.getDraftid();
	}
	public void remove(int id){
		DraftEntity draft = new DraftEntity();
		draft.setDraftid(id);
		repository.delete(draft);
		return;
	}
	
}
