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

import ch.qos.logback.classic.pattern.Util;
import vn.com.splussoftware.sms.model.constant.CategoryConstant;
import vn.com.splussoftware.sms.model.entity.CategoryEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.CategoryRepository;
import vn.com.splussoftware.sms.model.repository.CustomerRepository;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.CategoryDto;
import vn.com.splussoftware.sms.utils.validator.CategoryValidator;

/**
 * @author KietLT
 * 
 *         2:46 AM
 * 
 *         2/15/2016
 */
@RestController
@RequestMapping("/sms/category")
public class CategoryAPIController {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private DozerBeanMapper mapper;

	/**
	 * @author KietLT
	 * 
	 *         8:52 AM
	 * 
	 *         listAllCatelog method use to list all category
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public List<CategoryDto> listAllCategory() {
		List<CategoryDto> categoryDtoList = new ArrayList<CategoryDto>();
		List<CategoryEntity> categoryEntityList = categoryRepo.findAll();
		for (CategoryEntity categoryEntity : categoryEntityList) {
			categoryDtoList.add(mapper.map(categoryEntity, CategoryDto.class));
		}
		return categoryDtoList;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addCategory(@RequestBody(required = true) CategoryDto categoryDto) {
		if (categoryDto != null) {
			CategoryEntity oldCategoryEntiy = categoryRepo.findByName(categoryDto.getName());
			CategoryDto oldCategory;
			if (oldCategoryEntiy != null) {
				oldCategory = mapper.map(oldCategoryEntiy, CategoryDto.class);
			} else {
				oldCategory = null;
			}
			/**
			 * kietlt 9:44 AM 2016/2/16
			 * 
			 * check category is exist
			 */
			if (categoryDto.getId() != null) {
				if (categoryRepo.exists(categoryDto.getId())) {
					String errorMessage = CategoryConstant.FAIL + CategoryConstant.EXIST;
					return errorMessage;
				}
				if (oldCategory != null && categoryDto.getName() != null) {
					if (categoryDto.getName().equalsIgnoreCase(oldCategory.getName())) {
						String errorMessage = CategoryConstant.FAIL + CategoryConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldCategory != null) {
					if (categoryDto.getName().equalsIgnoreCase(oldCategory.getName())) {
						String errorMessage = CategoryConstant.FAIL + CategoryConstant.EXIST;
						return errorMessage;
					}
				}
			}
			/**
			 * kietlt 2:15 AM 2016/2/16 Set default value for CreateDate,
			 * CreateBy, ModifyBy, ModifyDate
			 */
			categoryDto.setCreateTime(Calendar.getInstance().getTime());
			categoryDto.setModifyTime(Calendar.getInstance().getTime());
			categoryDto.setCreateBy("Default User");
			categoryDto.setModifyBy("Default User");

			List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
			if (customerRepo.exists(categoryDto.getCustomerId())) { // check id of table reference is exist
				CategoryValidator.checkCategoryData(errorList, categoryDto);// kietlt 9:44 AM  check validator
				
			}
			else{
				ValidatorErrorModelException validatorErrorModelException = new ValidatorErrorModelException();
				validatorErrorModelException.setErrorCode(UtilValidatorConstant.ERROR_CODE_NOT_FOUND);
				validatorErrorModelException.setErrorMessage(CategoryConstant.FAIL_NOT_FOUND_CUSTOMERID);
				errorList.add(validatorErrorModelException);
			}

			if (errorList.isEmpty()) { // kietlt 9:44 AM 2016/2/16 data of
										// category
										// is not error
				CategoryEntity categoryEntity = mapper.map(categoryDto, CategoryEntity.class);
				categoryRepo.saveAndFlush(categoryEntity);
				return CategoryConstant.SUCCESS_CREATE;
			} else { // kietlt 9:44 AM 2016/2/16 data of category is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = CategoryConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return CategoryConstant.FAIL;
	}

	/**
	 * delete id use to delete category by id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteId(@PathVariable(value = "id") Integer id) {
		if (id != null) {
			if (categoryRepo.exists(id)) { // kietlt 3:56 PM 2016/2/16 check id
											// is exist
				categoryRepo.delete(id);
				return CategoryConstant.SUCCESS_DEL;
			} else {
				return CategoryConstant.FAIL;
			}
		}
		return CategoryConstant.FAIL;
	}

	/**
	 * show category by category id
	 * 
	 * @param id
	 *
	 * @return: category
	 */
	@RequestMapping(value = "/listId/{id}", method = RequestMethod.GET)
	public CategoryDto listId(@PathVariable(value = "id") Integer id) {
		if (categoryRepo.exists(id)) {
			CategoryEntity categoryEntity = categoryRepo.findOne(id);
			CategoryDto categoryDto = mapper.map(categoryEntity, CategoryDto.class);
			return categoryDto;
		} else {
			return null;
		}
	}

	/**
	 * Disable category by category id
	 * 
	 * @param id
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/disableId/{id}", method = RequestMethod.GET)
	public String disableId(@PathVariable(value = "id") Integer id) {
		if (categoryRepo.exists(id)) {
			CategoryEntity serviceData = new CategoryEntity();
			serviceData = categoryRepo.findOne(id);
			serviceData.setIsActive(false);
			categoryRepo.saveAndFlush(serviceData);
			return CategoryConstant.SUCCESS_DEL;
		}
		return CategoryConstant.FAIL;
	}

	/**
	 * Edit category by category id
	 * 
	 * @param categoryEdited
	 *
	 * @return: String
	 */
	@RequestMapping(value = "/updateId", method = RequestMethod.POST)
	public String updateId(@RequestBody CategoryDto categoryEdited) {
		List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
		if (categoryRepo.exists(categoryEdited.getId())) {
			CategoryDto categoryDto = new CategoryDto();
			CategoryEntity categoryEntity = categoryRepo.findOne(categoryEdited.getId());
			categoryDto = mapper.map(categoryEntity, CategoryDto.class);
			categoryDto.setData(categoryEdited);
			/**
			 * kietlt 2:20 PM 2016/1/27 set defaul user modifed and modified
			 * date
			 */
			categoryDto.setModifyTime(Calendar.getInstance().getTime());
			categoryDto.setModifyBy("Default User");

			CategoryValidator.checkCategoryData(errorList, categoryDto);
			if (errorList.isEmpty()) {
				categoryEntity = mapper.map(categoryDto, CategoryEntity.class);
				categoryRepo.saveAndFlush(categoryEntity);
				return CategoryConstant.SUCCESS_UPDATE;
			} else { //// kietlt 3:18 PM 2016/1/27 data of service is error
				StringBuilder errorMessage = new StringBuilder();
				for (ValidatorErrorModelException error : errorList) {
					errorMessage.append(error.toString() + "\n");
				}
				String messageFail = CategoryConstant.FAIL + "\n" + errorMessage.toString();
				return messageFail;
			}

		}
		return CategoryConstant.FAIL;
	}
}
