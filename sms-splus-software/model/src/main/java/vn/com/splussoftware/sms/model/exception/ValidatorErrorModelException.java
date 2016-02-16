/**
 * @Author: @author KietLT
 *
 * @Time: 9:52:50 AM
 */
package vn.com.splussoftware.sms.model.exception;

import lombok.Data;

/**
 * @author KietLT
 *
 */
@Data
public class ValidatorErrorModelException {
	/**
	 * code of error
	 */
	private int errorCode;
	
	/**
	 * message of error
	 */
	private String errorMessage;
}
