package vn.com.splussoftware.sms.utils.validator;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;

public class FileUploadValidator {

	public static boolean checkUploadFile(List<ValidatorErrorModelException> errorList, MultipartFile fileUpload) {
		double maxSize = 1000000;
		String type = "application/vnd.ms-excel";

		if (UtilValidator.checkObjectIsNull(fileUpload)) {
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage("Null");
			errorList.add(error);
			return false;
		}
		if (UtilValidator.checkFileType(fileUpload.getContentType(), type)) {
			ValidatorErrorModelException error = new ValidatorErrorModelException();
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage("Type");
			errorList.add(error);
			return false;
		} else {
			if (UtilValidator.checkFileSize(fileUpload.getSize(), maxSize)) {
				ValidatorErrorModelException error = new ValidatorErrorModelException();
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
				error.setErrorMessage("Size");
				errorList.add(error);
				return false;
			}
		}

		return true;
	}
}
