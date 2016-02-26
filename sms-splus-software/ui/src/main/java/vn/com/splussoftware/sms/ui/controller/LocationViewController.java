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
public class LocationViewController {

	@RequestMapping("/location/pageAdd")
	public String pageAdd() {
		return "/location/testAdd";
	}
	@RequestMapping("/location/pageDelete")
	public String pageDelete() {
		return "/location/testDelete";
	}
	@RequestMapping("/location/pageUpdate")
	public String pageUpdate() {
		return "/location/testUpdate";
	}
	@RequestMapping("/location/pageList")
	public String index() {
		return "/location/testList";
	}
	


}
