/**
 * 
 */
package vn.com.splussoftware.sms.view;

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

import vn.com.splussoftware.sms.model.constant.ServicesConstant;
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
					String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
					return errorMessage;
				}
				if (oldProcesses != null && contactPointDto.getName() != null) {
					if (contactPointDto.getName().equalsIgnoreCase(oldProcesses.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldProcesses != null) {
					if (contactPointDto.getName().equalsIgnoreCase(oldProcesses.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
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
				return ServicesConstant.SUCCESS;
			} else { // kietlt 9:44  AM 2016/2/16 data of processes is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = ServicesConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return ServicesConstant.FAIL;
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
				return ServicesConstant.SUCCESS;
			} else {
				return ServicesConstant.FAIL;
			}
		}
		return ServicesConstant.FAIL;
	}
}
