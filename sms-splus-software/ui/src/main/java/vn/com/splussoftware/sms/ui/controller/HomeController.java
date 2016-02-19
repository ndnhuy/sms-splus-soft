package vn.com.splussoftware.sms.ui.controller;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import vn.com.splussoftware.sms.model.repository.auth.GroupRepository;
import vn.com.splussoftware.sms.utils.AuthenticationFacade;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant.Permission;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant.PermissionTargetType;
import vn.com.splussoftware.sms.utils.service.AuthenticationService;


@Controller
public class HomeController {
    
	@Autowired
	AuthenticationFacade auth;
	
    @RequestMapping(value="/secure", method=RequestMethod.GET)
    @ResponseBody
    public String home(Model model, HttpServletRequest httpRequest) throws JsonParseException, JsonMappingException, IOException {
    	    	
    	String message = "hello " + auth.getCurrentLoggedInUser().getUserkey() + " with authorities are < ";
    	
    	for (GrantedAuthority a : auth.getAuthentication().getAuthorities()) {
    		message += a.getAuthority() + " ";
    	}
    	
    	message += "> ID: " + auth.getCurrentLoggedInUser().getId();
    	
        return message;
    }
    
    
    @Autowired
    private GroupRepository groupRepo;
    
    @Autowired
    private AuthenticationService authService;
    
    @RequestMapping(value="/test", method=RequestMethod.GET)
    @ResponseBody
    public String test(@RequestParam("userId") Integer userId,
    					@RequestParam("targetType") String targetType,
    					@RequestParam("targetId") Integer targetId,
    					@RequestParam("permission") String permission) {
    	
    	PermissionTargetType enumTargetType = PermissionTargetType.valueOf(targetType);
    	Permission enumPermission = Permission.valueOf(permission);
    	
    	return Boolean.toString(authService.checkPermission(userId, enumTargetType, targetId, enumPermission));
    }
    
}

