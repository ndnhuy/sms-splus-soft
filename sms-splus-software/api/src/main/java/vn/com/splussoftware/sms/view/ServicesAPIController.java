/**
 * @Author: @author KietLT
 *
 * @Time: 6:32:11 PM
 */
package vn.com.splussoftware.sms.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.com.splussoftware.sms.model.constant.ServicesConstant;
import vn.com.splussoftware.sms.model.entity.ServicesEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.ServicesRepository;
import vn.com.splussoftware.sms.utils.service.AuthenticationService;
//
//import vn.com.splus_software.sms.constant.ServicesConstant;
//import vn.com.splus_software.sms.exception.ServicesException;
//import vn.com.splus_software.sms.model.ServicesModel;
//import vn.com.splus_software.sms.model.ValidatorErrorModel;
//import vn.com.splus_software.sms.repository.ServicesRepository;
//import vn.com.splus_software.sms.validator.ServicesValidator;
import vn.com.splussoftware.sms.utils.validator.ServicesValidator;

/**
 * @author KietLT
 *
 */
@RestController
@RequestMapping("/sms/services")
public class ServicesAPIController {
	@Autowired
	ServicesRepository repoServices;
	
	@Autowired
	private AuthenticationService authService;
	
	Logger logger = Logger.getLogger(ServicesAPIController.class);

	/**
	 * List all services
	 * 
	 * @para null
	 *
	 * @return: List<EServices>
	 */
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public List<ServicesEntity> listAllServices() {
		logger.info("list all services");
		return repoServices.findAll();
	}

	/**
	 * show service by service id
	 * 
	 * @param id
	 *
	 * @return: EServices
	 */
	@RequestMapping(value = "/listId/{id}", method = RequestMethod.GET)
	public ServicesEntity listId(@PathVariable(value = "id") Integer id) {
		if (repoServices.exists(id)) {
			ServicesEntity service = repoServices.findOne(id);
			return service;
		} else {
			return null;
		}
	}

	/**
	 * Add one services into database
	 * 
	 * @param service
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/addService", method = RequestMethod.POST)
	public String addService(@RequestBody(required = true) ServicesEntity service) {
		
//		if (authService.checkPermissionForCurrentUser("service", null, "update")) {//...}
//		else {
//			throw new AppException(HttpStatus.FORBIDDEN_403, "Cannot add.", null);
//		}

		ServicesEntity oldService = repoServices.findByTitle(service.getTitle());
		/**
		 * kietlt 3:12 PM 2016/1/27
		 * 
		 * check service is exist
		 */
		if (service.getId() != null) {
			if (repoServices.exists(service.getId())) {
				String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
				return errorMessage;
			}
			if (oldService != null && service.getTitle() != null) {
				if (service.getTitle().equalsIgnoreCase(oldService.getTitle())) {
					String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
					return errorMessage;
				}
			}
		} else {
			if (oldService != null) {
				if (service.getTitle().equalsIgnoreCase(oldService.getTitle())) {
					String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
					return errorMessage;
				}
			}
		}
		/**
		 * kietlt 2:15 PM 2016/1/27 Set default value for CreateDate, CreateBy,
		 * ModifyBy, ModifyDate
		 */
		service.setCreateDate(Calendar.getInstance().getTime());
		service.setModifyDate(Calendar.getInstance().getTime());
		service.setCreateBy("Default User");
		service.setModifyBy("Default User");

		List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
		ServicesValidator.checkServiceData(errorList, service); // kietlt 3:12
																// PM 2016/1/27
																// check data of
																// service input
		if (errorList.isEmpty()) { // kietlt 3:12 PM 2016/1/27 data of service
									// is not error
			repoServices.saveAndFlush(service);
			return ServicesConstant.SUCCESS;
		} else { // kietlt 3:12 PM 2016/1/27 data of service is error
			StringBuilder errorMessage = new StringBuilder();
			for (ValidatorErrorModelException error : errorList) {
				errorMessage.append(error.toString() + "\n");
			}
			String messageFail = ServicesConstant.FAIL + "\n" + errorMessage.toString();
			return messageFail;
		}
	
		
		
		

	}

	/**
	 * deleteMul use to delete more than one service, delete by service id
	 * 
	 * @param listId
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/deleteMul", method = RequestMethod.POST)
	public String deleteMul(@RequestBody List<Integer> listId) {
		Boolean flag = true; // kietlt 3:12 PM 2016/1/27 this variable use to
								// check delete all Service that have id in
								// listId success.
		for (int count = 0; count < listId.size(); count++) {
			if (repoServices.exists(listId.get(count))) {
				repoServices.delete(listId.get(count));
			}
		}
		for (int count = 0; count < listId.size(); count++) {
			if (repoServices.exists(listId.get(count))) {
				flag = false;
			}
		}
		if (flag) {
			return ServicesConstant.SUCCESS;
		}
		return ServicesConstant.FAIL;

	}

	/**
	 * Delete one service
	 * 
	 * @param id
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteId(@PathVariable(value = "id") Integer id) {
		if (id != null) {
			if (repoServices.exists(id)) { // kietlt 3:12 PM 2016/1/27 check id
											// is exist
				repoServices.delete(id);
				return ServicesConstant.SUCCESS;
			} else {
				return ServicesConstant.FAIL;
			}
		}
		return ServicesConstant.FAIL;
	}

	/**
	 * Edit service by service id
	 * 
	 * @param serviceEdited
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/updateId", method = RequestMethod.POST)
	public String updateId(@RequestBody ServicesEntity serviceEdited) {
		List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
		if (repoServices.exists(serviceEdited.getId())) {
			ServicesEntity serviceData = new ServicesEntity();
			serviceData = repoServices.findOne(serviceEdited.getId());
			serviceData.setData(serviceEdited);
			/**
			 * kietlt 2:20 PM 2016/1/27 set defaul user modifed and modified
			 * date
			 */
			serviceData.setModifyDate(Calendar.getInstance().getTime());
			serviceData.setModifyBy("Default User");

			ServicesValidator.checkServiceData(errorList, serviceData);
			if (errorList.isEmpty()) {
				repoServices.saveAndFlush(serviceData);
				logger.warn("Successfull");
				return ServicesConstant.SUCCESS;
			} else { //// kietlt 3:18 PM 2016/1/27 data of service is error
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

}
