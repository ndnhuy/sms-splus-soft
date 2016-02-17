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
import vn.com.splussoftware.sms.model.entity.LocationEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.LocationRepository;
import vn.com.splussoftware.sms.utils.dto.LocationDto;
import vn.com.splussoftware.sms.utils.validator.LocationValidator;

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
					String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
					return errorMessage;
				}
				if (oldLocation != null && locationDto.getName() != null) {
					if (locationDto.getName().equalsIgnoreCase(oldLocation.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldLocation != null) {
					if (locationDto.getName().equalsIgnoreCase(oldLocation.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
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
			LocationValidator.checkLocationData(errorList, locationDto); // kietlt
																	// 9:44 
																	// AM
																	// 2016/2/16
																	// check
																	// data of
																	// Location
																	// input
			if (errorList.isEmpty()) { // kietlt 9:44  AM 2016/2/16 data of
										// Location
										// is not error
				LocationEntity LocationEntity = mapper.map(locationDto, LocationEntity.class);
				locationRepo.saveAndFlush(LocationEntity);
				return ServicesConstant.SUCCESS;
			} else { // kietlt 9:44  AM 2016/2/16 data of Location is error
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
				return ServicesConstant.SUCCESS;
			} else {
				return ServicesConstant.FAIL;
			}
		}
		return ServicesConstant.FAIL;
	}

}
