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

import vn.com.splussoftware.sms.model.constant.CustomerConstant;
import vn.com.splussoftware.sms.model.constant.ProviderConstant;
import vn.com.splussoftware.sms.model.entity.CustomerEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.ContactPointRepository;
import vn.com.splussoftware.sms.model.repository.CustomerRepository;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.CustomerDto;
import vn.com.splussoftware.sms.utils.validator.CustomerValidator;
import vn.com.splussoftware.sms.utils.validator.ProviderValidator;

/**
 * @author KietLT
 *
 */
@RestController
@RequestMapping("/sms/user")
public class CustomerAPIController {
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private ContactPointRepository contactPointRepo;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@RequestMapping(value="/listAll", method = RequestMethod.GET)
	public List<CustomerDto> listAllCustomer (){
		List<CustomerEntity> customerEntityList = customerRepo.findAll();
		List<CustomerDto> customerDtoList = new ArrayList<CustomerDto>();
		for (CustomerEntity entityCustomer : customerEntityList){
			customerDtoList.add(mapper.map(entityCustomer, CustomerDto.class));
		}
		return customerDtoList;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addCustomer(@RequestBody(required = true) CustomerDto customerDto) {
		if (customerDto != null) {
			CustomerEntity oldCustomerEntiy = customerRepo.findByName(customerDto.getName());
			CustomerDto oldCustomer;
			if (oldCustomerEntiy!=null){
				oldCustomer = mapper.map(oldCustomerEntiy, CustomerDto.class);
			}
			else {
				oldCustomer =null;
			}
			/**
			 * kietlt 11:16 AM  AM 2016/2/16
			 * 
			 * check Location is exist
			 */
			if (customerDto.getId() != null) {
				if (customerRepo.exists(customerDto.getId())) {
					String errorMessage = CustomerConstant.FAIL + CustomerConstant.EXIST;
					return errorMessage;
				}
				if (oldCustomer != null && customerDto.getName() != null) {
					if (customerDto.getName().equalsIgnoreCase(oldCustomer.getName())) {
						String errorMessage = CustomerConstant.FAIL + CustomerConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldCustomer != null) {
					if (customerDto.getName().equalsIgnoreCase(oldCustomer.getName())) {
						String errorMessage = CustomerConstant.FAIL + CustomerConstant.EXIST;
						return errorMessage;
					}
				}
			}
			/**
			 * kietlt 2:15 AM 2016/2/16 Set default value for CreateDate,
			 * CreateBy, ModifyBy, ModifyDate
			 */
			customerDto.setCreateTime(Calendar.getInstance().getTime());
			customerDto.setModifyTime(Calendar.getInstance().getTime());
			customerDto.setCreateBy("Default User");
			customerDto.setModifyBy("Default User");

			List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
			if (contactPointRepo.exists(customerDto.getContactPointId())) { // check id of table reference is exist
				CustomerValidator.checkCustomerData(errorList, customerDto);// kietlt 9:44 AM  check validate input of data
				
			}
			else{
				ValidatorErrorModelException validatorErrorModelException = new ValidatorErrorModelException();
				validatorErrorModelException.setErrorCode(UtilValidatorConstant.ERROR_CODE_NOT_FOUND);
				validatorErrorModelException.setErrorMessage(CustomerConstant.FAIL_NOT_FOUND_CONTACT_POINT_ID);
				errorList.add(validatorErrorModelException);
			}
			if (errorList.isEmpty()) { // kietlt 11:16 AM  AM 2016/2/16 data of
										// Location
										// is not error
				CustomerEntity CustomerEntity = mapper.map(customerDto, CustomerEntity.class);
				customerRepo.saveAndFlush(CustomerEntity);
				return CustomerConstant.SUCCESS_CREATE;
			} else { // kietlt 11:16 AM  AM 2016/2/16 data of Location is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = CustomerConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return CustomerConstant.FAIL;
	}
	/**
	 * delete id use to delete customer by id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteId(@PathVariable(value = "id") Integer id) {
		if (id != null) {
			if (customerRepo.exists(id)) { // kietlt 3:56 PM 2016/2/16 check id
											// is exist
				customerRepo.delete(id);
				return CustomerConstant.SUCCESS_DEL;
			} else {
				return CustomerConstant.FAIL;
			}
		}
		return CustomerConstant.FAIL;
	}
	/**
	 * show customerDto by customerDto id
	 * 
	 * @param id
	 *
	 * @return: customerDto
	 */
	@RequestMapping(value = "/listId/{id}", method = RequestMethod.GET)
	public CustomerDto listId(@PathVariable(value = "id") Integer id) {
		if (customerRepo.exists(id)) {
			CustomerEntity customerEntity = customerRepo.findOne(id);
			CustomerDto customerDto = mapper.map(customerEntity, CustomerDto.class);
			return customerDto;
		} else {
			return null;
		}
	}
	/**
	 * Disable customer by customer id
	 * 
	 * @param id
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/disableId/{id}", method = RequestMethod.GET)
	public String disableId(@PathVariable(value = "id") Integer id) {
		if (customerRepo.exists(id)) {
			CustomerEntity serviceData = new CustomerEntity();
			serviceData = customerRepo.findOne(id);
			serviceData.setIsActive(false);
			customerRepo.saveAndFlush(serviceData);
			return CustomerConstant.SUCCESS_DEL;
		}
		return CustomerConstant.FAIL;
	}
	/**
	 * Edit customer by customer id
	 * 
	 * @param customerEdited
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/updateId", method = RequestMethod.POST)
	public String updateId(@RequestBody CustomerDto customerEdited) {
		List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
		if (customerRepo.exists(customerEdited.getId())) {
			CustomerDto customerDto = new CustomerDto();
			CustomerEntity customerEntity  = customerRepo.findOne(customerEdited.getId());
			customerDto = mapper.map(customerEntity, CustomerDto.class);
			customerDto.setData(customerEdited);
			/**
			 * kietlt 10:08 AM 2016/2/17 set defaul user modifed and modified
			 * date
			 */
			customerDto.setModifyTime(Calendar.getInstance().getTime());
			customerDto.setModifyBy("Default User");

			CustomerValidator.checkCustomerData(errorList, customerDto);
			if (errorList.isEmpty()) {
				customerEntity = mapper.map(customerDto, CustomerEntity.class);
				customerRepo.saveAndFlush(customerEntity);
				return CustomerConstant.SUCCESS_UPDATE;
			} else { //// kietlt 10:08 AM 2016/2/17 data of service is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = CustomerConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return CustomerConstant.FAIL;
	}

}
