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
@Table(name = "tickettimetbl")
public class TicketTimeEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private long ID;
	 
	 @Column(name = "Ticketid")
	 private long TicketID;
	 
	 @Column(name = "Processid")
	 private long ProcessID;
	
	 @Column(name = "Implementtime")
	 private Date ImplementTime;
	 
	 @Column(name = "Fixtime")
	 private Date FixTime;
	 
	 @Column(name = "Owner")
	 private String Owner;
	 
	 public TicketTimeEntity setData(TicketTimeEntity data){
		 this.ID = data.ID;
		 this.TicketID = data.TicketID;
		 this.ProcessID = data.ProcessID;
		 this.ImplementTime = data.ImplementTime;
		 this.FixTime = data.FixTime;
		 this.Owner = data.Owner;
		 return this;
	 }
}
