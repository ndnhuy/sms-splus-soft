/**
 * 
 */
package vn.com.splussoftware.sms.utils.validator;

import java.util.List;

import vn.com.splussoftware.sms.model.constant.ProviderValidatorConstant;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.ProviderDto;

/**
 * @author KietLT
 * 
 *         11:00 AM
 * 
 *         2/16/2015
 *
 */
public class ProviderValidator {



	public static List<ValidatorErrorModelException> checkProviderData(List<ValidatorErrorModelException> errorList,
			ProviderDto dataProvider) {
		/**
		 * 
		 * check value of data Provider is null?
		 */
		if (UtilValidator.checkObjectIsNull(dataProvider.getName())) { // check
																		// NAME
																		// kietlt
																		// 11: 08
																		// AM
																		// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_NAME_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataProvider.getDescription())) { // check
			// DESCRIPTION kietlt 11: 08 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_DESCRIPTION_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProvider.getContactPointId())) { // check
			// CONTACT_POINT kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_CONTACT_POINT_ID_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProvider.getModifyTime())) { // check
																				// MODIFIED_DATE
																				// kietlt
																				// 11: 08
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_MODIFIED_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProvider.getModifyBy())) { // check
			ValidatorErrorModelException error = new ValidatorErrorModelException(); // MODIFIED_BY
																						// kietlt
																						// 11: 08
																						// AM
																						// 2016/2/16
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_MODIFIED_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProvider.getCreateBy())) { // check
			// CREATE_BY kietlt 11: 08 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_CREATE_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProvider.getCreateTime().toString())) { // check
			// CREATE_DATE kietlt 11: 08 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_CREATE_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProvider.getIsActive())) { // check
																			// IS_ACTIVE
																			// kietlt
																			// 11: 08
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_IS_ACTIVE_NULL);
			errorList.add(error);
		}

		/**
		 * check data service excess the limit
		 */

		if (UtilValidator.checkMaxSizeChar(dataProvider.getName())) { // check
																		// NAME
																		// kietlt
																		// 11: 08
																		// AM
																		// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_NAME_LENGTH);
			errorList.add(error);
		}

		if (UtilValidator.checkMaxSizeText(dataProvider.getDescription())) { // check
																				// description
																				// kietlt
																				// 11: 08
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_DESCRIPTION_LENGTH);
			errorList.add(error);
		}
		if (dataProvider.getContactPointId() != null) {
			if (dataProvider.getContactPointId()< 0) { // check
				// contact point id kietlt  11: 08 AM 2016/2/16
				ValidatorErrorModelException error = new ValidatorErrorModelException();
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
				error.setErrorMessage(ProviderValidatorConstant.ERROR_CONTACT_POINT_ID_SIZE);
				errorList.add(error);
			}
		}
		if (UtilValidator.checkMaxSizeChar(dataProvider.getCreateBy())) { // check
																			// creater
																			// kietlt
																			// 11: 08
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_CREATE_BY_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeChar(dataProvider.getModifyBy())) { // check
																			// modifier
																			// kietlt
																			// 11: 08
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ProviderValidatorConstant.ERROR_MODIFIED_BY_LENGTH);
			errorList.add(error);
		}
		return errorList;
	}





}
