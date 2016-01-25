package vn.com.splussoftware.sms.controller;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;
import vn.com.splussoftware.sms.utils.service.LoginMethodService;

@Controller
@RequestMapping("/admin/loginMethod")
public class LoginMethodController {
	
	@Autowired
	private LoginMethodService loginMethodService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getLoginMethodManagementPage(Model model) {
		List<LoginMethodDto> dtoLoginMethods = loginMethodService.findAll();
		
		model.addAttribute("loginMethods", dtoLoginMethods);
		model.addAttribute("loginMethodDto", new LoginMethodDto());
		
		return "hello";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String loginMethodSubmit(@ModelAttribute LoginMethodDto loginMethodDto, Model model) {
		
		System.out.println(ToStringBuilder.reflectionToString(loginMethodDto));
		
		return "redirect:/";
	}
}
