package vn.com.splussoftware.sms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ticketinfotbl")
public class TicketInfoEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private long ID;
	 
	 @Column(name = "Value")
	 private String Value;
	 
	 @Column(name = "Valuetype")
	 private String ValueType;
	
	 @Column(name = "Conditions")
	 private String Conditions;
	 
	 @Column(name = "Datatype")
	 private String DataType;
	 
	 @Column(name = "Datatemplateid")
	 private long DataTemplateID;
	 
	 @Column(name = "Datatemplaterowid")
	 private long DataTemplateRowID;
	 
	 @Column(name = "Datatemplatecolumnid")
	 private long DataTemplateColumnID;
	 
	 @Column(name = "Ticketid")
	 private long TicketID;
	 
	 @Column(name = "Relationshipid")
	 private long RelationshipID;
	 
	 public TicketInfoEntity setData(TicketInfoEntity data){
		 this.ID = data.ID;
		 this.ValueType = data.ValueType;
		 this.Conditions = data.Conditions;
		 this.DataType = data.DataType;
		 this.DataTemplateID = data.DataTemplateID;
		 this.DataTemplateRowID = data.DataTemplateRowID;
		 this.DataTemplateColumnID = data.DataTemplateColumnID;
		 this.TicketID = data.TicketID;
		 this.RelationshipID = data.RelationshipID;
		 this.Value = data.Value;
		 return this;
	 }
}
