package vn.com.splussoftware.sms.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionDto;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionUIDto;
import vn.com.splussoftware.sms.utils.dto.PermissionDto;
import vn.com.splussoftware.sms.utils.service.GlobalPermissionService;
import vn.com.splussoftware.sms.utils.service.GroupService;
import vn.com.splussoftware.sms.utils.service.PermissionService;
import vn.com.splussoftware.sms.utils.service.UserService;

//TODO insert '/secure' in request mapping
@Controller
public class PermissionGrantingController {
	private static final Logger logger = LoggerFactory.getLogger(PermissionGrantingController.class);
	
	
	@Autowired
	private GlobalPermissionService globalPermissionService;
		
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value="/permissions", method=RequestMethod.GET)
	public String showPermissionGrantingPage(Model model, PermissionDto permissionDto, GlobalPermissionDto globalPermissionDto) {
		
		/*
		 * Show permissions on page
		 */
		PermissionForm permissionForm = new PermissionForm();
		permissionForm.setGlobalPermissions(globalPermissionService.getListOfUsersWithGlobalPermission());
		
		
		List<PermissionDto> dtoPermissions = permissionService.findAll();
		
		// Any targetType or permission is 'null', change it to "" 
		// to make the showing on thymeleaf easier.
		dtoPermissions.forEach(p->{
			if (p.getTargetType() == null) {
				p.setTargetType("");
			}
			if (p.getPermission() == null) {
				p.setPermission("");
			}
		});
		permissionForm.setDtoPermissions(dtoPermissions);
		
		model.addAttribute("permissionForm", permissionForm);
		
		/*
		 * Show info for adding permission
		 */
		model.addAttribute("users", userService.findAll());
		model.addAttribute("groups", groupService.findAll());
		
		/*
		 * Show info for granting global permission
		 */
		model.addAttribute("users", userService.findAll());
		
		return "permission-granting";
	}
	
	
	
	
	
	@RequestMapping(value="/permissions", method=RequestMethod.POST)
	public String update(@ModelAttribute PermissionForm permissionForm) {
		// Convert PermissionForm#GlobalPermissionUIDto to GlobalPermissionDto-s
		// Update globalPermission
		for (GlobalPermissionUIDto uiDto : permissionForm.getGlobalPermissions()) {
			GlobalPermissionDto dto = new GlobalPermissionDto();
			dto.setId(uiDto.getId());
			dto.setUserId(uiDto.getUserId());
			dto.setPermission(uiDto.getPermission());
			
			globalPermissionService.save(dto);
		}
		
		// Update user permission
		for(PermissionDto p : permissionForm.getDtoPermissions()) {			
			permissionService.save(p);
		}
		
		return "redirect:/permissions/";
	}
	
	@RequestMapping(value="/permissions/delete/{permissionId}", method=RequestMethod.GET)
	public String deletePermission(@PathVariable("permissionId") Integer permissionId) {
		
		logger.info("Delete permission id " + permissionId);
		
		permissionService.delete(permissionId);
			
		return "redirect:/permissions/";
	}
	
	@RequestMapping(value="/permissions/add", method=RequestMethod.POST)
	public String addPermission(@Valid PermissionDto permissionDto, BindingResult bindingResult) {
		
		System.out.println(ToStringBuilder.reflectionToString(permissionDto));
		
		if (permissionDto.getTargetType().equals("")) {
			permissionDto.setTargetType(null);
		}
		
		permissionService.save(permissionDto);
		
		return "redirect:/permissions/";
	}
	
	@RequestMapping(value="/permissions/globalPermissions", method=RequestMethod.POST)
	public String grantGlobalPermission(@Valid GlobalPermissionDto globalPermissionDto) {
		logger.info("Grant global permission '{}' for user '{}'", globalPermissionDto.getPermission(), globalPermissionDto.getUserId());
		
		globalPermissionService.save(globalPermissionDto);
		
		return "redirect:/permissions";
	}
	
	@RequestMapping(value="/permissions/globalPermissions/delete/{id}", method=RequestMethod.GET)
	public String grantGlobalPermission(@PathVariable("id") Integer id) {
		
		globalPermissionService.delete(id);
		
		return "redirect:/permissions";
	}
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PermissionForm {
		private List<GlobalPermissionUIDto> globalPermissions = new ArrayList<GlobalPermissionUIDto>();
		private List<PermissionDto> dtoPermissions = new ArrayList<PermissionDto>();
		
	}
}
