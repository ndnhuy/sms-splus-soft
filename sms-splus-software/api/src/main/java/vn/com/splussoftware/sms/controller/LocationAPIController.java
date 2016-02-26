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

import vn.com.splussoftware.sms.model.constant.LocationConstant;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;
import vn.com.splussoftware.sms.model.entity.CustomerEntity;
import vn.com.splussoftware.sms.model.entity.LocationEntity;
import vn.com.splussoftware.sms.model.entity.ProcessesEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.LocationRepository;
import vn.com.splussoftware.sms.model.repository.ProviderRepository;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.CustomerDto;
import vn.com.splussoftware.sms.utils.dto.LocationDto;
import vn.com.splussoftware.sms.utils.validator.CustomerValidator;
import vn.com.splussoftware.sms.utils.validator.LocationValidator;
import vn.com.splussoftware.sms.utils.validator.ServicesValidator;

/**
 * @author KietLT
 * 
 * 3:00 PM 
 * 
 * 2/15/2016
 *
 */
@RestController
@RequestMapping("/sms/location")
public class LocationAPIController {
	@Autowired
	private LocationRepository locationRepo;
	@Autowired
	private ProviderRepository providerRepo;
	@Autowired
	private DozerBeanMapper mapper;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listAll", method=RequestMethod.GET)
	public List<LocationDto> listAllLocation (){
		List<LocationDto> locationDtoList = new ArrayList<LocationDto>();
		List<LocationEntity> locationEntityList = locationRepo.findAll();
		for(LocationEntity locationEntity : locationEntityList){
			locationDtoList.add(mapper.map(locationEntity, LocationDto.class));
		}
		return locationDtoList;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addLocation(@RequestBody(required = true) LocationDto locationDto) {
		if (locationDto != null) {
			LocationEntity oldLocationEntiy = locationRepo.findByName(locationDto.getName());
			LocationDto oldLocation;
			if (oldLocationEntiy!=null){
				oldLocation = mapper.map(oldLocationEntiy, LocationDto.class);
			}
			else {
				oldLocation =null;
			}
			/**
			 * kietlt 9:44  AM 2016/2/16
			 * 
			 * check Location is exist
			 */
			if (locationDto.getId() != null) {
				if (locationRepo.exists(locationDto.getId())) {
					String errorMessage = LocationConstant.FAIL + LocationConstant.EXIST;
					return errorMessage;
				}
				if (oldLocation != null && locationDto.getName() != null) {
					if (locationDto.getName().equalsIgnoreCase(oldLocation.getName())) {
						String errorMessage = LocationConstant.FAIL + LocationConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldLocation != null) {
					if (locationDto.getName().equalsIgnoreCase(oldLocation.getName())) {
						String errorMessage = LocationConstant.FAIL + LocationConstant.EXIST;
						return errorMessage;
					}
				}
			}
			/**
			 * kietlt 2:15 AM 2016/2/16 Set default value for CreateDate,
			 * CreateBy, ModifyBy, ModifyDate
			 */
			locationDto.setCreateTime(Calendar.getInstance().getTime());
			locationDto.setModifyTime(Calendar.getInstance().getTime());
			locationDto.setCreateBy("Default User");
			locationDto.setModifyBy("Default User");

			List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
			if (providerRepo.exists(locationDto.getProviderId())) { // check id of table reference is exist
				LocationValidator.checkLocationData(errorList, locationDto);// kietlt 9:44 AM  check validate input of data
				
			}
			else{
				ValidatorErrorModelException validatorErrorModelException = new ValidatorErrorModelException();
				validatorErrorModelException.setErrorCode(UtilValidatorConstant.ERROR_CODE_NOT_FOUND);
				validatorErrorModelException.setErrorMessage(LocationConstant.FAIL_NOT_FOUND_PROVIDER);
				errorList.add(validatorErrorModelException);
			}
			if (errorList.isEmpty()) { // kietlt 9:44  AM 2016/2/16 data of
										// Location
										// is not error
				LocationEntity LocationEntity = mapper.map(locationDto, LocationEntity.class);
				locationRepo.saveAndFlush(LocationEntity);
				return LocationConstant.SUCCESS_CREATE;
			} else { // kietlt 9:44  AM 2016/2/16 data of Location is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = LocationConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return LocationConstant.FAIL;
	}
	/**
	 * delete id use to delete location by id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteId(@PathVariable(value = "id") Integer id) {
		if (id != null) {
			if (locationRepo.exists(id)) { // kietlt 3:56 PM 2016/2/16 check id
											// is exist
				locationRepo.delete(id);
				return LocationConstant.SUCCESS_DEL;
			} else {
				return LocationConstant.FAIL;
			}
		}
		return LocationConstant.FAIL;
	}
	/**
	 * show locationDto by locationDto id
	 * 
	 * @param id
	 *
	 * @return: locationDto
	 */
	@RequestMapping(value = "/listId/{id}", method = RequestMethod.GET)
	public LocationDto listId(@PathVariable(value = "id") Integer id) {
		if (locationRepo.exists(id)) {
			LocationEntity locationEntity = locationRepo.findOne(id);
			LocationDto locationDto = mapper.map(locationEntity,LocationDto.class);
			return locationDto;
		} else {
			return null;
		}
	}
	/**
	 * Disable location by location id
	 * 
	 * @param id
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/disableId/{id}", method = RequestMethod.GET)
	public String disableId(@PathVariable(value = "id") Integer id) {
		if (locationRepo.exists(id)) {
			LocationEntity serviceData = new LocationEntity();
			serviceData = locationRepo.findOne(id);
			serviceData.setIsActive(false);
			locationRepo.saveAndFlush(serviceData);
			return LocationConstant.SUCCESS_DEL;
		}
		return LocationConstant.FAIL;
	}
	/**
	 * Edit location by location id
	 * 
	 * @param locationEdited
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/updateId", method = RequestMethod.POST)
	public String updateId(@RequestBody LocationDto locationEdited) {
		List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
		if (locationRepo.exists(locationEdited.getId())) {
			LocationDto locationDto = new LocationDto();
			LocationEntity locationEntity  = locationRepo.findOne(locationEdited.getId());
			locationDto = mapper.map(locationEntity, LocationDto.class);
			locationDto.setData(locationEdited);
			/**
			 * kietlt 10:08 AM 2016/2/17 set defaul user modifed and modified
			 * date
			 */
			locationDto.setModifyTime(Calendar.getInstance().getTime());
			locationDto.setModifyBy("Default User");

			LocationValidator.checkLocationData(errorList, locationDto);
			if (errorList.isEmpty()) {
				locationEntity = mapper.map(locationDto, LocationEntity.class);
				locationRepo.saveAndFlush(locationEntity);
				return LocationConstant.SUCCESS_UPDATE;
			} else { //// kietlt 10:08 AM 2016/2/17 data of service is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = LocationConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return LocationConstant.FAIL;
	}
}
