package vn.com.splussoftware.sms.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.com.splussoftware.sms.model.entity.PhaseEntity;
import vn.com.splussoftware.sms.model.entity.RelationshipEntity;
import vn.com.splussoftware.sms.model.entity.TicketEntity;
import vn.com.splussoftware.sms.utils.service.DraftingServiceUtils;
import vn.com.splussoftware.sms.utils.service.TicketService;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataAdapter;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataObject;
import vn.com.splussoftware.sms.utils.service.jsonhandler.ElementData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Matrix;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Table;

@Controller
public class WebController {
	@Autowired
	DraftingServiceUtils draftDb;
	
	@Autowired
	TicketService tService;
	//TuanHMA 1-29-2016 change ticket for admin
	@RequestMapping(value = "/ticket2", method = RequestMethod.GET)
    public String ticket() {
        return "ticketStatus";
    }
	
	@RequestMapping(value = "/ticketView", method = RequestMethod.GET)
    public String ticketView(Model model,@RequestParam(value = "id") long ticketid) {

    	
    	GsonBuilder gsonBilder = new GsonBuilder();
		
    	gsonBilder.registerTypeAdapter(ElementData.class, new DataAdapter());
		
		Gson gson = gsonBilder.create();
		
		TicketEntity ticket = tService.getTicketById(ticketid);
		
		if (ticket == null) return "404";
		
		//TuanHMA 1-29-2016 Object held infomation of json
		DataObject dataObjInp = gson.fromJson(ticket.getInput(), DataObject.class);
		
		DataObject dataObjOut = gson.fromJson(ticket.getOutput(), DataObject.class);
		
		
		
		List<ElementData> elementsOut = null;
		
		if (dataObjOut !=null) {
			elementsOut = dataObjOut.getElements();
			Collections.sort(elementsOut);
			for (ElementData eData : elementsOut){
				if (eData.getDataType().equals("table")){
					((Table)eData).getData().fillData();
				} else if (eData.getDataType().equals("matrix")){
					((Matrix)eData).getData().fillData();
				}			
			}
		}
		
		List<ElementData> elementsInp =  dataObjInp.getElements();	
			
		Collections.sort(elementsInp);
		//TuanHMA 1-29-2016 17:30 Fill data value so that every field have data value object
		for (ElementData eData : elementsInp){
			if (eData.getDataType().equals("table")){
				((Table)eData).getData().fillData();
			} else if (eData.getDataType().equals("matrix")){
				((Matrix)eData).getData().fillData();
			}			
		}
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session =  attr.getRequest().getSession(true); 
	    session.setAttribute("CONTAINER", dataObjInp);
		model.addAttribute("ELEMENTSINP", elementsInp);
		model.addAttribute("ELEMENTSOUT", elementsOut);
		model.addAttribute("ticket",ticket); 
        return "ticketView";
    }
	
	@RequestMapping(value = "/phaseView", method = RequestMethod.GET)
    public String phaseView(Model model,@RequestParam(value = "id") long phaseid) {

    	
    	GsonBuilder gsonBilder = new GsonBuilder();
		
    	gsonBilder.registerTypeAdapter(ElementData.class, new DataAdapter());
		
		Gson gson = gsonBilder.create();
		
		PhaseEntity phase = tService.getPhaseById(phaseid);
		
		if (phase == null) return "404";
		
		List<RelationshipEntity> relationPrev = tService.getRelationPrev(phaseid);
		List<RelationshipEntity> relationForw = tService.getRelationForw(phaseid);
		
		List<List<ElementData>> inps = new ArrayList<List<ElementData>>();
		for (RelationshipEntity rel : relationPrev){
			DataObject dataObject = gson.fromJson(rel.getInformation(), DataObject.class);
			List<ElementData> elements = null;
			
			if (dataObject !=null) {
				elements = dataObject.getElements();
				Collections.sort(elements);
				for (ElementData eData : elements){
					if (eData.getDataType().equals("table")){
						((Table)eData).getData().fillData();
					} else if (eData.getDataType().equals("matrix")){
						((Matrix)eData).getData().fillData();
					}			
				}
				inps.add(elements);
			}
		}
		
		List<List<ElementData>> outs = new ArrayList<List<ElementData>>();
		for (RelationshipEntity rel : relationForw){
			DataObject dataObject = gson.fromJson(rel.getInformation(), DataObject.class);
			List<ElementData> elements = null;
			
			if (dataObject !=null) {
				elements = dataObject.getElements();
				Collections.sort(elements);
				for (ElementData eData : elements){
					if (eData.getDataType().equals("table")){
						((Table)eData).getData().fillData();
					} else if (eData.getDataType().equals("matrix")){
						((Matrix)eData).getData().fillData();
					}			
				}
				outs.add(elements);
			}
		}
	    model.addAttribute("phase", phase);
		model.addAttribute("INPS", inps);
		model.addAttribute("OUTS", outs);
		//model.addAttribute("ticket",ticket); 
        return "phaseView";
    }
	//TuanHMA 1-29-2016 create ticket for end-user 
	  @RequestMapping("/createTicket")
	    public String createTicket(Model model,
	    		@CookieValue(value = "draftid", defaultValue = "0") int draftid) {
			//String jsonStr = "{\"elements\":[{\"dataType\":\"individual\",\"name\":\"Link to Employees Picture Folder\",\"id\":-1,\"position\":1,\"data\":{\"value\":\"Female\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]}}},{\"dataType\":\"table\",\"name\":\"Table Information Of Ticket\",\"id\":-2,\"position\":2,\"data\":{\"columns\":[{\"name\":\"No.\",\"type\":\"number\",\"id\":-1,\"position\":1,\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Fullname\",\"type\":\"text\",\"position\":2,\"id\":-2,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255}},{\"name\":\"Gender\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"position\":3,\"id\":-3},{\"name\":\"Parent Department\",\"position\":4,\"id\":-4,\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Child Department\",\"position\":5,\"id\":-5,\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Job\",\"position\":6,\"id\":-6,\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Contract Type\",\"type\":\"select\",\"id\":-7,\"position\":7,\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Probation Contract\",\"value\":\"Probation Contract\"},{\"text\":\"Labor contract follow a certain job to have duration less than 12 months\",\"value\":\"Labor contract follow a certain job to have duration less than 12 months\"},{\"text\":\"Definite-term Labor contract\",\"value\":\"Definite-term Labor contract\"},{\"text\":\"Indefinite-term Labor contract\",\"value\":\"Indefinite-term Labor contract\"},{\"text\":\"Seasonal Contract\",\"value\":\"Seasonal Contract\"},{\"text\":\"Fresher Training Contract\",\"value\":\"Fresher Training Contract\"},{\"text\":\"Intern Training Contract\",\"value\":\"Intern Training Contract\"},{\"text\":\"Service Contract\",\"value\":\"Service Contract\"},{\"text\":\"Vender Contract\",\"value\":\"Vender Contract\"},{\"text\":\"Internship\",\"value\":\"Internship\"},{\"text\":\"Customer\",\"value\":\"Customer\"},{\"text\":\"Other\",\"value\":\"Other\"}],\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Start date\",\"type\":\"date\",\"position\":8,\"id\":-8,\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"End date\",\"type\":\"date\",\"position\":9,\"id\":-9,\"conditions\":{\"required\":false,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"Account\",\"type\":\"text\",\"position\":10,\"id\":-10,\"conditions\":{}},{\"name\":\"Add To Group\",\"type\":\"text\",\"position\":11,\"id\":-11,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":1000}},{\"name\":\"Note\",\"type\":\"textarea\",\"position\":12,\"id\":-12,\"conditions\":{\"max-length\":1000}}],\"values\":[{\"columnId\":-1,\"row\":1,\"value\":\"\"}]}},{\"dataType\":\"matrix\",\"name\":\"Matrix Information Of Ticket\",\"id\":-3,\"position\":3,\"data\":{\"columns\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2}],\"rows\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2}],\"values\":[{\"columnId\":-1,\"rowId\":-1,\"value\":\"\",\"condition\":{\"required\":true,\"min-length\":5,\"max-length\":255},\"type\":\"text\"},{\"columnId\":-1,\"rowId\":-2,\"value\":\"\",\"conditions\":{},\"type\":\"date\"}]}}    ]}";
	    	String jsonStr = "{\"elements\":[{\"dataType\":\"individual\",\"name\":\"Link to Employees Picture Folder\",\"id\":-1,\"position\":1,\"data\":{\"value\":\"mimimi\",\"type\":\"textarea\",\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":1000}}},{\"dataType\":\"table\",\"name\":\"Table Information Of Ticket\",\"id\":-2,\"position\":2,\"data\":{\"columns\":[{\"name\":\"No.\",\"type\":\"number\",\"id\":-1,\"position\":1,\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Fullname\",\"type\":\"text\",\"position\":2,\"id\":-2,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255}},{\"name\":\"Gender\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"position\":3,\"id\":-3},{\"name\":\"Parent Department\",\"position\":4,\"id\":-4,\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Child Department\",\"position\":5,\"id\":-5,\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Job\",\"position\":6,\"id\":-6,\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Contract Type\",\"type\":\"select\",\"id\":-7,\"position\":7,\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Probation Contract\",\"value\":\"Probation Contract\"},{\"text\":\"Labor contract follow a certain job to have duration less than 12 months\",\"value\":\"Labor contract follow a certain job to have duration less than 12 months\"},{\"text\":\"Definite-term Labor contract\",\"value\":\"Definite-term Labor contract\"},{\"text\":\"Indefinite-term Labor contract\",\"value\":\"Indefinite-term Labor contract\"},{\"text\":\"Seasonal Contract\",\"value\":\"Seasonal Contract\"},{\"text\":\"Fresher Training Contract\",\"value\":\"Fresher Training Contract\"},{\"text\":\"Intern Training Contract\",\"value\":\"Intern Training Contract\"},{\"text\":\"Service Contract\",\"value\":\"Service Contract\"},{\"text\":\"Vender Contract\",\"value\":\"Vender Contract\"},{\"text\":\"Internship\",\"value\":\"Internship\"},{\"text\":\"Customer\",\"value\":\"Customer\"},{\"text\":\"Other\",\"value\":\"Other\"}],\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Start date\",\"type\":\"date\",\"position\":8,\"id\":-8,\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"End date\",\"type\":\"date\",\"position\":9,\"id\":-9,\"conditions\":{\"required\":false,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"Account\",\"type\":\"text\",\"position\":10,\"id\":-10,\"conditions\":{}},{\"name\":\"Add To Group\",\"type\":\"text\",\"position\":11,\"id\":-11,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":1000}},{\"name\":\"Note\",\"type\":\"textarea\",\"position\":12,\"id\":-12,\"conditions\":{\"max-length\":1000}}],\"values\":[{\"columnId\":-1,\"row\":1,\"value\":\"\"}]}},{\"dataType\":\"matrix\",\"name\":\"Matrix Information Of Ticket\",\"id\":-3,\"position\":3,\"data\":{\"columns\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2}],\"rows\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2}],\"values\":[{\"columnId\":-1,\"rowId\":-1,\"value\":\"\",\"condition\":{\"required\":true,\"min-length\":5,\"max-length\":255},\"type\":\"text\"},{\"columnId\":-1,\"rowId\":-2,\"value\":\"\",\"conditions\":{},\"type\":\"date\"}]}}]}";
			System.out.println(draftid);
			if (draftid!=0) {
				jsonStr = draftDb.getJson(draftid);
			}
	    	
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
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		    HttpSession session =  attr.getRequest().getSession(true); 
		    session.setAttribute("CONTAINER", dataObj);
			model.addAttribute("ELEMENTS", elements);
	        return "jSonForm";
	    }
	

}
