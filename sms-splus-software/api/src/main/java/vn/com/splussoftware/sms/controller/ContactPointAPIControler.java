/**
 * 
 */
package vn.com.splussoftware.sms.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.com.splussoftware.sms.model.constant.ContactPointConstant;
import vn.com.splussoftware.sms.model.entity.ContactPointEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.ContactPointRepository;
import vn.com.splussoftware.sms.utils.dto.ContactPointDto;
import vn.com.splussoftware.sms.utils.validator.ContactPointValidator;


/**
 * @author KietLT
 * 
 * 4:00 PM 
 * 
 * 2/15/2016
 *
 */
@RestController
@RequestMapping("/sms/contactPoint")
public class ContactPointAPIControler {
	@Autowired
	private ContactPointRepository contactPointRepo;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@RequestMapping(value = "/listAll", method=RequestMethod.GET)
	public List<ContactPointDto> listAllContactPoint (){
		List<ContactPointDto> contactPointDtoList = new ArrayList<ContactPointDto>();
		List<ContactPointEntity> contactPointEntityList = contactPointRepo.findAll();
		for(ContactPointEntity contactPointEntity : contactPointEntityList){
			contactPointDtoList.add(mapper.map(contactPointEntity, ContactPointDto.class));
		}
		return contactPointDtoList;
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContactPoint(@RequestBody(required = true) ContactPointDto contactPointDto) {
		if (contactPointDto != null) {
			ContactPointEntity oldContactPointEntiy = contactPointRepo.findByName(contactPointDto.getName());
			ContactPointDto oldProcesses;
			if (oldContactPointEntiy!=null){
				oldProcesses = mapper.map(oldContactPointEntiy, ContactPointDto.class);
			}
			else {
				oldProcesses =null;
			}
			/**
			 * kietlt 9:44  AM 2016/2/16
			 * 
			 * check processes is exist
			 */
			if (contactPointDto.getId() != null) {
				if (contactPointRepo.exists(contactPointDto.getId())) {
					String errorMessage = ContactPointConstant.FAIL + ContactPointConstant.EXIST;
					return errorMessage;
				}
				if (oldProcesses != null && contactPointDto.getName() != null) {
					if (contactPointDto.getName().equalsIgnoreCase(oldProcesses.getName())) {
						String errorMessage = ContactPointConstant.FAIL + ContactPointConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldProcesses != null) {
					if (contactPointDto.getName().equalsIgnoreCase(oldProcesses.getName())) {
						String errorMessage = ContactPointConstant.FAIL + ContactPointConstant.EXIST;
						return errorMessage;
					}
				}
			}
			/**
			 * kietlt 2:15 AM 2016/2/16 Set default value for CreateDate,
			 * CreateBy, ModifyBy, ModifyDate
			 */
			contactPointDto.setCreateTime(Calendar.getInstance().getTime());
			contactPointDto.setModifyTime(Calendar.getInstance().getTime());
			contactPointDto.setCreateBy("Default User");
			contactPointDto.setModifyBy("Default User");

			List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
			ContactPointValidator.checkContactPointData(errorList, contactPointDto); // kietlt
																	// 9:44 
																	// AM
																	// 2016/2/16
																	// check
																	// data of
																	// processes
																	// input
			if (errorList.isEmpty()) { // kietlt 9:44  AM 2016/2/16 data of
										// processes
										// is not error
				ContactPointEntity ContactPointEntity = mapper.map(contactPointDto, ContactPointEntity.class);
				contactPointRepo.saveAndFlush(ContactPointEntity);
				return ContactPointConstant.SUCCESS_CREATE;
			} else { // kietlt 9:44  AM 2016/2/16 data of processes is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = ContactPointConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return ContactPointConstant.FAIL;
	}
	
	/**
	 * delete id use to delete contact point by id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteId(@PathVariable(value = "id") Integer id) {
		if (id != null) {
			if (contactPointRepo.exists(id)) { // kietlt 3:56 PM 2016/2/16 check id
											// is exist
				contactPointRepo.delete(id);
				return ContactPointConstant.SUCCESS_DEL;
			} else {
				return ContactPointConstant.FAIL;
			}
		}
		return ContactPointConstant.FAIL;
	}
	/**
	 * show contactPoint by contactPoint id
	 * 
	 * @param id
	 *
	 * @return: contactPoint
	 */
	@RequestMapping(value = "/listId/{id}", method = RequestMethod.GET)
	public ContactPointDto listId(@PathVariable(value = "id") Integer id) {
		if (contactPointRepo.exists(id)) {
			ContactPointEntity contactPointEntiy = contactPointRepo.findOne(id);
			ContactPointDto contactPointDto = mapper.map(contactPointEntiy, ContactPointDto.class);
			return contactPointDto;
		} else {
			return null;
		}
	}
	/**
	 * Disable contact point by contact point id
	 * 
	 * @param id
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/disableId/{id}", method = RequestMethod.GET)
	public String disableId(@PathVariable(value = "id") Integer id) {
		if (contactPointRepo.exists(id)) {
			ContactPointEntity serviceData = new ContactPointEntity();
			serviceData = contactPointRepo.findOne(id);
			serviceData.setIsActive(false);
			contactPointRepo.saveAndFlush(serviceData);
			return ContactPointConstant.SUCCESS_DEL;
		}
		return ContactPointConstant.FAIL;
	}
	/**
	 * Edit contact point by contact point id
	 * 
	 * @param contactPointEdited
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/updateId", method = RequestMethod.POST)
	public String updateId(@RequestBody ContactPointDto contactPointEdited) {
		List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
		if (contactPointRepo.exists(contactPointEdited.getId())) {
			ContactPointDto contactPointDto = new ContactPointDto();
			ContactPointEntity contactPointEntity  = contactPointRepo.findOne(contactPointEdited.getId());
			contactPointDto = mapper.map(contactPointEntity, ContactPointDto.class);
			contactPointDto.setData(contactPointEdited);
			/**
			 * kietlt 10:08 AM 2016/2/17 set defaul user modifed and modified
			 * date
			 */
			contactPointDto.setModifyTime(Calendar.getInstance().getTime());
			contactPointDto.setModifyBy("Default User");

			ContactPointValidator.checkContactPointData(errorList, contactPointDto);
			if (errorList.isEmpty()) {
				contactPointEntity = mapper.map(contactPointDto, ContactPointEntity.class);
				contactPointRepo.saveAndFlush(contactPointEntity);
				return ContactPointConstant.SUCCESS_UPDATE;
			} else { //// kietlt 10:08 AM 2016/2/17 data of service is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = ContactPointConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return ContactPointConstant.FAIL;
	}
}
