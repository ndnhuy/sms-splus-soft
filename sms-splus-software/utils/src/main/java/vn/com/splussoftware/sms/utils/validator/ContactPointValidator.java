/**
 * 
 */
package vn.com.splussoftware.sms.utils.validator;

import java.util.List;

import vn.com.splussoftware.sms.model.constant.ContactPointValidatorConstant;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.ContactPointDto;

/**
 * @author KietLT
 * 
 *         10:26 AM
 * 
 *         2/16/2015
 *
 */
public class ContactPointValidator {

	public static List<ValidatorErrorModelException> checkContactPointData(List<ValidatorErrorModelException> errorList,
			ContactPointDto dataContactPoint) {
		/**
		 * 
		 * check value of data Contact point is null?
		 */
		if (UtilValidator.checkObjectIsNull(dataContactPoint.getName())) { // check
																			// NAME
																			// kietlt
																			// 9:32
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_NAME_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataContactPoint.getDescription())) { // check
			// DESCRIPTION kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_DESCRIPTION_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataContactPoint.getEmail())) { // check
			// EMAIL
			// kietlt
			// 9:32
			// AM
			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_EMAIL_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataContactPoint.getPhone())) { // check
			// PHONE
			// kietlt
			// 9:32
			// AM
			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_PHONE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataContactPoint.getAddress())) { // check
			// ADDRESS kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_ADDRESS_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataContactPoint.getModifyTime())) { // check
																					// MODIFIED_DATE
																					// kietlt
																					// 9:32
																					// AM
																					// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_MODIFIED_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataContactPoint.getModifyBy())) { // check
			ValidatorErrorModelException error = new ValidatorErrorModelException(); // MODIFIED_BY
																						// kietlt
																						// 9:32
																						// AM
																						// 2016/2/16
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_MODIFIED_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataContactPoint.getCreateBy())) { // check
			// CREATE_BY kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_CREATE_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataContactPoint.getCreateTime().toString())) { // check
			// CREATE_DATE kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_CREATE_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataContactPoint.getIsActive())) { // check
																				// IS_ACTIVE
																				// kietlt
																				// 9:32
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_IS_ACTIVE_NULL);
			errorList.add(error);
		}

		/**
		 * check data service excess the limit
		 */

		if (UtilValidator.checkMaxSizeChar(dataContactPoint.getName())) { // check
																			// NAME
																			// kietlt
																			// 9:32
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_NAME_LENGTH);
			errorList.add(error);
		}

		if (UtilValidator.checkMaxSizeText(dataContactPoint.getDescription())) { // check
																					// description
																					// kietlt
																					// 9:32
																					// AM
																					// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_DESCRIPTION_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeChar(dataContactPoint.getEmail())) { // check
			// EMAIL
			// kietlt
			// 9:32
			// AM
			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_EMAIL_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeChar(dataContactPoint.getPhone())) { // check
			// PHONE
			// kietlt
			// 9:32
			// AM
			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_PHONE_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeText(dataContactPoint.getAddress())) { // check
			// address
			// kietlt
			// 9:32
			// AM
			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_ADDRESS_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeChar(dataContactPoint.getCreateBy())) { // check
																				// creater
																				// kietlt
																				// 9:32
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_CREATE_BY_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeChar(dataContactPoint.getModifyBy())) { // check
																				// modifier
																				// kietlt
																				// 9:32
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ContactPointValidatorConstant.ERROR_MODIFIED_BY_LENGTH);
			errorList.add(error);
		}
		return errorList;
	}

}
