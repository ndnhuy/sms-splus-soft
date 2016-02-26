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
public class CustomerViewController {

	@RequestMapping("/customer/pageAdd")
	public String pageAdd() {
		return "/customer/testAdd";
	}
	@RequestMapping("/customer/pageDelete")
	public String pageDelete() {
		return "/customer/testDelete";
	}
	@RequestMapping("/customer/pageUpdate")
	public String pageUpdate() {
		return "/customer/testUpdate";
	}
	@RequestMapping("/customer/pageList")
	public String index() {
		return "/customer/testList";
	}
	


}
