package vn.com.splussoftware.sms.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tickettbl")
public class TicketEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private long ID;
	 
	 @Column(name = "Serviceid")
	 private long ServiceID;
	 
	 @Column(name = "Processid")
	 private long ProcessID;
	
	 @Column(name = "Status")
	 private String Status;
	 
	 @Column(name = "Input")
	 private String Input;
	 
	 @Column(name = "Output")
	 private String Output;
	 
	 @Column(name = "Workflow")
	 private String Workflow;
	 
	 @Column(name = "Rating")
	 private String Rating;
	 
	 @Column(name = "slaresponse")
	 private String SLAResponse;
	 
	 @Column(name = "slaimplement")
	 private String SLAImplement;
	 
	 @Column(name = "slafix")
	 private String SLAFix;
	 
	 @Column(name = "Createdby")
	 private String CreatedBy;
	 
	 @Column(name = "Createdtime")
	 private Date CreatedTime;
	 
	 @Column(name = "Modifiedby")
	 private String ModifiedBy;
	 
	 @Column(name = "Modifiedtime")
	 private Date ModifiedTime;
	 
	 public TicketEntity setData(TicketEntity data){
		 this.ID = data.ID;
		 this.ServiceID = data.ServiceID;
		 this.ProcessID = data.ProcessID;
		 this.Status = data.Status;
		 this.Input = data.Input;
		 this.Output = data.Output;
		 this.Workflow = data.Workflow;
		 this.Rating = data.Rating;
		 this.SLAResponse = data.SLAResponse;
		 this.SLAImplement = data.SLAImplement;
		 this.CreatedBy = data.CreatedBy;
		 this.CreatedTime = data.CreatedTime;
		 this.ModifiedBy = data.ModifiedBy;
		 this.ModifiedTime = data.ModifiedTime;
		 return this;
	 }
}
