package vn.com.splussoftware.sms.api.service;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.com.splussoftware.sms.model.entity.HistoryEntity;
import vn.com.splussoftware.sms.model.entity.PhaseEntity;
import vn.com.splussoftware.sms.model.entity.RelationshipEntity;
import vn.com.splussoftware.sms.model.entity.TicketEntity;
import vn.com.splussoftware.sms.model.entity.TicketInfoEntity;
import vn.com.splussoftware.sms.utils.service.DraftingServiceUtils;
import vn.com.splussoftware.sms.utils.service.HistoryService;
import vn.com.splussoftware.sms.utils.service.TicketService;
import vn.com.splussoftware.sms.utils.service.TicketUtilService;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataObject;
import vn.com.splussoftware.sms.utils.service.jsonhandler.ElementData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Individual;
import vn.com.splussoftware.sms.utils.service.jsonhandler.IndividualData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Matrix;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixValue;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Relationship;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Relative;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Table;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableValue;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Workflow;

@RestController
public class TicketApiService {
	@Autowired
	TicketService tService;
	
	@Autowired
	TicketUtilService tUtilService;
	
	@Autowired
	DraftingServiceUtils draftDb;
	
	@Autowired
	HistoryService hService;
	
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
	//TuanHMA delete cookie - remove draft from db. Save ticket data to db
	//ThanhND 27/01/2016 10:22 - Comment should have time.
		@RequestMapping(value = "/submit")
		public long submitTicket(HttpServletResponse response,
				@CookieValue(value = "draftid", defaultValue = "0") int draftid){
			if	(draftid == 0) return 0;
			Gson gson = new Gson();
			TicketEntity ticket = new TicketEntity();
			ticket.setCreatedTime(new Date());
			ticket.setInput(draftDb.getJson(draftid));
			tService.createTicket(ticket);
			long ticketid = ticket.getID();
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		    HttpSession session =  attr.getRequest().getSession(true); 
			DataObject dataObj = (DataObject) session.getAttribute("CONTAINER");
			
			//Save data to ticketinfo
			tUtilService.saveTicketInfo(dataObj, ticketid, -1, gson);
			
			String workflowString = "{\"relatives\":[{\"id\":-30,\"realid\":30,\"autoCreate\":true,\"type\":\"phase\",\"summary\":\"Asset Verify\",\"owner\":\"\",\"fromInput\":true,\"toOutput\":false,\"status\":\"Responding\",\"isDone\":false,\"phaseType\":\"Implement\",\"roles\":{\"assigner\":[\"Support.AD.HCM\",\"HaiNP3\",\"HieuTT\"],\"implementer\":[\"Support.AD.HCM\",\"HaiNP3\",\"DienNTP\"]},\"display\":{\"image\":\"http://ssc.fsoft.com.vn/plugins/servlet/sms-attachment/136/Verify%20Asset.png\",\"positions\":[200,15]}},{\"id\":-31,\"realid\":31,\"autoCreate\":true,\"type\":\"phase\",\"summary\":\"Collect Asset\",\"owner\":\"\",\"fromInput\":false,\"toOutput\":false,\"status\":\"Waiting\",\"isDone\":false,\"phaseType\":\"Implement\",\"roles\":{\"assigner\":[\"Support.IT.HCM\",\"phongit.hcm\",\"HuyND3\",\"NgaNT9\",\"ThaoBTT\",\"HieuTT\"],\"implementer\":[\"Support.IT.HCM\",\"phongit.hcm\",\"HuyND3\",\"NgaNT9\",\"ThaoBTT\",\"CuHV\",\"HieuTT\"]}},{\"id\":-32,\"realid\":32,\"autoCreate\":true,\"type\":\"phase\",\"summary\":\"Update Asset\",\"owner\":\"\",\"fromInput\":false,\"toOutput\":true,\"status\":\"Waiting\",\"isDone\":false,\"phaseType\":\"Implement\",\"roles\":{\"assigner\":[\"Support.AD.HCM\",\"HaiNP3\",\"HieuTT\"],\"implementer\":[\"DienNTP\",\"ThaoNT15\",\"PhongNT13\",\"Support.AD.HCM\",\"HaiNP3\"]}},{\"id\":-33,\"realid\":33,\"autoCreate\":true,\"type\":\"phase\",\"summary\":\"Store Asset\",\"owner\":\"\",\"fromInput\":false,\"toOutput\":true,\"status\":\"Waiting\",\"isDone\":false,\"phaseType\":\"Implement\",\"roles\":{\"assigner\":[\"Support.AD.HCM\",\"HaiNP3\",\"HieuTT\"],\"implementer\":[\"HuyP2\",\"PhongNT13\",\"DienNTP\",\"Support.AD.HCM\",\"HaiNP3\"]}}],\"relationships\":[{\"id\":0,\"from\":-30,\"to\":-31,\"realid\":19,\"fromreal\":30,\"toreal\":31,\"autoCreate\":true,\"status\":\"waiting\",\"type\":\"default\"},{\"id\":0,\"from\":-31,\"to\":-32,\"realid\":20,\"fromreal\":31,\"toreal\":32,\"autoCreate\":true,\"status\":\"waiting\",\"detail\":{\"elements\":[]},\"type\":\"default\"},{\"id\":0,\"from\":-31,\"to\":-33,\"realid\":21,\"fromreal\":31,\"toreal\":33,\"autoCreate\":true,\"status\":\"waiting\",\"detail\":{\"elements\":[]},\"type\":\"default\"}]}";
			Workflow workflow = gson.fromJson(workflowString, Workflow.class);
			System.out.println(workflow.getRelatives().length);
			HashMap<Long,Long> realidMap = new HashMap<Long,Long>();
			
			//TuanHMA 18/01/2016 Save relative and relationship to DB;
			for (Relative relative : workflow.getRelatives()){
				if (!relative.isAutoCreate()) continue;
				PhaseEntity phaseEntity = new PhaseEntity();
				phaseEntity.setTicketID(ticketid);
				phaseEntity.setDisplayAttribute(gson.toJson(relative.getDisplay()));
				phaseEntity.setStatus(relative.getStatus());
				phaseEntity.setType(relative.getPhaseType());
				tService.createPhase(phaseEntity);
				realidMap.put(relative.getId(), phaseEntity.getID());
				relative.setRealid(phaseEntity.getID());
			}		
			for (Relationship relationship : workflow.getRelationships()){
				if (!relationship.isAutoCreate()) continue;
				RelationshipEntity relationshipEntity = new RelationshipEntity();
				relationshipEntity.setTicketID(ticketid);
				relationshipEntity.setType(relationship.getType());
				relationshipEntity.setFromPhaseID(realidMap.get(relationship.getFrom()));
				relationshipEntity.setToPhaseID(realidMap.get(relationship.getTo()));
				relationshipEntity.setStatus(relationship.getStatus());
				relationshipEntity.setInformation(gson.toJson(relationship.getDetail()));
				tService.createRelationship(relationshipEntity);
				relationship.setRealid(relationshipEntity.getID());
				relationship.setFromreal(realidMap.get(relationship.getFrom()));
				relationship.setToreal(realidMap.get(relationship.getTo()));
				tUtilService.saveTicketInfo(relationship.getDetail(), ticketid, relationship.getId(), gson);
			}
			//TuanHMA 18/01/2016 Update workflow string back to tickettbl
			ticket.setWorkflow(gson.toJson(workflow));
			tService.updateTicket(ticket);
			
			//TuanHMA 18/01/2016 Remove draft's data in draft table
			draftDb.remove(draftid);
	//ThanhND 27/01/2016 10:22 - Must check if draftid !=0 but don't exist in database.
	//TuanHMA 27/01/2016 13:53 - Will let the DraftServiceUtils do the job to check if the id exist in database.
			Cookie deadCookie = new Cookie("draftid",null);
			deadCookie.setMaxAge(0);
			response.addCookie(deadCookie);	
			HistoryEntity hEntity = new HistoryEntity();
			hEntity.setActor("admin");
			hEntity.setAuthor("admin");
			hEntity.setPostcontent(gson.toJson(ticket));
			hService.createHistory(hEntity);
			return ticket.getID();
		}
}
