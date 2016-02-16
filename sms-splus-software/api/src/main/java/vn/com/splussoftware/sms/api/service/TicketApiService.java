package vn.com.splussoftware.sms.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.com.splussoftware.sms.model.entity.TicketEntity;
import vn.com.splussoftware.sms.utils.service.TicketService;

@RestController
public class TicketApiService {
	@Autowired
	TicketService tService;
	@RequestMapping(value = "/ticketCreate", method = RequestMethod.POST)
	public String createTicketTbl(@RequestBody(required = true) TicketEntity ticketEntity){
		tService.createTicket(ticketEntity);
		return "done";
	}
	@RequestMapping(value = "/ticketUpdate", method = RequestMethod.POST)
	public String updateTicketTbl(@RequestBody(required = true) TicketEntity ticketEntity){
		tService.updateTicket(ticketEntity);
		return "done";
	}
	
	@RequestMapping(value = "/ticketDelete", method = RequestMethod.POST)
	public String deleteTicketTbl(@RequestBody(required = true) TicketEntity ticketEntity){
		tService.deleteTicketById(ticketEntity.getID());
		return "done";
	}
	
	@RequestMapping(value = "/ticketGet", method = RequestMethod.POST)
	public String getTicketTbl(@RequestBody(required = true) TicketEntity ticketEntity){
		ticketEntity = tService.getTicketById(ticketEntity.getID());
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();
		String json = gson.toJson(ticketEntity);
		return json;
	}
}
