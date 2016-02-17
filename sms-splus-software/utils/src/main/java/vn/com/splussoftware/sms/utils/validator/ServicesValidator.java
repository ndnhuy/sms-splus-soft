/**
 * @Author: @author KietLT
 *
 * @Time: 5:09:41 PM
 */
package vn.com.splussoftware.sms.utils.validator;
/**
 * @Author: @author KietLT
 *
 * @Time: 5:09:41 PM
 */

import java.util.List;

import vn.com.splussoftware.sms.model.constant.ServicesValidatorConstant;
import vn.com.splussoftware.sms.model.entity.ServicesEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;


/**
 * @author KietLT
 *
 */
public class ServicesValidator {

	/**
	 * 
	 * KIETLT 
	 * 
	 * max size is 225 symbol
	 * 
	 * @param Serivce
	 *
	 * @return: Boolean
	 */

	/**
	 * checkServiceData use to check data of service after save into database;
	 * @param errorList
	 * @param dataService
	 * @return
	 */
	public static List<ValidatorErrorModelException> checkServiceData(List<ValidatorErrorModelException> errorList,
			ServicesEntity dataService) {
		/**
		 * 
		 * check value of data serivce is null?
		 */
		if (UtilValidator.checkObjectIsNull(dataService.getTitle())) { // check
																		// TITLE kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_TITLE_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataService.getDescription())) { // check 
			// DESCRIPTION kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_DESCRIPTION_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataService.getPriority())) { // check
																			// PRIORITY kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_PRIORITY_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataService.getProviderId())) { // check
																			// PROCESS_ID kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_PROCESS_ID_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataService.getSlaFixTime())) { // check
																			// SLA_FIX_TIME kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_SLA_FIX_TIME_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataService.getSlaImplementTime())) { // check 
																					// SLA_IMPLEMENT_TIME kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_SLA_IMPLEMENT_TIME_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataService.getSlaResponseTime())) { // check
																					// SLA_RESPONSE_TIME kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_SLA_RESPONSE_TIME_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataService.getModifyDate())) { // check
																			// MODIFIED_DATE kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_MODIFIED_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataService.getModifyBy())) { // check
			ValidatorErrorModelException error = new ValidatorErrorModelException(); // MODIFIED_BY kietlt 3:30 PM 2016/1/27
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_MODIFIED_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataService.getCreateBy())) { // check
			// CREATE_BY kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_CREATE_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataService.getCreateDate().toString())) { // check 
			// CREATE_DATE kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_CREATE_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataService.getActive())) { // check
																		// IS_ACTIVE kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_IS_ACTIVE_NULL);
			errorList.add(error);
		}

		/**
		 * check data service excess the limit
		 */

		if (UtilValidator.checkMaxSizeChar(dataService.getTitle())) { // check
																		// title kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_TITLE_LENGTH);
			errorList.add(error);
		}

		if (UtilValidator.checkMaxSizeText(dataService.getDescription())) { // check
																			// description kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_DESCRIPTION_LENGTH);
			errorList.add(error);
		}
		if (dataService.getPriority() != null) {
			if (dataService.getPriority() < 0 || dataService.getPriority() > 5) { // check
				// priority kietlt 3:30 PM 2016/1/27
				ValidatorErrorModelException error = new ValidatorErrorModelException();
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
				error.setErrorMessage(ServicesValidatorConstant.ERROR_PRIORITY_SIZE);
				errorList.add(error);
			}
		}

		if (dataService.getProviderId() != null) {
			if (dataService.getProviderId() < 0) { // check
				// proccess id kietlt 3:30 PM 2016/1/27
				ValidatorErrorModelException error = new ValidatorErrorModelException();
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
				error.setErrorMessage(ServicesValidatorConstant.ERROR_PROCESS_ID_SIZE);
				errorList.add(error);
			}
		}
		if (UtilValidator.checkMaxSizeChar(dataService.getCreateBy())) { // check
																			// creater kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_CREATE_BY_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeChar(dataService.getModifyBy())) { // check
																			// modifier kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ServicesValidatorConstant.ERROR_MODIFIED_BY_LENGTH);
			errorList.add(error);
		}
		return errorList;
	}
}
