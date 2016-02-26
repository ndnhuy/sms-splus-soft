package vn.com.splussoftware.sms.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import vn.com.splussoftware.sms.model.entity.TicketInfoEntity;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataObject;
import vn.com.splussoftware.sms.utils.service.jsonhandler.ElementData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Individual;
import vn.com.splussoftware.sms.utils.service.jsonhandler.IndividualData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Matrix;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixValue;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Table;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableValue;

@Service
//TuanHMA 18/2/2016 Create ticketinfo 
//TuanHMA 18/2/2016 Using in TicketApiService
public class TicketUtilService {
	@Autowired
	TicketService tService;
	public void saveTicketInfo(DataObject dataObj,long ticketid, long relationshipID, Gson gson){
		if (dataObj == null) return;
		for (ElementData eData : dataObj.getElements()) {
			TicketInfoEntity info = new TicketInfoEntity();
			info.setTicketID(ticketid);
			info.setDataType(eData.getDataType());
			info.setRelationshipID(relationshipID);
			if (eData.getDataType().equals("individual")){						
				IndividualData data = ((Individual)eData).getData();
				info.setValueType(data.getType());
				info.setValue(data.getValue());			
				info.setConditions(gson.toJson(data.getConditions()));
				tService.createTicketInfo(new TicketInfoEntity().setData(info));
			}
			if (eData.getDataType().equals("table")){
				TableData data = ((Table)eData).getData();
				for (TableValue value : data.getValues()){
					info.setValue(value.getValue());
					info.setDataTemplateColumnID(value.getColumnId());
					info.setDataTemplateRowID(value.getRow());
					info.setConditions(gson.toJson(value.getConditions()));			    
					info.setValueType(data.getColumnType(value.getColumnId()));
					tService.createTicketInfo(new TicketInfoEntity().setData(info));		
				}
			}
			if (eData.getDataType().equals("matrix")){
				MatrixData data = ((Matrix)eData).getData();
				for (MatrixValue value : data.getValues()){
					info.setValue(value.getValue());
					info.setDataTemplateColumnID(value.getColumnId());
					info.setDataTemplateRowID(value.getRowId());
					info.setConditions(gson.toJson(value.getConditions()));			    
					info.setValueType(value.getType());
					tService.createTicketInfo(new TicketInfoEntity().setData(info));		
				}
			}
		}
	}
}
