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
@Table(name = "entitiesroletbl")
public class EntitiesRoleEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private long ID;
	 
	 @Column(name = "entitytype")
	 private String EntityType;
	 
	 @Column(name = "entityiD")
	 private long EntityID;
	
	 @Column(name = "userkey")
	 private String Userkey;
	 
	 @Column(name = "role")
	 private String Role;

	 
	 public EntitiesRoleEntity setData(EntitiesRoleEntity data){
		 this.ID = data.ID;
		 this.EntityType = data.EntityType;
		 this.EntityID = data.EntityID;
		 this.Userkey = data.Userkey;
		 this.Role = data.Role;
		 return this;
	 }
}
