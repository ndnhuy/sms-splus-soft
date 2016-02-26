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

import vn.com.splussoftware.sms.model.constant.ProcessesConstant;
import vn.com.splussoftware.sms.model.constant.ProviderConstant;
import vn.com.splussoftware.sms.model.entity.LocationEntity;
import vn.com.splussoftware.sms.model.entity.ProcessesEntity;
import vn.com.splussoftware.sms.model.entity.ProviderEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.LocationRepository;
import vn.com.splussoftware.sms.model.repository.ProcessesRepository;
import vn.com.splussoftware.sms.model.repository.ServicesRepository;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.LocationDto;
import vn.com.splussoftware.sms.utils.dto.ProcessesDto;
import vn.com.splussoftware.sms.utils.validator.LocationValidator;
import vn.com.splussoftware.sms.utils.validator.ProcessesValidator;
import vn.com.splussoftware.sms.utils.validator.ProviderValidator;

/**
 * @author KietLT
 *
 */
@RestController
@RequestMapping("/sms/processes")
public class ProcessesAPIController {
	@Autowired
	private ProcessesRepository processesRepo;
	@Autowired
	private ServicesRepository servicesRepo;
	@Autowired
	private LocationRepository locationRepo;
	@Autowired
	private DozerBeanMapper mapper;
	
	@RequestMapping(value="/listAll", method = RequestMethod.GET)
	public List<ProcessesDto> listAllProcesses (){
		List<ProcessesEntity>processesEntityList = processesRepo.findAll();
		List<ProcessesDto> customerDtoList = new ArrayList<ProcessesDto>();
		for (ProcessesEntity entityProcess : processesEntityList){
			customerDtoList.add(mapper.map(entityProcess, ProcessesDto.class));
		}
		return customerDtoList;
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProcesses(@RequestBody(required = true) ProcessesDto processesDto) {
		if (processesDto != null) {
			ProcessesEntity oldProcessesEntiy = processesRepo.findByName(processesDto.getName());
			ProcessesDto oldProcesses;
			if (oldProcessesEntiy!=null){
				oldProcesses = mapper.map(oldProcessesEntiy, ProcessesDto.class);
			}
			else {
				oldProcesses =null;
			}
			/**
			 * kietlt 9:44  AM 2016/2/16
			 * 
			 * check provider is exist
			 */
			if (processesDto.getId() != null) {
				if (processesRepo.exists(processesDto.getId())) {
					String errorMessage = ProcessesConstant.FAIL + ProcessesConstant.EXIST;
					return errorMessage;
				}
				if (oldProcesses != null && processesDto.getName() != null) {
					if (processesDto.getName().equalsIgnoreCase(oldProcesses.getName())) {
						String errorMessage = ProcessesConstant.FAIL + ProcessesConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldProcesses != null) {
					if (processesDto.getName().equalsIgnoreCase(oldProcesses.getName())) {
						String errorMessage = ProcessesConstant.FAIL + ProcessesConstant.EXIST;
						return errorMessage;
					}
				}
			}
			/**
			 * kietlt 2:15 AM 2016/2/16 Set default value for CreateDate,
			 * CreateBy, ModifyBy, ModifyDate
			 */
			processesDto.setCreateTime(Calendar.getInstance().getTime());
			processesDto.setModifyTime(Calendar.getInstance().getTime());
			processesDto.setCreateBy("Default User");
			processesDto.setModifyBy("Default User");

			List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
			if (locationRepo.exists(processesDto.getLocationId()) && servicesRepo.exists(processesDto.getServiceId())) { // check id of table reference is exist
				ProcessesValidator.checkProcessesData(errorList, processesDto);// kietlt 9:44 AM  check validate input of data
				
			}
			else{
				ValidatorErrorModelException validatorErrorModelException = new ValidatorErrorModelException();
				validatorErrorModelException.setErrorCode(UtilValidatorConstant.ERROR_CODE_NOT_FOUND);
				if (locationRepo.exists(processesDto.getLocationId())){
					validatorErrorModelException.setErrorMessage(ProcessesConstant.FAIL_NOT_FOUND_LOCATION_ID);
				} else {
					validatorErrorModelException.setErrorMessage(ProcessesConstant.FAIL_NOT_FOUND_SERVICES_ID);
				}
				errorList.add(validatorErrorModelException);
			}
			if (errorList.isEmpty()) { // kietlt 9:44  AM 2016/2/16 data of
										// processes
										// is not error
				ProcessesEntity ProcessesEntity = mapper.map(processesDto, ProcessesEntity.class);
				processesRepo.saveAndFlush(ProcessesEntity);
				return ProcessesConstant.SUCCESS_CREATE;
			} else { // kietlt 9:44  AM 2016/2/16 data of processes is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = ProcessesConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return ProcessesConstant.FAIL;
	}
	/**
	 * delete id use to delete processes by id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteId(@PathVariable(value = "id") Integer id) {
		if (id != null) {
			if (processesRepo.exists(id)) { // kietlt 3:56 PM 2016/2/16 check id
											// is exist
				processesRepo.delete(id);
				return ProcessesConstant.SUCCESS_DEL;
			} else {
				return ProcessesConstant.FAIL;
			}
		}
		return ProcessesConstant.FAIL;
	}
	/**
	 * show processesDto by processesDto id
	 * 
	 * @param id
	 *
	 * @return: processesDto
	 */
	@RequestMapping(value = "/listId/{id}", method = RequestMethod.GET)
	public ProcessesDto listId(@PathVariable(value = "id") Integer id) {
		if (processesRepo.exists(id)) {
			ProcessesEntity processesEntity = processesRepo.findOne(id);
			ProcessesDto processesDto = mapper.map(processesEntity,ProcessesDto.class);
			return processesDto;
		} else {
			return null;
		}
	}
	/**
	 * Disable processes by processes id
	 * 
	 * @param id
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/disableId/{id}", method = RequestMethod.GET)
	public String disableId(@PathVariable(value = "id") Integer id) {
		if (processesRepo.exists(id)) {
			ProcessesEntity serviceData = new ProcessesEntity();
			serviceData = processesRepo.findOne(id);
			serviceData.setIsActive(false);
			processesRepo.saveAndFlush(serviceData);
			return ProcessesConstant.SUCCESS_DEL;
		}
		return ProcessesConstant.FAIL;
	}
	/**
	 * Edit processes by processes id
	 * 
	 * @param processesEdited
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/updateId", method = RequestMethod.POST)
	public String updateId(@RequestBody ProcessesDto processesEdited) {
		List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
		if (processesRepo.exists(processesEdited.getId())) {
			ProcessesDto processesDto = new ProcessesDto();
			ProcessesEntity processesEntity  = processesRepo.findOne(processesEdited.getId());
			processesDto = mapper.map(processesEntity, ProcessesDto.class);
			processesDto.setData(processesEdited);
			/**
			 * kietlt 10:08 AM 2016/2/17 set defaul user modifed and modified
			 * date
			 */
			processesDto.setModifyTime(Calendar.getInstance().getTime());
			processesDto.setModifyBy("Default User");

			ProcessesValidator.checkProcessesData(errorList, processesDto);
			if (errorList.isEmpty()) {
				processesEntity = mapper.map(processesDto, ProcessesEntity.class);
				processesRepo.saveAndFlush(processesEntity);
				return ProcessesConstant.SUCCESS_UPDATE;
			} else { //// kietlt 10:08 AM 2016/2/17 data of service is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = ProcessesConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return ProcessesConstant.FAIL;
	}

}
