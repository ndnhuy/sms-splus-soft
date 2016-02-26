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
public class ProviderViewController {

	@RequestMapping("/provider/pageAdd")
	public String pageAdd() {
		return "/provider/testAdd";
	}
	@RequestMapping("/provider/pageDelete")
	public String pageDelete() {
		return "/provider/testDelete";
	}
	@RequestMapping("/provider/pageUpdate")
	public String pageUpdate() {
		return "/provider/testUpdate";
	}
	@RequestMapping("/provider/pageList")
	public String index() {
		return "/provider/testList";
	}
	


}
