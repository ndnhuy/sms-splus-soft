package vn.com.splussoftware.sms.model.exception;

public class UserAlreadyInGroupException extends RuntimeException {
	
	public UserAlreadyInGroupException(String userkey, String groupName) {
		super("The user '" + userkey + "' is already in group '" + groupName + "'");
	}
}
