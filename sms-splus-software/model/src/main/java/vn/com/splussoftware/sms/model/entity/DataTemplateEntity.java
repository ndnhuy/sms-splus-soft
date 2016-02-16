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
@Table(name = "datatemplatetbl")
public class DataTemplateEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
	 private long ID;
	 @Column(name = "Type")
	 private String Type;
	 @Column(name = "Template")
	 private String Template;
	 @Column(name = "createdby")
	 private String CreatedBy;
	 @Column(name = "createdtime")
	 private Date CreatedTime;
	 @Column(name = "modifiedby")
	 private String ModifiedBy;
	 @Column(name = "modifiedtime")
	 private Date ModifiedTime;
	 
	 public DataTemplateEntity setData(DataTemplateEntity data){
		 this.ID = data.ID;
		 this.Type = data.Type;
		 this.Template = data.Template;
		 this.CreatedBy = data.CreatedBy;
		 this.CreatedTime = data.CreatedTime;
		 this.ModifiedBy = data.ModifiedBy;
		 this.ModifiedTime = data.ModifiedTime;
		 return this;
	 }
}
