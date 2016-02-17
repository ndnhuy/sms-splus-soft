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
import vn.com.splussoftware.sms.model.entity.ProviderEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.ProviderRepository;
import vn.com.splussoftware.sms.utils.dto.ProviderDto;
import vn.com.splussoftware.sms.utils.validator.ProviderValidator;

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
					String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
					return errorMessage;
				}
				if (oldProvider != null && providerDto.getName() != null) {
					if (providerDto.getName().equalsIgnoreCase(oldProvider.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldProvider != null) {
					if (providerDto.getName().equalsIgnoreCase(oldProvider.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
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
			ProviderValidator.checkProviderData(errorList, providerDto); // kietlt
																	// 11:16 AM 
																	// AM
																	// 2016/2/16
																	// check
																	// data of
																	// Location
																	// input
			if (errorList.isEmpty()) { // kietlt 11:16 AM  AM 2016/2/16 data of
										// Location
										// is not error
				ProviderEntity ProviderEntity = mapper.map(providerDto, ProviderEntity.class);
				providerRepo.saveAndFlush(ProviderEntity);
				return ServicesConstant.SUCCESS;
			} else { // kietlt 11:16 AM  AM 2016/2/16 data of Location is error
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
				return ServicesConstant.SUCCESS;
			} else {
				return ServicesConstant.FAIL;
			}
		}
		return ServicesConstant.FAIL;
	}

}
