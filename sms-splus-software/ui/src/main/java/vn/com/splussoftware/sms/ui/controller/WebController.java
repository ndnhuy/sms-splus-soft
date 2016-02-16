package vn.com.splussoftware.sms.ui.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.com.splussoftware.sms.utils.service.jsonhandler.DataAdapter;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataObject;
import vn.com.splussoftware.sms.utils.service.jsonhandler.ElementData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Matrix;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Table;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableData;

@Controller
public class WebController {
	//TuanHMA 1-29-2016 change ticket for admin
	@RequestMapping(value = "/ticket2", method = RequestMethod.GET)
    public String ticket() {
        return "Ticket";
    }
	//TuanHMA 1-29-2016 create ticket for end-user 
	  @RequestMapping("/createTicket")
	    public String createTicket(Model model) {
			String jsonStr = "{\"elements\":[{\"dataType\":\"individual\",\"name\":\"Link to Employees Picture Folder\",\"id\":-1,\"position\":1,\"data\":{\"value\":\"\",\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":1000}}},{\"dataType\":\"table\",\"name\":\"Table Information Of Ticket\",\"id\":-2,\"position\":2,\"data\":{\"columns\":[{\"name\":\"No.\",\"type\":\"number\",\"id\":-1,\"position\":1,\"conditions\":{\"min\":1,\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Fullname\",\"type\":\"text\",\"position\":2,\"id\":-2,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255}},{\"name\":\"Gender\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"position\":3,\"id\":-3},{\"name\":\"Parent Department\",\"position\":4,\"id\":-4,\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Child Department\",\"position\":5,\"id\":-5,\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Job\",\"position\":6,\"id\":-6,\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Contract Type\",\"type\":\"select\",\"id\":-7,\"position\":7,\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Probation Contract\",\"value\":\"Probation Contract\"},{\"text\":\"Labor contract follow a certain job to have duration less than 12 months\",\"value\":\"Labor contract follow a certain job to have duration less than 12 months\"},{\"text\":\"Definite-term Labor contract\",\"value\":\"Definite-term Labor contract\"},{\"text\":\"Indefinite-term Labor contract\",\"value\":\"Indefinite-term Labor contract\"},{\"text\":\"Seasonal Contract\",\"value\":\"Seasonal Contract\"},{\"text\":\"Fresher Training Contract\",\"value\":\"Fresher Training Contract\"},{\"text\":\"Intern Training Contract\",\"value\":\"Intern Training Contract\"},{\"text\":\"Service Contract\",\"value\":\"Service Contract\"},{\"text\":\"Vender Contract\",\"value\":\"Vender Contract\"},{\"text\":\"Internship\",\"value\":\"Internship\"},{\"text\":\"Customer\",\"value\":\"Customer\"},{\"text\":\"Other\",\"value\":\"Other\"}],\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Start date\",\"type\":\"date\",\"position\":8,\"id\":-8,\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"End date\",\"type\":\"date\",\"position\":9,\"id\":-9,\"conditions\":{\"required\":false,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"Account\",\"type\":\"text\",\"position\":10,\"id\":-10,\"conditions\":{}},{\"name\":\"Add To Group\",\"type\":\"text\",\"position\":11,\"id\":-11,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":1000}},{\"name\":\"Note\",\"type\":\"textarea\",\"position\":12,\"id\":-12,\"conditions\":{\"max-length\":1000}}],\"values\":[{\"columnId\":-1,\"row\":1,\"value\":\"Hihi\",\"conditions\":{},\"type\":\"text\"}]}},{\"dataType\":\"matrix\",\"name\":\"Matrix Information Of Ticket\",\"id\":-3,\"position\":3,\"data\":{\"columns\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2}],\"rows\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2}],\"values\":[{\"columnId\":-1,\"rowId\":-1,\"value\":\"iHihi\",\"conditions\":{},\"type\":\"text\"},{\"columnId\":-1,\"rowId\":-2,\"value\":\"\",\"conditions\":{},\"type\":\"date\"}]}}],\"notifications\":[{\"id\":-1,\"action\":\"CREATE_TICKET\",\"to\":[{\"type\":\"TICKET_CREATOR\"}],\"template\":\"Default Template For Ticket Creation\"},{\"id\":-2,\"action\":\"CREATE_TICKET\",\"to\":[{\"type\":\"TICKET_WATCHERS\"}],\"template\":\"Default Template For Ticket Creation Send To Watchers\"}]}";
	    	
			GsonBuilder gsonBilder = new GsonBuilder();
			
	    	gsonBilder.registerTypeAdapter(ElementData.class, new DataAdapter());
			
			Gson gson = gsonBilder.create();
			
			//TuanHMA 1-29-2016 Object held infomation of json
			DataObject dataObj = gson.fromJson(jsonStr, DataObject.class);
			
			List<ElementData> elements =  dataObj.getElements();
			Collections.sort(elements);
			//TuanHMA 1-29-2016 17:30 Fill data value so that every field have data value object
			for (ElementData eData : elements){
				if (eData.getDataType().equals("table")){
					((Table)eData).getData().fillData();
				} else if (eData.getDataType().equals("matrix")){
					((Matrix)eData).getData().fillData();
				}
					
			}
			model.addAttribute("ELEMENTS", elements);
	        return "jSonForm";
	    }

}
