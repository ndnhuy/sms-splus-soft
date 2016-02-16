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
	@RequestMapping("/pageAdd")
	public String pageAdd() {
		return "testAdd";
	}
	@RequestMapping("/pageDelete")
	public String pageDelete() {
		return "testDelete";
	}
	@RequestMapping("/pageUpdate")
	public String pageUpdate() {
		return "testUpdate";
	}
	@RequestMapping("/pageList")
	public String index() {
		return "testList";
	}
	
}
