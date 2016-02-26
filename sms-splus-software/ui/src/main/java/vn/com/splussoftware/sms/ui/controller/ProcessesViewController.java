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
public class ProcessesViewController {

	@RequestMapping("/processes/pageAdd")
	public String pageAdd() {
		return "/processes/testAdd";
	}
	@RequestMapping("/processes/pageDelete")
	public String pageDelete() {
		return "/processes/testDelete";
	}
	@RequestMapping("/processes/pageUpdate")
	public String pageUpdate() {
		return "/processes/testUpdate";
	}
	@RequestMapping("/processes/pageList")
	public String index() {
		return "/processes/testList";
	}
	


}
