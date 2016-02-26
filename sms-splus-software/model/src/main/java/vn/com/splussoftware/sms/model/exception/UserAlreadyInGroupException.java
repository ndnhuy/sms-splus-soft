package vn.com.splussoftware.sms.model.exception;

import vn.com.splussoftware.sms.model.commonutils.ApplicationContextProvider;
import vn.com.splussoftware.sms.model.constant.MessagePropertiesConstant;


public class UserAlreadyInGroupException extends RuntimeException {
	
	public UserAlreadyInGroupException(String userkey, String groupName) {
		super(ApplicationContextProvider.getApplicationContext().getMessage(
				UserAlreadyInGroupException.class.getName() + MessagePropertiesConstant.KEY_USER_ALREADY_IN_GROUP, 
				new Object[] {userkey, groupName}, 
				null));
		
		
		
	}
}
