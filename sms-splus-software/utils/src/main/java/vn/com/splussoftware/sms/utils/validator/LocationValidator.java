package vn.com.splussoftware.sms.utils.validator;

import java.util.List;

import vn.com.splussoftware.sms.model.constant.LocationValidatorConstant;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.LocationDto;

/**
 * @author KietLT
 * 
 *         10:26 AM
 * 
 *         2/16/2015
 *
 */
public class LocationValidator {

	public static List<ValidatorErrorModelException> checkLocationData(List<ValidatorErrorModelException> errorList,
			LocationDto dataLocation) {
		/**
		 * 
		 * check value of data Location is null?
		 */
		if (UtilValidator.checkObjectIsNull(dataLocation.getName())) { // check
																		// NAME
																		// kietlt
																		// 9:32
																		// AM
																		// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(LocationValidatorConstant.ERROR_NAME_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataLocation.getDescription())) { // check
			// DESCRIPTION kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(LocationValidatorConstant.ERROR_DESCRIPTION_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataLocation.getProviderId())) { // check
			// PROVIDER_ID kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(LocationValidatorConstant.ERROR_PROVIDER_ID_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataLocation.getModifyTime())) { // check
																				// MODIFIED_DATE
																				// kietlt
																				// 9:32
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(LocationValidatorConstant.ERROR_MODIFIED_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataLocation.getModifyBy())) { // check
			ValidatorErrorModelException error = new ValidatorErrorModelException(); // MODIFIED_BY
																						// kietlt
																						// 9:32
																						// AM
																						// 2016/2/16
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(LocationValidatorConstant.ERROR_MODIFIED_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataLocation.getCreateBy())) { // check
			// CREATE_BY kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(LocationValidatorConstant.ERROR_CREATE_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataLocation.getCreateTime().toString())) { // check
			// CREATE_DATE kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(LocationValidatorConstant.ERROR_CREATE_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataLocation.getIsActive())) { // check
																			// IS_ACTIVE
																			// kietlt
																			// 9:32
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(LocationValidatorConstant.ERROR_IS_ACTIVE_NULL);
			errorList.add(error);
		}

		/**
		 * check data service excess the limit
		 */

		if (UtilValidator.checkMaxSizeChar(dataLocation.getName())) { // check
																		// NAME
																		// kietlt
																		// 9:32
																		// AM
																		// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(LocationValidatorConstant.ERROR_NAME_LENGTH);
			errorList.add(error);
		}

		if (UtilValidator.checkMaxSizeText(dataLocation.getDescription())) { // check
																				// description
																				// kietlt
																				// 9:32
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(LocationValidatorConstant.ERROR_DESCRIPTION_LENGTH);
			errorList.add(error);
		}
		if (dataLocation.getProviderId() != null) {
			if (dataLocation.getProviderId() < 0) { // check
				// provider id kietlt  9:32 AM 2016/2/16
				ValidatorErrorModelException error = new ValidatorErrorModelException();
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
				error.setErrorMessage(LocationValidatorConstant.ERROR_PROVIDER_ID_SIZE);
				errorList.add(error);
			}
		}
		if (UtilValidator.checkMaxSizeChar(dataLocation.getCreateBy())) { // check
																			// creater
																			// kietlt
																			// 9:32
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(LocationValidatorConstant.ERROR_CREATE_BY_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeChar(dataLocation.getModifyBy())) { // check
																			// modifier
																			// kietlt
																			// 9:32
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(LocationValidatorConstant.ERROR_MODIFIED_BY_LENGTH);
			errorList.add(error);
		}
		return errorList;
	}

}
