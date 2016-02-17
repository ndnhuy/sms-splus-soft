package vn.com.splussoftware.sms.ui.controller;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.com.splussoftware.sms.utils.service.DraftingServiceUtils;
import vn.com.splussoftware.sms.utils.service.jsonhandler.ConditionData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataAdapter;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataObject;
import vn.com.splussoftware.sms.utils.service.jsonhandler.ElementData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Table;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableColumn;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableValue;

@RestController
public class TableRowUtilsController {

	@Autowired
	DraftingServiceUtils draftDb;

	// TuanHMA Feb-1-2016 10:53 Get table row info
	@RequestMapping("/getTableRow")
	public String getTableRow(Model model,
			@RequestParam(value = "row") int row, @RequestParam(value = "id") int id,
			@CookieValue(value = "draftid", defaultValue = "0") int draftid) {
		String jsonStr = "";
		ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attribute.getRequest().getSession(true);
		DataObject dataObj = (DataObject) session.getAttribute("CONTAINER");
		if (draftid != 0 && dataObj == null) {
			jsonStr = draftDb.getJson(draftid);
			GsonBuilder gsonBilder = new GsonBuilder();

			gsonBilder.registerTypeAdapter(ElementData.class, new DataAdapter());

			Gson gson = gsonBilder.create();

			// TuanHMA 1-29-2016 Object held informations of JSON
			dataObj = gson.fromJson(jsonStr, DataObject.class);
		}

		List<ElementData> elements = dataObj.getElements();
		Collections.sort(elements);
		String response = "";

		for (ElementData eData : elements) {
			if (eData.getDataType().equals("table") && id == eData.getId()) {
				response += ("<tr><td><button row='" + row + "' class='btnClone'>Clone</button><button row='" + row
						+ "' class='btnRemove'>Remove</button>");
				response += "<select class='ddlDragRow' id = '"+id+"' row = '"+row+"'>";
				for (int j=1; j<=row; j++){
					String select = j==row?"selected":"";
					response+="<option "+select+">"+j+"</option>";
				}
				response += "</select>";
				response += "</td>";
				Table table = (Table) eData;
				for (TableColumn column : table.getData().getColumns()) {
					response += ("<td>");
					String placeHolder = " placeholder=''";
					String attr = " id ='" + id + "' row='" + row + "' column='" + column.getId() + "' ";
					if (column.getType() == null
							|| (!column.getType().equals("select") && !column.getType().equals("textarea"))) {
						response += ("<input class='value' " + attr + placeHolder + "></input>");
					} else if (column.getType().equals("select")) {
						response += ("<select class='value'" + attr + ">");
						if (!column.getConditions().isRequired())
							response += ("<option value=''>-- Empty Selection --</option>");
						for (ConditionData data : column.getConditions().getData()) {
							response += ("<option value='" + data.getValue() + "'>" + data.getText() + "</option>");
						}
					} else if (column.getType().equals("textarea")) {
						response += ("<textarea " + attr + "></textarea>");
					}
					response += ("</td>");
				}
				response += ("</tr>");
			}
		}
		return response;
	}

	// TuanHMA Feb-1-2016 12:11 Remove a row
	@RequestMapping("/removeRow")
	public String removeRow(Model model, @RequestParam(value = "row") int row, @RequestParam(value = "id") int id,
			@CookieValue(value = "draftid", defaultValue = "0") int draftid) {
		String jsonStr = "";
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		DataObject dataObj = (DataObject) session.getAttribute("CONTAINER");
		if (draftid != 0 && dataObj == null) {
			jsonStr = draftDb.getJson(draftid);
			GsonBuilder gsonBilder = new GsonBuilder();

			gsonBilder.registerTypeAdapter(ElementData.class, new DataAdapter());

			Gson gson = gsonBilder.create();

			// TuanHMA 1-29-2016 Object held informations of JSON
			dataObj = gson.fromJson(jsonStr, DataObject.class);
		}

		List<ElementData> elements = dataObj.getElements();
		Collections.sort(elements);

		for (ElementData eData : elements) {
			if (eData.getDataType().equals("table") && id == eData.getId()) {
				Table table = (Table) eData;
				Iterator<TableValue> valueIter = table.getData().getValues().iterator();
				int i = 0;
				while (valueIter.hasNext()) {
					TableValue value = valueIter.next();
					if (value.getRow() == row) {
						valueIter.remove();
						i--;
					} else if (value.getRow() > row) {
						value.setRow(value.getRow() - 1);
						table.getData().getValues().set(i, value);
					}
					i++;
				}
			}
		}
		dataObj.setElements(elements);
		session.setAttribute("CONTAINER", dataObj);
		if (draftid != 0) {
			draftDb.saveJsonDB(draftid, new Gson().toJson(dataObj, DataObject.class));
		}
		return new Gson().toJson(dataObj, DataObject.class);
	}

	// TuanHMA Feb-1-2016 12:11 Remove a row
	@RequestMapping("/cloneRow")
	public String cloneRow(Model model, @RequestParam(value = "row") int row, @RequestParam(value = "id") int id,
			@CookieValue(value = "draftid", defaultValue = "0") int draftid) {
		String jsonStr = "";
		ServletRequestAttributes attribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attribute.getRequest().getSession(true);
		DataObject dataObj = (DataObject) session.getAttribute("CONTAINER");
		if (draftid != 0 && dataObj == null) {
			jsonStr = draftDb.getJson(draftid);
			GsonBuilder gsonBilder = new GsonBuilder();

			gsonBilder.registerTypeAdapter(ElementData.class, new DataAdapter());

			Gson gson = gsonBilder.create();

			// TuanHMA 1-29-2016 Object held informations of JSON
			dataObj = gson.fromJson(jsonStr, DataObject.class);
		}

		List<ElementData> elements = dataObj.getElements();
		Collections.sort(elements);
		String response = "";
		int oldRow = row;
		row = ((Table)dataObj.find(id)).getData().getMaxRow()+1;
		for (ElementData eData : elements) {
			if (eData.getDataType().equals("table") && id == eData.getId()) {
				response += ("<tr><td><button row='" + row + "' class='btnClone'>Clone</button><button row='" + row
						+ "' class='btnRemove'>Remove</button>");
				response += "<select class='ddlDragRow' id = '"+id+"' row = '"+row+"'>";
				for (int j=1; j<=row; j++){
					String select = j==row?"selected":"";
					response+="<option "+select+">"+j+"</option>";
				}
				response += "</select>";
				response += "</td>";
				Table table = (Table) eData;
				for (TableColumn column : table.getData().getColumns()) {
					TableValue oldValue = table.getData().getValueById(column.getId(), oldRow);
					TableValue newValue = new TableValue();
					newValue.setColumnId(oldValue.getColumnId());
					newValue.setValue(oldValue.getValue());
					newValue.setConditions(oldValue.getConditions());
					newValue.setRow(row);
					table.getData().getValues().add(newValue);
					response += ("<td>");
					String placeHolder = " placeholder=''";
					String attr = " id ='" + id + "' row='" + row + "' column='" + column.getId() + "' ";
					String valueStr = oldValue.getValue()!=null?newValue.getValue():"";
					if (column.getType() == null
							|| (!column.getType().equals("select") && !column.getType().equals("textarea"))) {
						response += ("<input class='value' " + attr + placeHolder + " value='"+valueStr+"'></input>");
					} else if (column.getType().equals("select")) {
						response += ("<select class='value'" + attr + ">");
						if (!column.getConditions().isRequired())
							response += ("<option value=''>-- Empty Selection --</option>");
						for (ConditionData data : column.getConditions().getData()) {
							String selected = (valueStr).equals(data.getValue())?"selected":"";
							response += ("<option " + selected + " value='" + data.getValue() + "'>" + data.getText() + "</option>");						
						}
					} else if (column.getType().equals("textarea")) {
						response += ("<textarea " + attr + ">"+valueStr+"</textarea>");
					}
					response += ("</td>");
					
				}
				response += ("</tr>");
			}
		}
		session.setAttribute("CONTAINER", dataObj);
		if (draftid != 0) {
			draftDb.saveJsonDB(draftid, new Gson().toJson(dataObj, DataObject.class));
		}
		return response;
	}
	
	// TuanHMA Feb-1-2016 12:11 Remove a row
		@RequestMapping("/dragRow")
		public String dragDrow(Model model,@RequestParam(value = "id") int id, @RequestParam(value = "from") int from, @RequestParam(value = "to") int to,
				@CookieValue(value = "draftid", defaultValue = "0") int draftid) {
			String jsonStr = "";
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			DataObject dataObj = (DataObject) session.getAttribute("CONTAINER");
			
			if (draftid != 0 && dataObj == null) {
				jsonStr = draftDb.getJson(draftid);
				GsonBuilder gsonBilder = new GsonBuilder();

				gsonBilder.registerTypeAdapter(ElementData.class, new DataAdapter());

				Gson gson = gsonBilder.create();

				// TuanHMA 1-29-2016 Object held informations of JSON
				dataObj = gson.fromJson(jsonStr, DataObject.class);
			}
			if (dataObj == null) return "no data";
			ElementData eData  = dataObj.find(id);
			if (eData.getDataType().equals("table")){
				Table table = (Table) eData;
				table.getData().dragRow(from, to);
			}	
			
			session.setAttribute("CONTAINER", dataObj);
			if (draftid != 0) {
				draftDb.saveJsonDB(draftid, new Gson().toJson(dataObj, DataObject.class));
			}
			return new Gson().toJson(dataObj, DataObject.class);
		}
	
}
