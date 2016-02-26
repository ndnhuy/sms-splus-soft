package vn.com.splussoftware.sms.utils.constant;

public class ExcelValidatorConstant {
	public static Integer ERROR_CODE_NULL = 1001;
	/**
	 * code of error is limit length
	 */
	public static Integer ERROR_CODE_LENGTH = 1002;
	public static Integer ERROR_CODE_DATE = 1003;
	public static Integer ERROR_CODE_NUMBER = 1004;
	public static Integer ERROR_CODE_SELECT = 1005;
	public static Integer ERROR_CODE_TEXT_NULL = 1006;
	public static Integer ERROR_CODE_HEADER = 1007;
	public static Integer ERROR_CODE_FORMAT = 1008;
	/**
	 * message of error is less than 0
	 */
	public static String ERROR_TEXT_LENGTH = "Text out of length:";
	public static String ERROR_DATE_FORMAT = "Invalid date format:";
	public static String ERROR_NUMBER_FORMAT = "Invalid number format:";
	public static String ERROR_SELECT_DATA = "Invalid data:";
	public static String ERROR_TEXT_NULL = "Null:";
	public static String ERROR_HEADER = "Invalid Header:";
	public static String ERROR_FORMAT = "Invalid Format";
}
