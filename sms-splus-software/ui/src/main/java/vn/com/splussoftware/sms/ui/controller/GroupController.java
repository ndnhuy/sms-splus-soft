package vn.com.splussoftware.sms.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.apache.poi.ss.formula.functions.T;
import org.apache.tomcat.util.net.jsse.openssl.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.splussoftware.sms.model.exception.UserAlreadyInGroupException;
import vn.com.splussoftware.sms.utils.constant.AuthenticationConstant;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionDto;
import vn.com.splussoftware.sms.utils.dto.UserDto;
import vn.com.splussoftware.sms.utils.dto.group.GroupDto;
import vn.com.splussoftware.sms.utils.dto.group.UserAdditionForm;
import vn.com.splussoftware.sms.utils.service.GroupService;
import vn.com.splussoftware.sms.utils.service.UserService;

@Controller
public class GroupController {
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
	
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/groups", method=RequestMethod.GET)
	public String showGroups(Model model, GroupDto groupDto, UserAdditionForm userAdditionForm) {
		logger.info("Show groups");
		
		model.addAttribute("groups", groupService.findAll());
		model.addAttribute("users", listOfUsersWithoutAdminOrMod(userService.findAll()));
		
		return "group-user-management";
	}
	
	/**
	 * Get all users and remove 'admin' or 'mod'
	 * <p>
	 * This list is used to show on the select box for adding user to group.
	 * 
	 * @param users
	 * @return list of users after removing 'admin' or 'mod'
	 */
	private List<UserDto> listOfUsersWithoutAdminOrMod(List<UserDto> users) {
		users.removeIf(new Predicate<UserDto>() {
			
			@Override
			public boolean test(UserDto v) {
				/*
				 * If this user has permission 'admin' or 'mod', then remove it from the list.
				 */
				if (v.getGlobalPermissions() != null) {
					if (v.getGlobalPermissions().stream().anyMatch(
							p -> p.getPermission().equals(AuthenticationConstant.ROLE_ADMIN)
									|| p.getPermission().equals(AuthenticationConstant.ROLE_MOD)
						))
					return true;
				}
				
		
				return false;
			}
			
		});
		
		return users;
	}

	@RequestMapping(value="/groups", method=RequestMethod.POST)
	public String addGroup(@Valid GroupDto groupDto, BindingResult bindingResult, Model model, final RedirectAttributes redirectAttr) {
		
		logger.info("Add group '{}' - '{}'", groupDto.getGroupName(), groupDto.getDescription());
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("groups", groupService.findAll());
			return "group-user-management";
		}
		
		groupDto.setUsers(null);
		try {
			groupService.save(groupDto);
		} catch (EntityExistsException ex) {
			redirectAttr.addFlashAttribute("error", ex.getMessage());
			return "redirect:/groups";
		}
		
		
		return "redirect:/groups";
	}
	
	@RequestMapping(value="/groups/users", method=RequestMethod.POST)
	public String addUserToGroup(UserAdditionForm userAdditionForm, RedirectAttributes redirectAttr) {
		
		try {
			groupService.addUserToGroup(userAdditionForm.getUserId(), userAdditionForm.getGroupId());
		} catch (UserAlreadyInGroupException ex) {
			redirectAttr.addFlashAttribute("error", ex.getMessage());
			return "redirect:/groups";
		}
		
		return "redirect:/groups";
	}
	
	
	@RequestMapping(value="/groups/{groupId}", method=RequestMethod.POST)
	public String deleteGroup(@PathVariable("groupId") Integer groupId) {
		
		logger.info("Delete group id " + groupId);
		
		groupService.delete(groupId);
		
		return "redirect:/groups";
	}
	
	@RequestMapping(value="/groups/{groupId}/users/{userId}", method=RequestMethod.POST)
	public String removeUserFromGroup(@PathVariable("groupId") Integer groupId, @PathVariable("userId") Integer userId) {
		
		logger.info("Remove user '{}' from group '{}'", userId, groupId);
		
		groupService.removeUserFromGroup(userId, groupId);
		
		return "redirect:/groups";
	}
	
	
	
}
