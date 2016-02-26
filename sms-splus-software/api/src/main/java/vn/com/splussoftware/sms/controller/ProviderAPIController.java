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

import vn.com.splussoftware.sms.model.constant.ProviderConstant;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;
import vn.com.splussoftware.sms.model.entity.ProviderEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.ContactPointRepository;
import vn.com.splussoftware.sms.model.repository.ProviderRepository;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.ProviderDto;
import vn.com.splussoftware.sms.utils.validator.ProviderValidator;
import vn.com.splussoftware.sms.utils.validator.ServicesValidator;

/**
 * @author KietLT
 * 
 * 3:18 PM
 * 
 * 2/15/2016
 *
 */
@RestController
@RequestMapping("/sms/provider")
public class ProviderAPIController {
	@Autowired 
	private ProviderRepository providerRepo;
	@Autowired
	private ContactPointRepository contactPointRepo;
	@Autowired 
	private DozerBeanMapper mapper;
	
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public List<ProviderDto> listAllProvider (){
		List<ProviderDto> providerDtoList = new ArrayList<ProviderDto>();
		List<ProviderEntity> providerEntityList = providerRepo.findAll();
		for (ProviderEntity providerEntity: providerEntityList){
			providerDtoList.add(mapper.map(providerEntity, ProviderDto.class));
		}
		return providerDtoList;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProvider(@RequestBody(required = true) ProviderDto providerDto) {
		if (providerDto != null) {
			ProviderEntity oldProviderEntiy = providerRepo.findByName(providerDto.getName());
			ProviderDto oldProvider;
			if (oldProviderEntiy!=null){
				oldProvider = mapper.map(oldProviderEntiy, ProviderDto.class);
			}
			else {
				oldProvider =null;
			}
			/**
			 * kietlt 11:16 AM  AM 2016/2/16
			 * 
			 * check Location is exist
			 */
			if (providerDto.getId() != null) {
				if (providerRepo.exists(providerDto.getId())) {
					String errorMessage = ProviderConstant.FAIL + ProviderConstant.EXIST;
					return errorMessage;
				}
				if (oldProvider != null && providerDto.getName() != null) {
					if (providerDto.getName().equalsIgnoreCase(oldProvider.getName())) {
						String errorMessage = ProviderConstant.FAIL + ProviderConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldProvider != null) {
					if (providerDto.getName().equalsIgnoreCase(oldProvider.getName())) {
						String errorMessage = ProviderConstant.FAIL + ProviderConstant.EXIST;
						return errorMessage;
					}
				}
			}
			/**
			 * kietlt 2:15 AM 2016/2/16 Set default value for CreateDate,
			 * CreateBy, ModifyBy, ModifyDate
			 */
			providerDto.setCreateTime(Calendar.getInstance().getTime());
			providerDto.setModifyTime(Calendar.getInstance().getTime());
			providerDto.setCreateBy("Default User");
			providerDto.setModifyBy("Default User");

			List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
			if (contactPointRepo.exists(providerDto.getContactPointId())) { // check id of table reference is exist
				ProviderValidator.checkProviderData(errorList, providerDto);// kietlt 9:44 AM  check validate input of data
				
			}
			else{
				ValidatorErrorModelException validatorErrorModelException = new ValidatorErrorModelException();
				validatorErrorModelException.setErrorCode(UtilValidatorConstant.ERROR_CODE_NOT_FOUND);
				validatorErrorModelException.setErrorMessage(ProviderConstant.FAIL_NOT_FOUND_CONTACT_POINT_ID);
				errorList.add(validatorErrorModelException);
			}
			if (errorList.isEmpty()) { // kietlt 11:16 AM  AM 2016/2/16 data of
										// Location
										// is not error
				ProviderEntity ProviderEntity = mapper.map(providerDto, ProviderEntity.class);
				providerRepo.saveAndFlush(ProviderEntity);
				return ProviderConstant.SUCCESS_CREATE;
			} else { // kietlt 11:16 AM  AM 2016/2/16 data of Location is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = ProviderConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return ProviderConstant.FAIL;
	}
	
	/**
	 * delete id use to delete provider by id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteId(@PathVariable(value = "id") Integer id) {
		if (id != null) {
			if (providerRepo.exists(id)) { // kietlt 4:00 PM 2016/2/16 check id
											// is exist
				providerRepo.delete(id);
				return ProviderConstant.SUCCESS_DEL;
			} else {
				return ProviderConstant.FAIL;
			}
		}
		return ProviderConstant.FAIL;
	}
	/**
	 * show providerDto by providerDto id
	 * 
	 * @param id
	 *
	 * @return: providerDto
	 */
	@RequestMapping(value = "/listId/{id}", method = RequestMethod.GET)
	public ProviderDto listId(@PathVariable(value = "id") Integer id) {
		if (providerRepo.exists(id)) {
			ProviderEntity providerEntity = providerRepo.findOne(id);
			ProviderDto providerDto = mapper.map(providerEntity,ProviderDto.class);
			return providerDto;
		} else {
			return null;
		}
	}
	/**
	 * Disable provider by provider id
	 * 
	 * @param id
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/disableId/{id}", method = RequestMethod.GET)
	public String disableId(@PathVariable(value = "id") Integer id) {
		if (providerRepo.exists(id)) {
			ProviderEntity serviceData = new ProviderEntity();
			serviceData = providerRepo.findOne(id);
			serviceData.setIsActive(false);
			providerRepo.saveAndFlush(serviceData);
			return ProviderConstant.SUCCESS_DEL;
		}
		return ProviderConstant.FAIL;
	}
	/**
	 * Edit provider by provider id
	 * 
	 * @param providerEdited
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/updateId", method = RequestMethod.POST)
	public String updateId(@RequestBody ProviderDto providerEdited) {
		List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
		if (providerRepo.exists(providerEdited.getId())) {
			ProviderDto providerDto = new ProviderDto();
			ProviderEntity providerEntity  = providerRepo.findOne(providerEdited.getId());
			providerDto = mapper.map(providerEntity, ProviderDto.class);
			providerDto.setData(providerEdited);
			/**
			 * kietlt 10:08 AM 2016/2/17 set defaul user modifed and modified
			 * date
			 */
			providerDto.setModifyTime(Calendar.getInstance().getTime());
			providerDto.setModifyBy("Default User");

			ProviderValidator.checkProviderData(errorList, providerDto);
			if (errorList.isEmpty()) {
				providerEntity = mapper.map(providerDto, ProviderEntity.class);
				providerRepo.saveAndFlush(providerEntity);
				return ProviderConstant.SUCCESS_UPDATE;
			} else { //// kietlt 10:08 AM 2016/2/17 data of service is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = ProviderConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return ProviderConstant.FAIL;
	}

}
