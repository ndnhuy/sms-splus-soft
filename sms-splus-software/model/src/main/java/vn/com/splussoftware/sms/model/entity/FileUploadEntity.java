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
@Table(name = "uploadtbl")
public class FileUploadEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long ID;

	@Column(name = "Name")
	private String Name;

	@Column(name = "Path")
	private String Path;
	
	@Column(name = "Createdtime")
	private Date CreatedTime;

	@Column(name = "Size")
	private long Size;

	@Column(name = "Type")
	private String Type;

	public FileUploadEntity setData(FileUploadEntity data) {
		this.ID = data.ID;
		this.Path = data.Path;
		this.Name = data.Name;
		this.CreatedTime = data.CreatedTime;
		this.Size = data.Size;
		this.Type = data.Type;
		return this;
	}
}
