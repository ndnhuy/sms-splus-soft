package vn.com.splussoftware.sms.ui.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.splussoftware.sms.utils.dto.LoginMethodDto;
import vn.com.splussoftware.sms.utils.dto.loginMethod.LoginMethodForm;
import vn.com.splussoftware.sms.utils.service.LoginMethodService;

/**
 * Login Method Controller
 * 
 * @author HuyNDN
 * created on Feb 25, 2016
 */
@Controller
@RequestMapping("/loginMethods")
public class LoginMethodController {
	
	@Autowired
	private LoginMethodService loginMethodService;
	
	/**
	 * 
	 * 
	 * 
	 * @param model
	 * @return
	 * @author HuyNDN created on Feb 25, 2016
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String getLoginMethodManagementPage(Model model) {
		
		
		LoginMethodForm loginMethodForm = new LoginMethodForm();
		loginMethodForm.setLoginMethods(loginMethodService.findAll());
		model.addAttribute("loginMethodForm", loginMethodForm);
		model.addAttribute("loginMethodDto", new LoginMethodDto());
		
		return "loginMethod/login-method";
	}
	
	/**
	 * 
	 * @param model
	 * @param loginMethodDto
	 * @param bindingResult
	 * @return
	 * @author HuyNDN created on Feb 25, 2016
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String loginMethodSubmit(Model model, @Valid LoginMethodDto loginMethodDto, BindingResult bindingResult) {
		
		// Check if the input loginMethodDto has any validation error.
		if (bindingResult.hasErrors()) {
			
			LoginMethodForm loginMethodForm = new LoginMethodForm();
			loginMethodForm.setLoginMethods(loginMethodService.findAll());
			model.addAttribute("loginMethodForm", loginMethodForm);
			return "loginMethod/login-method";
		}
		
		loginMethodService.save(loginMethodDto);
		
		return "redirect:/loginMethods";
	}
	
	/**
	 * 
	 * 
	 * @param model
	 * @param loginMethodForm
	 * @param redirectAttr
	 * @return
	 * @author HuyNDN created on Feb 25, 2016
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String udpateLoginMethod(Model model, LoginMethodForm loginMethodForm, RedirectAttributes redirectAttr) {
		
//		if (bindingResult.hasErrors()) {
//			loginMethodForm = new LoginMethodForm();
//			loginMethodForm.setLoginMethods(loginMethodService.findAll());
//			model.addAttribute("loginMethodForm", loginMethodForm);
//			model.addAttribute("loginMethodDto", new LoginMethodDto());
//			return "loginMethod/login-method";
//		}
		
		for (LoginMethodDto dto : loginMethodForm.getLoginMethods()) {
			loginMethodService.save(dto);
		}
		
		return "redirect:/loginMethods";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deleteLoginMethod(@PathVariable("id") Integer id) {
		loginMethodService.deleteLoginMethod(id);
		
		return "redirect:/loginMethods";
	}
}
