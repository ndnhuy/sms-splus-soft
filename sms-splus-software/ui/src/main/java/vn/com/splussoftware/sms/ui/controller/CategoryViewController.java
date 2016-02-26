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
public class CategoryViewController {

	@RequestMapping("/category/pageAdd")
	public String pageAdd() {
		return "/category/testAdd";
	}
	@RequestMapping("/category/pageDelete")
	public String pageDelete() {
		return "/category/testDelete";
	}
	@RequestMapping("/category/pageUpdate")
	public String pageUpdate() {
		return "/category/testUpdate";
	}
	@RequestMapping("/category/pageList")
	public String index() {
		return "/category/testList";
	}
	


}
