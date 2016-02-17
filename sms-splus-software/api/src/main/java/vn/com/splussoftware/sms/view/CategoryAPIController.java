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
import vn.com.splussoftware.sms.model.entity.CategoryEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.model.repository.CategoryRepository;
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
	public String addCategory (@RequestBody(required = true) CategoryDto categoryDto) {
		if (categoryDto != null) {
			CategoryEntity oldCategoryEntiy = categoryRepo.findByName(categoryDto.getName());
			CategoryDto oldCategory;
			if (oldCategoryEntiy!=null){
				oldCategory = mapper.map(oldCategoryEntiy, CategoryDto.class);
			}
			else {
				oldCategory =null;
			}
			/**
			 * kietlt 9:44  AM 2016/2/16
			 * 
			 * check category is exist
			 */
			if (categoryDto.getId() != null) {
				if (categoryRepo.exists(categoryDto.getId())) {
					String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
					return errorMessage;
				}
				if (oldCategory != null && categoryDto.getName() != null) {
					if (categoryDto.getName().equalsIgnoreCase(oldCategory.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
						return errorMessage;
					}
				}
			} else {
				if (oldCategory != null) {
					if (categoryDto.getName().equalsIgnoreCase(oldCategory.getName())) {
						String errorMessage = ServicesConstant.FAIL + ServicesConstant.EXIST;
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
			CategoryValidator.checkCategoryData(errorList, categoryDto); // kietlt
																	// 9:44 
																	// AM
																	// 2016/2/16
																	// check
																	// data of
																	// category
																	// input
			if (errorList.isEmpty()) { // kietlt 9:44  AM 2016/2/16 data of
										// category
										// is not error
				CategoryEntity categoryEntity = mapper.map(categoryDto, CategoryEntity.class);
				categoryRepo.saveAndFlush(categoryEntity);
				return ServicesConstant.SUCCESS;
			} else { // kietlt 9:44  AM 2016/2/16 data of category is error
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
	 * delete id use to delete category by id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteId(@PathVariable(value = "id") Integer id) {
		if (id != null) {
			if (categoryRepo.exists(id)) { // kietlt 3:56 PM 2016/2/16 check id
											// is exist
				categoryRepo.delete(id);
				return ServicesConstant.SUCCESS;
			} else {
				return ServicesConstant.FAIL;
			}
		}
		return ServicesConstant.FAIL;
	}

}
