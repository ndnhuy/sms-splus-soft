package vn.com.splussoftware.sms.ui.controller;
/**
 * Kietlt 12:15PM 2016/1/27
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * use to get url and mapping to view file .html
 * 
 * @author KietLT 3:30 PM 2016/1/27
 *
 */
@Controller
@RequestMapping("/sms")
public class ServicesViewController {
	@RequestMapping("/services/pageAdd")
	public String pageAdd() {
		return "/services/testAdd";
	}
	@RequestMapping("/services/pageDelete")
	public String pageDelete() {
		return "/services/testDelete";
	}
	@RequestMapping("/services/pageUpdate")
	public String pageUpdate() {
		return "/services/testUpdate";
	}
	@RequestMapping("/services/pageList")
	public String index() {
		return "/services/testList";
	}
	
}
