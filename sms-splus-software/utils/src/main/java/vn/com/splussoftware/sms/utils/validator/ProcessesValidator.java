/**
 * 
 */
package vn.com.splussoftware.sms.utils.validator;

import java.util.List;

import vn.com.splussoftware.sms.model.constant.ProcessesValidatorConstant;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.dto.ProcessesDto;

/**
 * @author KietLT
 * 
 *         10:26 AM
 * 
 *         2/16/2015
 *
 */
public class ProcessesValidator {


	public static List<ValidatorErrorModelException> checkProcessesData(List<ValidatorErrorModelException> errorList,
			ProcessesDto dataProcesses) {
		/**
		 * 
		 * check value of data Processes is null?
		 */
		if (UtilValidator.checkObjectIsNull(dataProcesses.getName())) { // check
																		// NAME
																		// kietlt
																		// 9:32
																		// AM
																		// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_NAME_NULL);
			errorList.add(error);
		}

		if (UtilValidator.checkObjectIsNull(dataProcesses.getDescription())) { // check
			// DESCRIPTION kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_DESCRIPTION_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getServiceId())) { // check
			// SERVICE_ID kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_SERVICE_ID_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getLocationId())) { // check
			// LOCATION_ID kietlt 3:30 PM 2016/1/27
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_LOCATION_ID_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getInput())) { // check
			// INPUT kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_INPUT_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getOutput())) { // check
			// OUTPUT kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_OUTPUT_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getWorkflow())) { // check
			// WORKFLOW kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_WORK_FLOW_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getModifyTime())) { // check
																				// MODIFIED_DATE
																				// kietlt
																				// 9:32
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_MODIFIED_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getModifyBy())) { // check
			ValidatorErrorModelException error = new ValidatorErrorModelException(); // MODIFIED_BY
																						// kietlt
																						// 9:32
																						// AM
																						// 2016/2/16
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_MODIFIED_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getCreateBy())) { // check
			// CREATE_BY kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_CREATE_BY_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getCreateTime().toString())) { // check
			// CREATE_DATE kietlt 9:32 AM 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_CREATE_DATE_NULL);
			errorList.add(error);
		}
		if (UtilValidator.checkObjectIsNull(dataProcesses.getIsActive())) { // check
																			// IS_ACTIVE
																			// kietlt
																			// 9:32
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_IS_ACTIVE_NULL);
			errorList.add(error);
		}

		/**
		 * check data service excess the limit
		 */

		if (UtilValidator.checkMaxSizeChar(dataProcesses.getName())) { // check
																		// NAME
																		// kietlt
																		// 9:32
																		// AM
																		// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_NAME_LENGTH);
			errorList.add(error);
		}

		if (UtilValidator.checkMaxSizeText(dataProcesses.getDescription())) { // check
																				// description
																				// kietlt
																				// 9:32
																				// AM
																				// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_DESCRIPTION_LENGTH);
			errorList.add(error);
		}
		if (dataProcesses.getServiceId() != null) {
			if (dataProcesses.getServiceId() < 0) { // check
				// Service id kietlt  9:32 AM 2016/2/16
				ValidatorErrorModelException error = new ValidatorErrorModelException();
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
				error.setErrorMessage(ProcessesValidatorConstant.ERROR_SERVICE_ID_SIZE);
				errorList.add(error);
			}
		}
		if (dataProcesses.getLocationId() != null) {
			if (dataProcesses.getLocationId() < 0) { // check
				// Location id kietlt  9:32 AM 2016/2/16
				ValidatorErrorModelException error = new ValidatorErrorModelException();
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
				error.setErrorMessage(ProcessesValidatorConstant.ERROR_LOCATION_ID_SIZE);
				errorList.add(error);
			}
		}
		if (UtilValidator.checkMaxSizeChar(dataProcesses.getCreateBy())) { // check
																			// creater
																			// kietlt
																			// 9:32
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_CREATE_BY_LENGTH);
			errorList.add(error);
		}
		if (UtilValidator.checkMaxSizeChar(dataProcesses.getModifyBy())) { // check
																			// modifier
																			// kietlt
																			// 9:32
																			// AM
																			// 2016/2/16
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_LENGTH);
			error.setErrorMessage(ProcessesValidatorConstant.ERROR_MODIFIED_BY_LENGTH);
			errorList.add(error);
		}
		return errorList;
	}



}
