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
@Table(name = "phasetimetbl")
public class PhaseTimeEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private long ID;
	 
	 @Column(name = "Phaseid")
	 private long PhaseID;
	 
	 @Column(name = "Responsetime")
	 private Date ResponseTime;
	
	 @Column(name = "Implementtime")
	 private Date ImplementTime;
	 
	 @Column(name = "Fixtime")
	 private Date FixTime;
	 
	 @Column(name = "Owner")
	 private String Owner;
	 
	 public PhaseTimeEntity setData(PhaseTimeEntity data){
		 this.ID = data.ID;
		 this.PhaseID = data.PhaseID;
		 this.ResponseTime = data.ResponseTime;
		 this.ImplementTime = data.ImplementTime;
		 this.FixTime = data.FixTime;
		 this.Owner = data.Owner;
		 return this;
	 }
}
