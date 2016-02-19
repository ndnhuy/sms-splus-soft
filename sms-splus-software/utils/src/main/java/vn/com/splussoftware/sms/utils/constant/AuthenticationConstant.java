package vn.com.splussoftware.sms.utils.constant;

public class AuthenticationConstant {
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_MOD = "mod";
	
	public static final String USER_STATUS_ACTIVE = "active";
	public static final String USER_STATUS_BANNED = "banned";
	
	public static final String SERVER_URL = "http://localhost:8182";
	
	
	public static enum Permission {
		read,
		write
	}
	public static enum PermissionTargetType {
		ticket,
		service
	}
	
//	public static enum Role {
//		admin("role.admin"),
//		mod("role.mod")
//	}
}
