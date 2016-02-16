package vn.com.splussoftware.sms.api.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;

import vn.com.splussoftware.sms.model.entity.TicketEntity;
import vn.com.splussoftware.sms.utils.service.DraftingServiceUtils;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataObject;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Individual;
import vn.com.splussoftware.sms.utils.service.jsonhandler.IndividualData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Matrix;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixValue;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Table;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableValue;


@RestController
public class DraftService {
	//TuanHMA save draft for individuals
	@Autowired
	DraftingServiceUtils draftDb;

	@RequestMapping(value = "/draft", method = RequestMethod.POST)
	public String DraftIndividualMapping(HttpServletResponse response,
									@CookieValue(value = "draftid", defaultValue = "0") int draftid,
									@RequestParam(value = "value") String value,
									@RequestParam(value = "id") int id
    								) {	
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session =  attr.getRequest().getSession(true); 
	    DataObject dataObj = (DataObject) session.getAttribute("CONTAINER");
	    if	(dataObj == null) {
	    	dataObj = new DataObject();
	    }
	    Individual iObj = (Individual) (dataObj.find(id));
	    if	(iObj == null){
	    	iObj = new Individual();	    
	    	iObj.setData(new IndividualData());
	    	iObj.setId(id);
	    	((IndividualData)iObj.getData()).setValue(value);
	    	dataObj.getElements().add(iObj);
	    	session.setAttribute("CONTAINER", dataObj);
	    } else {
	    	((IndividualData)iObj.getData()).setValue(value);	    	
	    	dataObj.change(iObj);
	    	session.setAttribute("CONTAINER", dataObj);	    	
	    }
	    Gson gson = new Gson();
	    String jsonStr = gson.toJson(dataObj);
	    int draftCookieId = draftDb.saveJsonDB(draftid, jsonStr);
	    if	(draftid == 0) {
	    	response.addCookie(new Cookie("draftid",draftCookieId+""));
	    }
		return jsonStr;
	}
	//TuanHMA save draft for matrix
	@RequestMapping(value = "/draftMatrix", method = RequestMethod.POST)
	public String SMSDraftMatrixMapping(HttpServletResponse response,
									@CookieValue(value = "draftid", defaultValue = "0") int draftid,
									@RequestParam(value = "value") String value,
									@RequestParam(value = "x") int x,
									@RequestParam(value = "y") int y,
									@RequestParam(value = "id") int id
    								) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session =  attr.getRequest().getSession(true); 
	    DataObject dataObj = (DataObject) session.getAttribute("CONTAINER");
	    if	(dataObj == null) {
	    	dataObj = new DataObject();
	    	session.setAttribute("CONTAINER", dataObj);
	    }
	    Matrix eObj = (Matrix) (dataObj.find(id));
	    if	(eObj == null){
	    	eObj = new Matrix();
	    	eObj.setData(new MatrixData());
	    	eObj.setId(id);
	    	eObj.setDataType("matrix");
	    	MatrixValue mValue = new MatrixValue();
	    	mValue.setColumnId(x);
	    	mValue.setRowId(y);
	    	mValue.setValue(value);
	    	((MatrixData)eObj.getData()).getValues().add(mValue);
	    	dataObj.getElements().add(eObj);
	    	session.setAttribute("CONTAINER", dataObj);
	    } else {
	    	MatrixValue mValue = new MatrixValue();
	    	mValue.setColumnId(x);
	    	mValue.setRowId(y);
	    	mValue.setValue(value);
	    	((MatrixData)eObj.getData()).change(mValue);
	    	dataObj.change(eObj);
	    	session.setAttribute("CONTAINER", dataObj);   	
	    }
	    Gson gson = new Gson();
	    String jsonStr = gson.toJson(dataObj);
	    int draftCookieId = draftDb.saveJsonDB(draftid, jsonStr);
	    if	(draftid == 0) {
	    	response.addCookie(new Cookie("draftid",draftCookieId+""));
	    }
		return jsonStr;
	}
	//TuanHMA save draft for Table
	@RequestMapping(value = "/draftTable", method = RequestMethod.POST)
	public String SMSDraftTableMapping(HttpServletResponse response,
									@CookieValue(value = "draftid", defaultValue = "0") int draftid,
									@RequestParam(value = "value") String value,
									@RequestParam(value = "x") int x,
									@RequestParam(value = "y") int y,
									@RequestParam(value = "id") int id
    								) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session =  attr.getRequest().getSession(true); 
	    DataObject dataObj = (DataObject) session.getAttribute("CONTAINER");
	    if	(dataObj == null) {
	    	dataObj = new DataObject();	
	    	session.setAttribute("CONTAINER", dataObj);
	    }
	    Table eObj = (Table) (dataObj.find(id));
	    if	(eObj == null){
	    	eObj = new Table();
	    	eObj.setData(new TableData());
	    	eObj.setId(id);
	    	eObj.setDataType("table");
	    	TableValue mValue = new TableValue();
	    	mValue.setColumnId(x);
	    	mValue.setRow(y);
	    	mValue.setValue(value);
	    	((TableData)eObj.getData()).getValues().add(mValue);
	    	dataObj.getElements().add(eObj);
	    	session.setAttribute("CONTAINER", dataObj);
	    } else {
	    	TableValue mValue = new TableValue();
	    	mValue.setColumnId(x);
	    	mValue.setRow(y);
	    	mValue.setValue(value);
	    	((TableData)eObj.getData()).change(mValue);
	    	dataObj.change(eObj);
	    	session.setAttribute("CONTAINER", dataObj);   	
	    }
	    Gson gson = new Gson();
	    String jsonStr = gson.toJson(dataObj);
	    int draftCookieId = draftDb.saveJsonDB(draftid, jsonStr);
	    if	(draftid == 0) {
//ThanhND 27/01/2016 10:22 - Should check if draftid exists in database
//TuanHMA 27/01/2016 13:53 - If the draftid existed in database, it will update the content of the draft.
	    	response.addCookie(new Cookie("draftid",draftCookieId+""));
	    }
		return jsonStr;
	}
	//TuanHMA delete cookie - remove draft from db
//ThanhND 27/01/2016 10:22 - Comment should have time.
	@RequestMapping(value = "/submit")
	public void clearDraft(HttpServletResponse response,
			@CookieValue(value = "draftid", defaultValue = "0") int draftid){
		if	(draftid == 0) return;
		draftDb.remove(draftid);
//ThanhND 27/01/2016 10:22 - Must check if draftid !=0 but don't exist in database.
//TuanHMA 27/01/2016 13:53 - Will let the DraftServiceUtils do the job to check if the id exist in database.
		Cookie deadCookie = new Cookie("draftid",null);
		deadCookie.setMaxAge(0);
		response.addCookie(deadCookie);
	}
//ThanhND 27/01/2016 10:22 - Test function should be delete!
}
