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
import vn.com.splussoftware.sms.model.entity.ProcessesEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.ProcessesRepository;
import vn.com.splussoftware.sms.utils.dto.ProcessesDto;
import vn.com.splussoftware.sms.utils.validator.ProcessesValidator;

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
					String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
					return errorMessage;
				}
				if (oldProcesses != null && processesDto.getName() != null) {
					if (processesDto.getName().equalsIgnoreCase(oldProcesses.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldProcesses != null) {
					if (processesDto.getName().equalsIgnoreCase(oldProcesses.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
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
			ProcessesValidator.checkProcessesData(errorList, processesDto); // kietlt
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
				ProcessesEntity ProcessesEntity = mapper.map(processesDto, ProcessesEntity.class);
				processesRepo.saveAndFlush(ProcessesEntity);
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
				return ServicesConstant.SUCCESS;
			} else {
				return ServicesConstant.FAIL;
			}
		}
		return ServicesConstant.FAIL;
	}
}
