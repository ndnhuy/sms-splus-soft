/**
 * 
 */
package vn.com.splussoftware.sms.utils.validator;

import java.util.List;

import vn.com.splussoftware.sms.model.constant.CustomerValidatorConstant;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.CustomerDto;

/**
 * @author KietLT
 * 
 *         11:00 AM
 * 
 *
 */
public class CustomerValidator {


	public static List<ValidatorErrorModelException> checkCustomerData(List<ValidatorErrorModelException> errorList,
			CustomerDto dataCustomer) {
		/**
		 * 
		 * check value of data customer is null?
		 */
		if (UtilValidator.checkObjectIsNull(dataCustomer.getName())) { // check
																		// NAME
																		// kietlt
																		// 11: 08
																		// AM
																		// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_NAME_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataCustomer.getDescription())) { // check
			// DESCRIPTION kietlt 11: 08 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_DESCRIPTION_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataCustomer.getContactPointId())) { // check
			// CONTACT_POINT kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_CONTACT_POINT_ID_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataCustomer.getModifyTime())) { // check
																				// MODIFIED_DATE
																				// kietlt
																				// 11: 08
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_MODIFIED_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataCustomer.getModifyBy())) { // check
			ValidatorErrorModelException error = new ValidatorErrorModelException(); // MODIFIED_BY
																						// kietlt
																						// 11: 08
																						// AM
																						// 2016/2/16
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_MODIFIED_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataCustomer.getCreateBy())) { // check
			// CREATE_BY kietlt 11: 08 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_CREATE_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataCustomer.getCreateTime().toString())) { // check
			// CREATE_DATE kietlt 11: 08 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_CREATE_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataCustomer.getIsActive())) { // check
																			// IS_ACTIVE
																			// kietlt
																			// 11: 08
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_IS_ACTIVE_NULL);
			errorList.add(error);
		}

		/**
		 * check data service excess the limit
		 */

		if (UtilValidator.checkMaxSizeChar(dataCustomer.getName())) { // check
																		// NAME
																		// kietlt
																		// 11: 08
																		// AM
																		// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_NAME_LENGTH);
			errorList.add(error);
		}

		if (UtilValidator.checkMaxSizeText(dataCustomer.getDescription())) { // check
																				// description
																				// kietlt
																				// 11: 08
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_DESCRIPTION_LENGTH);
			errorList.add(error);
		}
		if (dataCustomer.getContactPointId() != null) {
			if (dataCustomer.getContactPointId()< 0) { // check
				// contact point id kietlt  11: 08 AM 2016/2/16
				ValidatorErrorModelException error = new ValidatorErrorModelException();
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
				error.setErrorMessage(CustomerValidatorConstant.ERROR_CONTACT_POINT_ID_SIZE);
				errorList.add(error);
			}
		}
		if (UtilValidator.checkMaxSizeChar(dataCustomer.getCreateBy())) { // check
																			// creater
																			// kietlt
																			// 11: 08
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_CREATE_BY_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeChar(dataCustomer.getModifyBy())) { // check
																			// modifier
																			// kietlt
																			// 11: 08
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(CustomerValidatorConstant.ERROR_MODIFIED_BY_LENGTH);
			errorList.add(error);
		}
		return errorList;
	}



}
