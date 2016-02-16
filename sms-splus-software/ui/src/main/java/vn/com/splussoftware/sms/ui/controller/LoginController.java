package vn.com.splussoftware.sms.ui.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import vn.com.splussoftware.sms.ui.common.OAuthTokenHelper;
import vn.com.splussoftware.sms.utils.AuthenticationFacade;
import vn.com.splussoftware.sms.utils.dto.LoginFormDto;


@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	AuthenticationFacade auth;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginPage(Model model, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		
		//TODO need refactor, should not using embbeded code 'anonymousUser'
		if (!auth.getCurrentLoggedInUser().getUserkey().equals("anonymousUser")) {
			return "redirect:/";
		}
		
		logger.info("Show login page");
		
		model.addAttribute("loginFormDto", new LoginFormDto());
		
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute LoginFormDto loginFormDto, Model model, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws JsonParseException, JsonMappingException, IOException {
		
		logger.info("Login as " + loginFormDto.getUsername() + " (password: " + loginFormDto.getPassword() + ")");
		
		Map map = OAuthTokenHelper.getValidAccessToken(loginFormDto.getUsername(), loginFormDto.getPassword());
		
		httpResponse.addCookie(new Cookie("access_token", (String) map.get("access_token")));
		httpResponse.addCookie(new Cookie("refresh_token", (String) map.get("refresh_token")));
		
		return "redirect:/secure";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(Model model, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		
		if (!auth.getCurrentLoggedInUser().getUserkey().equals("anonymousUser")) {
			Cookie[] cookies = httpRequest.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					cookie.setMaxAge(0);
					httpResponse.addCookie(cookie);
				}
			}
		}
		
		logger.info("Show login page");
		
		model.addAttribute("loginFormDto", new LoginFormDto());
		
		return "redirect:/login";
	}
}
