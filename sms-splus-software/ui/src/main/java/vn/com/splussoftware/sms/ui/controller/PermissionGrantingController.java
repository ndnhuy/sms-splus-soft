package vn.com.splussoftware.sms.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionDto;
import vn.com.splussoftware.sms.utils.dto.GlobalPermissionUIDto;
import vn.com.splussoftware.sms.utils.dto.PermissionDto;
import vn.com.splussoftware.sms.utils.service.GlobalPermissionService;
import vn.com.splussoftware.sms.utils.service.PermissionService;

//TODO insert '/secure' in request mapping
@Controller
@RequestMapping(value="/permissions")
public class PermissionGrantingController {
	private static final Logger logger = LoggerFactory.getLogger(PermissionGrantingController.class);
	
	
	@Autowired
	private GlobalPermissionService globalPermissionService;
		
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showPermissionGrantingPage(Model model) {
//		model.addAttribute("globalPermissionForm", new GlobalPermissionForm(globalPermissionService.getListOfUsersWithGlobalPermission()));
//		model.addAttribute("permissions", permissionService.findAll());
		
		PermissionForm permissionForm = new PermissionForm();
		permissionForm.setGlobalPermissions(globalPermissionService.getListOfUsersWithGlobalPermission());
		permissionForm.setDtoPermissions(permissionService.findAll());
		
		model.addAttribute("permissionForm", permissionForm);
		
		//Test
//		model.addAttribute("test", new TestUI());
//		model.addAttribute("loginFormDto", new TestUI());
		
		return "permission-granting";
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String test(@ModelAttribute PermissionForm permissionForm) {
//		for (GlobalPermissionUIDto g : permissionForm.getGlobalPermissions()) {
//			System.out.println(g.getId() + " - " + g.getUserkey() + " - " + g.getPermission());
//		}
//		
//		for (PermissionDto dto : permissionForm.getDtoPermissions()) {
//			System.out.println(dto.getId() + " - " + dto.getUserkey() + " - " + dto.getTargetType() + " - " + dto.getTargetId() + " - " + dto.getPermission() + " - " + dto.getGroupName() + " - ");
//		}
		
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
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PermissionForm {
		private List<GlobalPermissionUIDto> globalPermissions = new ArrayList<GlobalPermissionUIDto>();
		private List<PermissionDto> dtoPermissions = new ArrayList<PermissionDto>();
		
	}
}
