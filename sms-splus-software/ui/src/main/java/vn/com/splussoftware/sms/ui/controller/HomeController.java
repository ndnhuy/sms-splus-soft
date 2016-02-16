package vn.com.splussoftware.sms.ui.controller;



import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import vn.com.splussoftware.sms.utils.AuthenticationFacade;


@Controller
public class HomeController {
    
	@Autowired
	AuthenticationFacade auth;
	
    @RequestMapping(value="/secure", method=RequestMethod.GET)
    @ResponseBody
    public String home(Model model, HttpServletRequest httpRequest) throws JsonParseException, JsonMappingException, IOException {
    	    	
        return "hello " + auth.getCurrentLoggedInUser().getUserkey();
    }
    
}

