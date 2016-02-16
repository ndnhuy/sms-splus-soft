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
@Table(name = "phasetbl")
public class PhaseEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private long ID;
	 
	 @Column(name = "ticketid")
	 private long TicketID;
	 
	 @Column(name = "Status")
	 private String Status;
	
	 @Column(name = "Type")
	 private String Type;
	 
	 @Column(name = "displayattribute")
	 private String DisplayAttribute;

	 
	 public PhaseEntity setData(PhaseEntity data){
		 this.ID = data.ID;
		 this.TicketID = data.TicketID;
		 this.Status = data.Status;
		 this.Type = data.Type;
		 this.DisplayAttribute = data.DisplayAttribute;
		 return this;
	 }
}
