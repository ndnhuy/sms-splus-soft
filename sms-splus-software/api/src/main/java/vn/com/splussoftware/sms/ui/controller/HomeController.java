package vn.com.splussoftware.sms.ui.controller;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.splussoftware.sms.ui.common.UnauthorizedUIClientException;
import vn.com.splussoftware.sms.utils.AuthenticationFacade;


@Controller
public class HomeController {
    
	@Autowired
	AuthenticationFacade auth;
	
    @RequestMapping(value="/", method=RequestMethod.GET)
    @ResponseBody
    public String home(Model model, HttpServletRequest httpRequest) throws JsonParseException, JsonMappingException, IOException {
    	
        return "hello " + auth.getCurrentLoggedInUser().getUsername();
    }
    
}

