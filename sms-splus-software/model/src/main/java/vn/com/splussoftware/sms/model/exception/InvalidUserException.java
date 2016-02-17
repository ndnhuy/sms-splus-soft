package vn.com.splussoftware.sms.model.exception;

import vn.com.splussoftware.sms.model.entity.auth.SMSUserEntity;

/**
 * 
 * Thrown if an {@link SMSUserEntity} does not have {@code ACTIVE} status
 * 
 * @author HuyNDN
 */
@SuppressWarnings("serial")
public class InvalidUserException extends RuntimeException {

	public InvalidUserException(String msg) {
		super(msg);
	}

}
