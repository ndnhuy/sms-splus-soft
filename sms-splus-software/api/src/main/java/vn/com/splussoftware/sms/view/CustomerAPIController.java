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
import vn.com.splussoftware.sms.model.entity.CustomerEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.CustomerRepository;
import vn.com.splussoftware.sms.utils.dto.CustomerDto;
import vn.com.splussoftware.sms.utils.validator.CustomerValidator;

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
					String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
					return errorMessage;
				}
				if (oldCustomer != null && customerDto.getName() != null) {
					if (customerDto.getName().equalsIgnoreCase(oldCustomer.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldCustomer != null) {
					if (customerDto.getName().equalsIgnoreCase(oldCustomer.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
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
			CustomerValidator.checkCustomerData(errorList, customerDto); // kietlt
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
				CustomerEntity CustomerEntity = mapper.map(customerDto, CustomerEntity.class);
				customerRepo.saveAndFlush(CustomerEntity);
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
				return ServicesConstant.SUCCESS;
			} else {
				return ServicesConstant.FAIL;
			}
		}
		return ServicesConstant.FAIL;
	}
}
