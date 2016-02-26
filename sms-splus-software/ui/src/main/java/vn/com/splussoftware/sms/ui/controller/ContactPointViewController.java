/**
 * 
 */
package vn.com.splussoftware.sms.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * use to get url and mapping to view file .html
 * 
 * @author KietLT 1:23 PM 2016/2/17
 *
 */
@Controller
@RequestMapping ("/sms")
public class ContactPointViewController {

	@RequestMapping("/contact/pageAdd")
	public String pageAdd() {
		return "/contact/testAdd";
	}
	@RequestMapping("/contact/pageDelete")
	public String pageDelete() {
		return "/contact/testDelete";
	}
	@RequestMapping("/contact/pageUpdate")
	public String pageUpdate() {
		return "/contact/testUpdate";
	}
	@RequestMapping("/contact/pageList")
	public String index() {
		return "/contact/testList";
	}
	


}
