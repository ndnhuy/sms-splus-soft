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
@Table(name = "relationshiptbl")
public class RelationshipEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private long ID;
	 
	 @Column(name = "Type")
	 private String Type;
	 
	 @Column(name = "Status")
	 private String Status;
	
	 @Column(name = "Ticketid")
	 private long TicketID;
	 
	 @Column(name = "Fromphaseid")
	 private long FromPhaseID;
	 
	 
	 @Column(name = "Tophaseid")
	 private long ToPhaseID;
	 
	 
	 @Column(name = "Information")
	 private String Information;
	 
	 public RelationshipEntity setData(RelationshipEntity data){
		 this.ID = data.ID;
		 this.Type = data.Type;
		 this.Status = data.Status;
		 this.TicketID = data.TicketID;
		 this.FromPhaseID = data.FromPhaseID;
		 this.ToPhaseID = data.ToPhaseID;
		 this.Information = data.Information;
		 return this;
	 }
}
