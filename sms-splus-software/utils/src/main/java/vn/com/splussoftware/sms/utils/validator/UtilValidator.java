/**
 * @Author: @author KietLT
 *
 * @Time: 9:44:55 AM
 */
package vn.com.splussoftware.sms.utils.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vn.com.splussoftware.sms.utils.service.jsonhandler.ConditionData;

/**
 * @Author: @author KietLT
 *
 * @Time: 9:44:55 AM
 */

public class UtilValidator {
	/**
	 * kietlt 3:30 PM 2016/1/27 checkMaxSizeChar use to check max length of
	 * input string
	 * 
	 * @param stringNeedCheck
	 * @return: Boolean be called by: ServicesValidator.checkServiceData()
	 */
	public static Boolean checkMaxSizeChar(String stringNeedCheck) {

		if (stringNeedCheck != null) {
			if (stringNeedCheck.length() > 255) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static Boolean checkMaxSizeText(String stringNeedCheck) {

		if (stringNeedCheck != null) {
			if (stringNeedCheck.length() > 1000) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * kietlt 3:30 PM 2016/1/27
	 * 
	 * checkNull use to check null input object
	 * 
	 * @param Object
	 *
	 * @return: Boolean be called by: ServicesValidator.checkServiceData()
	 * 
	 */
	public static Boolean checkObjectIsNull(Object objectNeedCheck) {
		if (objectNeedCheck == null) {
			return true;
		}
		return false;
	}

	public static Boolean checkMaxLength(String stringNeedCheck, int max) {

		if (stringNeedCheck != null) {
			if (stringNeedCheck.length() < max) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static Boolean checkMinLength(String stringNeedCheck, int min) {

		if (stringNeedCheck != null) {
			if (stringNeedCheck.length() > min) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static Boolean checkRequired(String stringNeedCheck, boolean isRequired) {

		if (isRequired == false) {
			return true;
		} else {
			if (stringNeedCheck.trim().length() != 0) {
				return true;
			}
		}

		return false;
	}

	public static Boolean checkValidDataOption(String stringNeedCheck, List<ConditionData> list) {

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getValue().equals(stringNeedCheck)) {
				return true;
			}

		}
		return false;
	}

	public static Boolean checkDateFormat(String stringNeedCheck, String format) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(stringNeedCheck);
			if (stringNeedCheck.equals(sdf.format(date))) {
				return true;
			}
		} catch (ParseException ex) {
			return false;
		}
		return false;
	}

	public static Boolean checkDataType(String stringNeedCheck, String format) {
		if (!stringNeedCheck.trim().equals(format.trim())) {
			return true;
		}
		return false;
	}

	public static Boolean checkNumberFormat(String stringNeedCheck, String format) {
		if (stringNeedCheck.matches(format)) {
			return true;
		}
		return false;
	}

	public static Boolean checkHeader(List<String> listNeedCheck, List<String> format) {
		if (listNeedCheck.size() != format.size()) {
			return true;
		} else {
			for (int i = 0; i < format.size(); i++) {
				if (listNeedCheck.get(i) != format.get(i)) {
					return true;
				}
			}
			return false;
		}
	}

}
